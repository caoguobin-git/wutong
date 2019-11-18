/***********************************************
 * File Name: BookController
 * Author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 11 10 2019 上午 11:25
 ***********************************************/

package com.wutong.controller;


import com.google.common.base.Strings;
import com.wutong.common.entity.*;
import com.wutong.common.vo.JsonResult;
import com.wutong.service.BookService;
import com.wutong.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/book")
@Slf4j
public class BookController {

    private static Map<String, Integer> hotWords = new HashMap<>();

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getBook")
    @ResponseBody
    public List<BookEntity> getBook(Integer bookId) {
        if (bookId == null || bookId == 0) {
            bookId = 1;
        }
        List<BookEntity> result = bookService.getBook(bookId);
        return result;
    }

    @RequestMapping(value = "/getBookByBookId")
    @ResponseBody
    public JsonResult getBookByBookId(Integer bookId, String keyWords) {
        log.info(keyWords);
        List<BookEntity> book = getBook(bookId);
        if (book.size() == 0) {
            return new JsonResult("404", "未找到当前文档", null);
        }
        StringBuilder sb = new StringBuilder();
        List<ChapterEntity> chapters = book.get(0).getChapters();
        for (ChapterEntity chapter : chapters) {

            sb.append("<div class='chapter-title'>")
                    .append(StringEscapeUtils.unescapeJava(chapter.getChapterTitle()))
                    .append("</div>");
            List<ChapterDetailEntity> chapterDetails = chapter.getChapterDetails();
            for (ChapterDetailEntity chapterDetail : chapterDetails) {
                sb.append("<div class='chapter-detail-title'>")
                        .append(StringEscapeUtils.unescapeJava(chapterDetail.getChapterDetailTitle()))
                        .append("</div>")
                        .append(StringEscapeUtils.unescapeJava(chapterDetail.getChapterDetailContent()));
            }
        }
        System.out.println(sb.toString());
        Map<String, String> result = new HashMap<>();

        String chapterStr = sb.toString();
        if (!Strings.isNullOrEmpty(keyWords)) {
            String[] keywordArr = keyWords.split(",");
            for (int i = 0; i < keywordArr.length; i++) {
                chapterStr = chapterStr.replaceAll(keywordArr[i], "<text class='highlight-detail'>" + keywordArr[i] + "</text>");
            }
        }

        //去除转义
        chapterStr= StringEscapeUtils.unescapeJava(chapterStr);
        chapterStr=chapterStr.replaceAll("\\n","");
        result.put("courseName", book.get(0).getCourseName());
        result.put("bookName", book.get(0).getBookName());
        result.put("bookAddr", book.get(0).getBookAddr());
        result.put("chapters", chapterStr);
        return new JsonResult(result);
    }

    @RequestMapping(value = "toBookDetailPage")
    public String toBookDetailPage() {
        return "book-detail";
    }

    @RequestMapping(value = "/toBook/{bookname}")
    public String toBook(@PathVariable("bookname") String bookName) {
        System.out.println(bookName);
        return "pages/" + bookName;
    }

    @RequestMapping(value = "/getSuggest")
    @ResponseBody
    public JsonResult getSuggest(String keyWord){
        log.info(keyWord);
        List<String> result = bookService.getSuggest(keyWord);
        log.info(result.toString());
        return new JsonResult(result);
    }

    @RequestMapping("/searchKeyWords")
    @ResponseBody
    public JsonResult searchKeyWords(String usertoken, String keyWords, String course, Integer pageSize, Integer currentPage,String select) {
        log.info(keyWords);


//        if (flag){
//            HashMap<String, Object> stringObjectHashMap = new HashMap<>();
//            stringObjectHashMap.put("wordAnalysis",bookService.getWordsFromString(keyWords));
//            stringObjectHashMap.put("numFound", 0);
//            stringObjectHashMap.put("start", 0);
//            stringObjectHashMap.put("pageSize", 100);
//            stringObjectHashMap.put("currentPage", 1);
//            stringObjectHashMap.put("results", new LinkedList<>());
//
//            return new JsonResult(stringObjectHashMap);
//        }


        if (!Strings.isNullOrEmpty(usertoken)) {
            UserEntity userEntity = userService.findUserById(usertoken);
            if (userEntity != null) {
                course = userEntity.getRole();
            }
        }

        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        if (currentPage == null || currentPage == 0) {
            currentPage = 1;
        }
        //设置搜索热词
        String trim = null;
        if (keyWords != null) {
            trim = keyWords.trim();
        }

        Pattern pattern=Pattern.compile("\\w*");
        String test=keyWords.replaceAll(" ", "");
        Matcher matcher = pattern.matcher(test);
        while (matcher.find()){
            if (matcher.group().length()>0){
                String trim1 = matcher.group(0).trim();
                trim = trim.replaceAll(trim1, "");
            }
        }
        log.info(trim);

        if (trim != null && trim.length() > 0) {
            String[] s = trim.split(" ");
            for (int i = 0; i < s.length; i++) {
                if (!Strings.isNullOrEmpty(s[i])) {
                    hotWords.merge(s[i], 1, Integer::sum);
                }
            }
        }
        log.info(course);

        Map<String, Object> results = bookService.searchKeyWords(trim, course, pageSize, currentPage,select);
        return new JsonResult(results);
    }

