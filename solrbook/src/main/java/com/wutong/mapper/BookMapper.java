/***********************************************
 * File Name: BookMapper
 * Author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 11 10 2019 上午 11:29
 ***********************************************/
package com.wutong.mapper;

import com.wutong.common.entity.BookEntity;
import com.wutong.common.entity.ChapterEntity;
import com.wutong.common.entity.CourseEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BookMapper {
    List<Map<String, String>> getBook(@Param("bookId") String bookId);

    List<Map<String, String>> getContent();

    List<CourseEntity> getCourses();

    List<BookEntity> getBooksByCourseId(@Param("courseId") Integer courseId);

    List<ChapterEntity> getChaptersByBookId(@Param("bookId") Integer bookId);
}
