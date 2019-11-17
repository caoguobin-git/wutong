/***********************************************
 * File Name: BookService
 * Author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 11 10 2019 上午 11:27
 ***********************************************/
package com.wutong.service;

import com.wutong.common.entity.BookEntity;
import com.wutong.common.entity.ChapterDetailEntity;
import com.wutong.common.entity.ChapterEntity;
import com.wutong.common.entity.CourseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface BookService {
    List<BookEntity> getBook(int bookId);

    List<String> getSuggest(String keyWord);

    Map<String, Object> searchKeyWords(String keywords, String course, int pageSize, int currentPage, String select);

    List<Map<String, String>> getContent();

    List<CourseEntity> getCourses();

    List<BookEntity> getBooksByCourseId(Integer courseId);

    List<ChapterEntity> getChaptersByBookId(Integer bookId);

    String savePic(MultipartFile pic);

    List<ChapterDetailEntity> getChapterdetailsByChapterId(Integer chapterId);

    String saveChapterByBookId(Integer bookId, String chapterTitle);

    String saveChapterDetailByChapterId(Integer chapterId, String chapterDetailTitle, String chapterDetailContent);

    List<String> getWordsFromString(String wordStr);
}
