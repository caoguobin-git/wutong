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
import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/book")
@Slf4j
public class BookController {

    private static Map<String, Integer> hotWords = new HashMap<>();

    @Autowired
    private BookService bookService;

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
    public JsonResult getBookByBookId(Integer bookId) {
        List<BookEntity> book = getBook(bookId);
        if (book.size()==0){
            return new JsonResult("404","未找到当前文档",null);
        }
        StringBuilder sb = new StringBuilder();
        List<ChapterEntity> chapters = book.get(0).getChapters();
        for (ChapterEntity chapter : chapters) {
            sb.append(chapter.getChapterTitle());
            List<ChapterDetailEntity> chapterDetails = chapter.getChapterDetails();
            for (ChapterDetailEntity chapterDetail : chapterDetails) {
                sb.append(chapterDetail.getChapterDetailTitle())
                        .append(chapterDetail.getChapterDetailContent());
            }
        }
        System.out.println(sb.toString());
        Map<String,String> result = new HashMap<>();

        result.put("courseName", book.get(0).getCourseName());
        result.put("bookName", book.get(0).getBookName());
        result.put("bookAddr", book.get(0).getBookAddr());
        result.put("chapters", sb.toString());
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

    @RequestMapping("/getSuggest")
    @ResponseBody
    public JsonResult getSuggest(String keyWord) throws IOException, SolrServerException {
        List<String> result = bookService.getSuggest(keyWord);
        return new JsonResult(result);
    }

    @RequestMapping("/searchKeyWords")
    @ResponseBody
    public JsonResult searchKeyWords(String usertoken, String keyWords, String course, Integer pageSize, Integer currentPage) {
        log.info(keyWords);
        log.info(course);
        if (!Strings.isNullOrEmpty(usertoken)) {
            UserEntity userEntity = UserController.loginUsers.get(usertoken);
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
        if (trim != null && trim.length() > 0) {
            String[] s = trim.split(" ");
            for (int i = 0; i < s.length; i++) {
                hotWords.merge(s[i], 1, Integer::sum);
            }
        }

        Map<String, Object> results = bookService.searchKeyWords(keyWords, course, pageSize, currentPage);
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


}