    @RequestMapping("/getHotWords")
    @ResponseBody
    public JsonResult getHotWords(Integer size) {
        if (null == size || size == 0) {
            size = 10;
        }
        List<Map.Entry<String, Integer>> list = new ArrayList<>(hotWords.entrySet());
        Collections.sort(list, (o1, o2) -> o2.getValue() - o1.getValue());
        List<String> result = new ArrayList<>();
        for (int i = 0; i < list.size() && i < size; i++) {
            result.add(list.get(i).getKey());
        }

        return new JsonResult(result);
    }

    @RequestMapping(value = "/getContent")
    @ResponseBody
    public JsonResult getContent() {
        List<Map<String, String>> result = bookService.getContent();
        return new JsonResult(result);
    }

    @RequestMapping(value = "/getCourses")
    @ResponseBody
    public JsonResult getCourses() {
        List<CourseEntity> result = bookService.getCourses();
        return new JsonResult(result);
    }

    @RequestMapping(value = "/getBooksByCourseId")
    @ResponseBody
    public JsonResult getBooksByCourseId(Integer courseId) {
        if (courseId == null || courseId == 0) {
            courseId = 1;
        }
        String courseName = null;
        List<BookEntity> result = bookService.getBooksByCourseId(courseId);
        if (result.size() > 0) {
            courseName = result.get(0).getCourseName();
        }
        return new JsonResult("200", courseName, result);
    }

    @RequestMapping(value = "/getChaptersByBookId")
    @ResponseBody
    public JsonResult getChaptersByBookId(Integer bookId) {
        if (bookId == null || bookId == 0) {
            bookId = 1;
        }
        List<ChapterEntity> result = bookService.getChaptersByBookId(bookId);
        return new JsonResult(result);
    }

    @RequestMapping(value = "/getChapterdetailsByChapterId")
    @ResponseBody
    public JsonResult getChapterdetailsByChapterId(Integer chapterId) {
        if (chapterId == null || chapterId == 0) {
            chapterId = 1;
        }
        List<ChapterDetailEntity> result = bookService.getChapterdetailsByChapterId(chapterId);
        return new JsonResult(result);
    }

    @RequestMapping(value = "/saveChapter")
    @ResponseBody
    public JsonResult saveChapter(String chapterDetailContent) {
        return null;
    }

