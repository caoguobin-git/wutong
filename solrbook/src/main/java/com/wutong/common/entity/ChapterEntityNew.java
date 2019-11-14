/***********************************************
 * File Name: ChapterEntityNew
 * Author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 14 11 2019 20:55
 ***********************************************/

package com.wutong.common.entity;

import java.util.List;
import java.util.Map;

public class ChapterEntityNew {
    private int chapterId;
    private int bookId;
    private String chapterTitle;
    private String chapterContent;
    private List<ChapterDetailContent> chapterDetails;

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

    public List<ChapterDetailContent> getChapterDetails() {
        return chapterDetails;
    }

    public void setChapterDetails(List<ChapterDetailContent> chapterDetails) {
        this.chapterDetails = chapterDetails;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"chapterId\":")
                .append(chapterId);
        sb.append(",\"bookId\":")
                .append(bookId);
        sb.append(",\"chapterTitle\":\"")
                .append(chapterTitle).append('\"');
        sb.append(",\"chapterContent\":\"")
                .append(chapterContent).append('\"');
        sb.append(",\"chapterDetails\":")
                .append(getDetails(chapterDetails));
        sb.append('}');
        return sb.toString();
    }

    private String getDetails(Object chapterDetails) {
       ChapterDetailContent chapterDetailContent = (ChapterDetailContent) chapterDetails;
        StringBuilder sb=new StringBuilder();
sb.append(chapterDetailContent.toString());
        return sb.toString();
    }
}
