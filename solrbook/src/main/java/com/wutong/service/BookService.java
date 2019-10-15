/***********************************************
 * File Name: BookService
 * Author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 11 10 2019 上午 11:27
 ***********************************************/
package com.wutong.service;

import com.wutong.common.entity.BookEntity;
import com.wutong.common.entity.ChapterEntity;
import com.wutong.common.entity.CourseEntity;

import java.util.List;
import java.util.Map;

public interface BookService {
    List<Map<String, String>> getBook(int bookId);

    List<String> getSuggest(String keyWord);

    Map<String, Object> searchKeyWords(String keywords, String course, int pageSize, int currentPage);

    List<Map<String, String>> getContent();

    List<CourseEntity> getCourses();

    List<BookEntity> getBooksByCourseId(Integer courseId);

    List<ChapterEntity> getChaptersByBookId(Integer bookId);
}
