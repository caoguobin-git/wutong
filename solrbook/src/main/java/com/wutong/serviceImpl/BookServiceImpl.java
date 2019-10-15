/***********************************************
 * File Name: BookServiceImpl
 * Author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 11 10 2019 上午 11:27
 ***********************************************/

package com.wutong.serviceImpl;


import com.wutong.common.entity.BookEntity;
import com.wutong.common.entity.ChapterEntity;
import com.wutong.common.entity.CourseEntity;
import com.wutong.mapper.BookMapper;
import com.wutong.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    final static String solrUrl = "http://106.13.51.176:8983/solr/book";

    final static HttpSolrClient solrServer = new HttpSolrClient.Builder(solrUrl)
            .withConnectionTimeout(10000)
            .withSocketTimeout(60000)
            .build();

    @Override
    public List<Map<String, String>> getBook(int bookId) {
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
            rsp = solrServer.query(query);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取拼写检查的结果集
        SpellCheckResponse re = rsp.getSpellCheckResponse();
        if (re != null) {
            List<SpellCheckResponse.Suggestion> suggestions = re.getSuggestions();
            for (SpellCheckResponse.Suggestion suggestion : suggestions) {
                List<String> list = suggestion.getAlternatives();
                for (String s : list) {
                    System.out.println(s);
                    wordList.add(s);
                }
                return wordList;
            }
            //获取第一个推荐词
            String t = re.getFirstSuggestion(keyWord);
            System.out.println("推荐词：" + t);
        }
        return null;
    }

    @Override
    public Map<String, Object> searchKeyWords(String keywords, String course, int pageSize, int currentPage) {
        SolrQuery query = new SolrQuery();
        StringBuilder queryStr = new StringBuilder();
        if (null == keywords || "".equalsIgnoreCase(keywords.trim())) {
            queryStr.append("*:*").append(" ");
        }
        String[] s = keywords.split(" ");
        for (String s1 : s) {
            s1 = s1.trim();
            if (s1 != null && !"".equalsIgnoreCase(s1)) {
                queryStr.append("chapterdetailcontent:")
                        .append(s1).append(" ")
                        .append("chaptertitle:")
                        .append(s1).append(" ");
            }
        }
        if (!(course == null || "".equalsIgnoreCase(course.trim()))) {
            queryStr.append("coursename:").append(course);
        }

        System.out.println("查询条件：" + queryStr.toString());
        query.set("q", queryStr.toString());
        query.set("sort","chapterdetailtitle asc");

        query.set("df", "chapterdetailcontent");
        query.set("fl", "bookaddr,chapterdetailaddr,coursename,chaptertitle,bookid,chpterid,bookname,courseshort,chapterdetailtitle,chapterdetailcontent,chapterdetailid,id");

//        设置高亮
        query.setHighlight(true);
        query.addHighlightField("bookname");
        query.addHighlightField("chapterdetailtitle");
        query.addHighlightField("chapterdetailcontent");
        query.setHighlightFragsize(100000);
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
            response = solrServer.query(query);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 查询结果
        SolrDocumentList results = response.getResults();
        log.info(String.valueOf(results.size()));
        long numFound = results.getNumFound();
        long start = results.getStart();


        Map<String, Map<String, List<String>>> highlightingMap = response.getHighlighting();
        highlightingMap.forEach((k, v) -> {
            System.out.println(k);
            v.forEach((a, b) -> {
                System.out.println(a);
                System.out.println(b.get(0));
            });
        });


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
//                    result.setField(fieldName1, highlightingMap.get(result.getFieldValue("id")).get(fieldName1).get(0));


                }
                if (highlightingMap.get(id).get(fieldName2) != null) {
                    String id2=highlightingMap.get(result.getFieldValue("id")).get(fieldName2).get(0);
                    if (keywords.indexOf(course) == -1) {
                        id2 = id2.replaceAll("<text class='highlight-detail'>" + course + "</text>", course);
                    }
                    result.setField(fieldName2, id2);
//                    result.setField(fieldName1, highlightingMap.get(result.getFieldValue("id")).get(fieldName1).get(0));

                }
                if (highlightingMap.get(id).get(fieldName3) != null) {
                    String id3=highlightingMap.get(result.getFieldValue("id")).get(fieldName3).get(0);
                    if (keywords.indexOf(course) == -1) {
                        id3 = id3.replaceAll("<text class='highlight-detail'>" + course + "</text>", course);
                    }
                    result.setField(fieldName3, id3);
//                    result.setField(fieldName2, highlightingMap.get(result.getFieldValue("id")).get(fieldName2).get(0));
                }
            }
        }

        System.out.println(results);

        Map<String, Object> pageResult = new HashMap<>();
        pageResult.put("numFound", numFound);
        pageResult.put("start", start);
        pageResult.put("pageSize", pageSize);
        if (currentPage > (numFound / pageSize + 1)) {
            currentPage = (int) (numFound / pageSize + 1);
        }
        pageResult.put("currentPage", currentPage);
        pageResult.put("results", results);
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
}