    @RequestMapping(value = "/saveChapterByBookId",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult saveChapterByBookId(String chapterTitle,Integer bookId){
        String resutl = bookService.saveChapterByBookId(bookId,chapterTitle);
        return new JsonResult(resutl);
    }

    @RequestMapping(value = "saveChapterDetailByChapterId",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult saveChapterDetailByChapterId(Integer chapterId,String chapterDetailTitle,String chapterDetailContent){
        String result = bookService.saveChapterDetailByChapterId(chapterId,chapterDetailTitle,chapterDetailContent);
        return new JsonResult(result);
    }

    @RequestMapping(value = "/savePic", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult savePic(String usertoken, MultipartFile picFile) {
        log.info(usertoken);
        log.info(picFile.getOriginalFilename());
        String picUrl = bookService.savePic(picFile);
        return new JsonResult(picUrl);
    }

    @RequestMapping(value = "/getBookById")
    @ResponseBody
    public JsonResult getBookById(Integer bookId, String keyWords) {
        log.info("getBookById");
        List<BookEntity> book = getBook(bookId);
        if (book.size() == 0) {
            return new JsonResult("404", "未找到当前文档", null);
        }
        List<ChapterEntity> chapters = book.get(0).getChapters();
        List<ChapterEntityNew> chapterResult = new LinkedList<>();
        for (ChapterEntity chapter : chapters) {
            ChapterEntityNew chapterEntityNew=new ChapterEntityNew();
            chapterEntityNew.setBookId(chapter.getBookId());
            chapterEntityNew.setChapterContent(chapter.getChapterContent());
            chapterEntityNew.setChapterId(chapter.getChapterId());
            chapterEntityNew.setChapterTitle(chapter.getChapterTitle());
            List<ChapterDetailEntity> chapterDetails = chapter.getChapterDetails();
            chapterEntityNew.setChapterDetails(setChapterDetails(chapterDetails));

            chapterResult.add(chapterEntityNew);
            for (ChapterEntityNew entityNew : chapterResult) {
            }
        }
        Map<String, Object> result = new HashMap<>();
//
//        String chapterStr = sb.toString();
//        if (!Strings.isNullOrEmpty(keyWords)) {
//            String[] keywordArr = keyWords.split(",");
//            for (int i = 0; i < keywordArr.length; i++) {
//                chapterStr = chapterStr.replaceAll(keywordArr[i], "<text class='highlight-detail'>" + keywordArr[i] + "</text>");
//            }
//        }
//
        //去除转义
//        chapterStr= StringEscapeUtils.unescapeJava(chapterStr);
//        chapterStr=chapterStr.replaceAll("\\n","");
        result.put("courseName", book.get(0).getCourseName());
        result.put("bookName", book.get(0).getBookName());
        result.put("bookAddr", book.get(0).getBookAddr());
        result.put("chapters", chapterResult);
//        return new JsonResult(result);
        return new JsonResult(result);
    }

    private List<ChapterDetailContent> setChapterDetails(List<ChapterDetailEntity> chapterDetails) {
        List<ChapterDetailContent> list=new LinkedList<>();

        for (ChapterDetailEntity chapterDetail : chapterDetails) {
            ChapterDetailContent chapterDetailContent=new ChapterDetailContent();
            chapterDetailContent.setChapterDetailTitle(chapterDetail.getChapterDetailTitle());
            chapterDetailContent.setChapterDetailId(chapterDetail.getChapterDetailId());
            chapterDetailContent.setChapterId(chapterDetail.getChapterId());
            chapterDetailContent.setChapterDetailAddr(chapterDetail.getChapterDetailAddr());
            chapterDetailContent.setChapterDetailContent(getChapterDetailContentNew(chapterDetail.getChapterDetailContent()));
            list.add(chapterDetailContent);
        }
//        List<ChapterDetailContent> list=new LinkedList<>();
//        for (ChapterDetailEntity chapterDetail : chapterDetails) {
//            ChapterDetailContent chapterDetailContent=new ChapterDetailContent();
//            System.out.println("detail::::"+chapterDetailContent);
//            chapterDetailContent.setChapterDetailAddr(chapterDetail.getChapterDetailAddr());
//            chapterDetailContent.setChapterId(chapterDetail.getChapterId());
//            System.out.println("content"+chapterDetail.getChapterDetailContent());
//            chapterDetailContent.setChapterDetailContent(getChapterDetailContentNew(chapterDetail.getChapterDetailContent()));
//            chapterDetailContent.setChapterDetailId(chapterDetail.getChapterDetailId());
//            chapterDetailContent.setChapterDetailTitle(chapterDetail.getChapterDetailTitle());
//        }
        return list;
    }

    private Object getChapterDetailContentNew(String chapterDetailContent) {
//        System.out.println(chapterDetailContent);
        chapterDetailContent=chapterDetailContent.replaceAll("<br>","");
        chapterDetailContent=chapterDetailContent.replaceAll("\\n","");
        List<Map> result=new LinkedList<>();
        Document doc = Jsoup.parse(chapterDetailContent);
//        System.out.println(doc.toString());
        Elements allElements = doc.getElementsByTag("div");
        for (Element element : allElements) {
            Elements children = element.children();
            for (Element child : children) {
                System.out.println(child.toString());
                Map<String,Object> map=new HashMap<>();
                switch (child.nodeName()){
                    case "p":
                        map.put("type", "p");
                        map.put("content", child.text());
                        break;
                    case "img":
                        map.put("type", "img");
                        map.put("content", child.attr("src"));
                        break;
                    case "ul":
                    case "ol":
                        System.out.println("list");
                        map.put("type", "list");
                        List<String> strings=new LinkedList<>();
                        System.out.println("zheshige list----------------");
                        Elements li = child.select("li");
                        for (Element element1 : li) {
                            strings.add(element1.text());
                        }
                        map.put("content",strings);

                        break;
                    case "h5":{
                        map.put("type", "title");

                        map.put("content", child.text());
                        break;
                    }

                }
//                System.out.println(map);
                result.add(map);
            }
        }

        return result;
    }

    //获取分词结果
    @GetMapping(value = "/getWordsFromString")
    @ResponseBody
    public JsonResult getWordsFromString(String wordStr){
        List<String> result = bookService.getWordsFromString(wordStr);
        return  new JsonResult(result);
    }

}
