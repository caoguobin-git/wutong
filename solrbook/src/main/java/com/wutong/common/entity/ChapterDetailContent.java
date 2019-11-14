/***********************************************
 * File Name: ChapterDetailContentNew
 * Author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 14 11 2019 20:28
 ***********************************************/

package com.wutong.common.entity;

import java.util.List;
import java.util.Map;

public class ChapterDetailContent {
    private int chapterDetailId;
    private int chapterId;
    private String chapterDetailTitle;
    private Object chapterDetailContent;
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

    public Object getChapterDetailContent() {
        return chapterDetailContent;
    }

    public void setChapterDetailContent(Object chapterDetailContent) {
        this.chapterDetailContent = chapterDetailContent;
    }

    public String getChapterDetailAddr() {
        return chapterDetailAddr;
    }

    public void setChapterDetailAddr(String chapterDetailAddr) {
        this.chapterDetailAddr = chapterDetailAddr;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"chapterDetailId\":")
                .append(chapterDetailId);
        sb.append(",\"chapterId\":")
                .append(chapterId);
        sb.append(",\"chapterDetailTitle\":\"")
                .append(chapterDetailTitle).append('\"');
        sb.append(",\"chapterDetailContent\":")
                .append(getHH(chapterDetailContent));
        sb.append(",\"chapterDetailAddr\":\"")
                .append(chapterDetailAddr).append('\"');
        sb.append('}');
        return sb.toString();
    }

    private String getHH(Object chapterDetailContent) {
        StringBuilder sb=new StringBuilder();
        List<Map> list= (List<Map>) chapterDetailContent;
        for (Map map : list) {
            sb.append(map.toString());
        }
        return sb.toString();
    }
}
