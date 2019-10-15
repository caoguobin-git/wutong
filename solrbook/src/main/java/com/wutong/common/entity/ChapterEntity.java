/***********************************************
 * File Name: ChapterEntity
 * Author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 11 10 2019 上午 11:52
 ***********************************************/

package com.wutong.common.entity;

import java.io.Serializable;
import java.util.List;

public class ChapterEntity implements Serializable {
    private int chapterId;
    private int bookId;
    private String chapterTitle;
    private String chapterContent;
    private List<ChapterDetailEntity> chapterDetails;

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public String getChapterContent() {
        return chapterContent;
    }

    public void setChapterContent(String chapterContent) {
        this.chapterContent = chapterContent;
    }

    public List<ChapterDetailEntity> getChapterDetails() {
        return chapterDetails;
    }

    public void setChapterDetails(List<ChapterDetailEntity> chapterDetails) {
        this.chapterDetails = chapterDetails;
    }
}
