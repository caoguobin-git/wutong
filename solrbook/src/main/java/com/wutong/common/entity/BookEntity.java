/***********************************************
 * File Name: BookEntity
 * Author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 11 10 2019 上午 11:52
 ***********************************************/

package com.wutong.common.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author duochuang
 */
public class BookEntity implements Serializable {

    private int bookId;
    private int courseId;
    private String courseName;
    private String courseShort;
    private String bookName;
    private String bookAddr;
    private List<ChapterEntity> chapters;

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

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAddr() {
        return bookAddr;
    }

    public void setBookAddr(String bookAddr) {
        this.bookAddr = bookAddr;
    }

    public List<ChapterEntity> getChapters() {
        return chapters;
    }

    public void setChapters(List<ChapterEntity> chapters) {
        this.chapters = chapters;
    }
}
