/***********************************************
 * File Name: CourseEntity
 * Author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 13 10 2019 21:45
 ***********************************************/

package com.wutong.common.entity;

import java.io.Serializable;
import java.util.List;

public class CourseEntity implements Serializable {
    private String courseName;
    private String courseShort;
    private int courseId;
    private List<BookEntity> books;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseShort() {
        return courseShort;
    }

    public void setCourseShort(String courseShort) {
        this.courseShort = courseShort;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public List<BookEntity> getBooks() {
        return books;
    }

    public void setBooks(List<BookEntity> books) {
        this.books = books;
    }
}
