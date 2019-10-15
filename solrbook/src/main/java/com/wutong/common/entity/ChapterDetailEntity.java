/***********************************************
 * File Name: ChapterDetailEntity
 * Author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 11 10 2019 上午 11:53
 ***********************************************/

package com.wutong.common.entity;


import java.io.Serializable;

public class ChapterDetailEntity implements Serializable {
    private int chapterDetailId;
    private int chapterId;
    private String chapterDetailTitle;
    private String chapterDetailContent;
    private String chapterDetailAddr;

    public int getChapterDetailId() {
        return chapterDetailId;
    }

    public void setChapterDetailId(int chapterDetailId) {
        this.chapterDetailId = chapterDetailId;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterDetailTitle() {
        return chapterDetailTitle;
    }

    public void setChapterDetailTitle(String chapterDetailTitle) {
        this.chapterDetailTitle = chapterDetailTitle;
    }

    public String getChapterDetailContent() {
        return chapterDetailContent;
    }

    public void setChapterDetailContent(String chapterDetailContent) {
        this.chapterDetailContent = chapterDetailContent;
    }

    public String getChapterDetailAddr() {
        return chapterDetailAddr;
    }

    public void setChapterDetailAddr(String chapterDetailAddr) {
        this.chapterDetailAddr = chapterDetailAddr;
    }
}
