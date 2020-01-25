/***********************************************
 * File Name: BookServiceImpl
 * Author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 11 10 2019 上午 11:27
 ***********************************************/

package com.wutong.serviceImpl;


import com.google.common.base.Strings;
import com.wutong.common.entity.BookEntity;
import com.wutong.common.entity.ChapterDetailEntity;
import com.wutong.common.entity.ChapterEntity;
import com.wutong.common.entity.CourseEntity;
import com.wutong.common.util.FilePathUtil;
import com.wutong.mapper.BookMapper;
import com.wutong.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.FieldAnalysisRequest;
import org.apache.solr.client.solrj.response.AnalysisResponseBase;
import org.apache.solr.client.solrj.response.FieldAnalysisResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class BookServiceImpl implements BookService {


    @Value("${pic-path.root-path}")
    private String ROOT_PATH;
    @Value("${pic-path.child-path}")
    private String CHILD_PATH;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private HttpSolrClient solrClient;

    @Override
    public List<BookEntity> getBook(int bookId) {
        return bookMapper.getBook(String.valueOf(bookId));
    }

    @Override
    public List<String> getSuggest(String keyWord) {
        List<String> wordList = new ArrayList<>();
        SolrQuery query = new SolrQuery();
        //查询的词
        query.set("q", "chapterdetailcontent:" + keyWord);
        //请求到suggest中
        query.set("qt", "/suggest");
        //返回数量
        query.set("spellcheck.count", "10");
        QueryResponse rsp = null;
        try {
            rsp = solrClient.query(query);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取拼写检查的结果集
        SpellCheckResponse re = rsp.getSpellCheckResponse();
        wordList.add(keyWord);
        if (re != null) {
            List<SpellCheckResponse.Suggestion> suggestions = re.getSuggestions();
            for (SpellCheckResponse.Suggestion suggestion : suggestions) {
                List<String> list = suggestion.getAlternatives();
                for (String s : list) {
//                    System.out.println(s);
                    wordList.add(s);
                }
            }
            //获取第一个推荐词
            String t = re.getFirstSuggestion(keyWord);
        }
        return wordList;
    }

    @Override
    public Map<String, Object> searchKeyWords(String keywords, String course, int pageSize, int currentPage, String select) {
        SolrQuery query = new SolrQuery();
        StringBuilder queryStr = new StringBuilder();
        if (null == keywords || "".equalsIgnoreCase(keywords.trim())) {
            queryStr.append("*:*").append(" ");
        }
        String[] s = keywords.split(" ");
        queryStr.append(String.join(" AND ", s));

        if (!Strings.isNullOrEmpty(select)) {
            queryStr.append(" AND courseshort:").append(select);
        }

        //修改默认连字符
        ModifiableSolrParams params = new ModifiableSolrParams();
        params.set("q.op", "AND");
        query.add(params);

        query.set("df", "searchText");
        query.set("q", queryStr.toString());

        //设置权重
        query.set("bf", "sum(div(chapterdetailtitle,0.01),if(exists(coursename),20000,0),div(chapterdetailcontent,0.1)");


        query.set("fl", "bookaddr,chapterdetailaddr,coursename,chaptertitle,bookid,chpterid,bookname,courseshort,chapterdetailtitle,chapterdetailcontent,chapterdetailid,id,chapterdetailhtmlid");

//        设置高亮
        query.setHighlight(true);
        query.addHighlightField("bookname");
        query.addHighlightField("chapterdetailtitle");
        query.addHighlightField("chapterdetailcontent");
        query.setHighlightFragsize(9999999);
        query.setHighlightSimplePre("<text class='highlight-detail'>");
        query.setHighlightSimplePost("</text>");


        if (0 == pageSize) {
            pageSize = 10;
        }
        if (currentPage == 0) {
            currentPage = 1;
        }
        query.setStart((currentPage - 1) * pageSize);
        query.setRows(pageSize);
        // 调用server的查询方法，查询索引库
        QueryResponse response = null;
        try {
            response = solrClient.query(query);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 查询结果
        SolrDocumentList results = response.getResults();
        long numFound = results.getNumFound();
        long start = results.getStart();


        Map<String, Map<String, List<String>>> highlightingMap = response.getHighlighting();

        //去除course高亮
        String fieldName1 = "bookname";
        String fieldName2 = "chapterdetailtitle";
        String fieldName3 = "chapterdetailcontent";

        for (SolrDocument result : results) {
            String id = (String) result.getFieldValue("id");
            if (highlightingMap.get(id) != null) {
                if (highlightingMap.get(id).get(fieldName1) != null) {
                    String id1 = highlightingMap.get(result.getFieldValue("id")).get(fieldName1).get(0);
                    if (keywords.indexOf(course) == -1) {
                        id1 = id1.replaceAll("<text class='highlight-detail'>" + course + "</text>", course);
                    }
                    result.setField(fieldName1, id1);
                }
                if (highlightingMap.get(id).get(fieldName2) != null) {
                    String id2 = highlightingMap.get(result.getFieldValue("id")).get(fieldName2).get(0);
                    if (keywords.indexOf(course) == -1) {
                        id2 = id2.replaceAll("<text class='highlight-detail'>" + course + "</text>", course);
                    }
                    result.setField(fieldName2, id2);

                }
                if (highlightingMap.get(id).get(fieldName3) != null) {
                    String id3 = highlightingMap.get(result.getFieldValue("id")).get(fieldName3).get(0);
                    //System.out.println("开始===================================");
                    //System.out.println(id3);
                    if (keywords.indexOf(course) == -1) {
                        id3 = id3.replaceAll("<text class='highlight-detail'>" + course + "</text>", course);
                    }

                    result.setField(fieldName3, id3);
                }
            }
        }

        Map<String, Object> pageResult = new HashMap<>();
        for (int i = 0; i < results.size(); i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("<div class='content-container>")
                    .append("<div class='searchListTitle'>")
                    .append(results.get(i).getFieldValue("chapterdetailtitle"))
                    .append("</div>")
                    .append("<div class='searchListTitle2'>")
                    .append(results.get(i).getFieldValue("courseshort"))
                    .append("->")
                    .append(results.get(i).getFieldValue("bookname"))
                    .append("->")
                    .append(results.get(i).getFieldValue("chaptertitle"))
                    .append("</div>")
                    .append(results.get(i).getFieldValue("chapterdetailcontent"))
                    .append("</div>");
            results.get(i).setField("chapterdetailcontent", sb.toString());
        }
        pageResult.put("numFound", numFound);
        pageResult.put("start", start);
        pageResult.put("pageSize", pageSize);
        if (currentPage > (numFound / pageSize + 1)) {
            currentPage = (int) (numFound / pageSize + 1);
        }
        pageResult.put("currentPage", currentPage);
        pageResult.put("results", results);
        pageResult.put("wordAnalysis", getWordsFromString(keywords));

        return pageResult;
    }

    @Override
    public List<Map<String, String>> getContent() {
        return bookMapper.getContent();
    }

    @Override
    public List<CourseEntity> getCourses() {

        List<CourseEntity> result = bookMapper.getCourses();
        return result;
    }

    @Override
    public List<BookEntity> getBooksByCourseId(Integer courseId) {
        List<BookEntity> result = bookMapper.getBooksByCourseId(courseId);
        return result;
    }

    @Override
    public List<ChapterEntity> getChaptersByBookId(Integer bookId) {
        List<ChapterEntity> result = bookMapper.getChaptersByBookId(bookId);
        return result;
    }

    @Override
    public String savePic(MultipartFile pic) {
        return uploadPic(pic);
    }

    @Override
    public List<ChapterDetailEntity> getChapterdetailsByChapterId(Integer chapterId) {
        List<ChapterDetailEntity> result = bookMapper.getChapterdetailsByChapterId(chapterId);
        return result;
    }

    @Override
    public String saveChapterByBookId(Integer bookId, String chapterTitle) {
        int result = bookMapper.saveChapterByBookId(bookId, chapterTitle);
        if (result > 0) {
            return "ok";
        } else {
            return "failed";
        }
    }

    @Override
    public String saveChapterDetailByChapterId(Integer chapterId, String chapterDetailTitle, String chapterDetailContent) {
        String s = chapterDetailContent.replaceAll("\"", "\'");
        StringBuilder sb = new StringBuilder();
        sb.append("<div>").append(s).append("</div>");

        int result = bookMapper.saveChapterDetailByChapterId(chapterId, chapterDetailTitle, sb.toString());
        if (result > 0) {
            return "ok";
        } else {
            return "failed";
        }
    }

    @Override
    public List<String> getWordsFromString(String wordStr) {
        FieldAnalysisRequest request = new FieldAnalysisRequest();
        request.setFieldNames(Collections.singletonList("chapterdetailtitle"));
        request.setFieldValue(wordStr);
        request.setQuery(wordStr);

        Set<String> resultSet = new HashSet<>();
        resultSet.add(wordStr.toLowerCase());

        FieldAnalysisResponse response = null;
        try {
            response = request.process(solrClient);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(resultSet);
        }

        Iterable<AnalysisResponseBase.AnalysisPhase> searchText = response.getFieldNameAnalysis("chapterdetailtitle").getQueryPhases();

        Iterator<AnalysisResponseBase.AnalysisPhase> it = searchText.iterator();

        while (it.hasNext()) {
            AnalysisResponseBase.AnalysisPhase next = it.next();
            List<AnalysisResponseBase.TokenInfo> tokens = next.getTokens();
            for (AnalysisResponseBase.TokenInfo token : tokens) {
                //去除长度小于2的结果
                if (token.getText().length() > 1) {
                    resultSet.add(token.getText().toLowerCase());
                }
            }
        }
        List<String> result = new ArrayList<>(resultSet);

        return result;
    }


    private String uploadPic(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
        String s = null;
        try {
            s = FilePathUtil.uploadFile(ROOT_PATH + CHILD_PATH, fileType);
            File file = new File(ROOT_PATH + CHILD_PATH + s);
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return CHILD_PATH + s;
    }
}
