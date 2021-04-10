package com.example.hsknows;

import java.util.ArrayList;

public class CardImageInfor_message {
    private String title; //卡片标题
    private String writer; //题目作者
    private String content; //问题内容

    public CardImageInfor_message(String title, String writer, String content, int kind){
        this.title = title;
        this.writer = writer;
        this.content = content;
    }

    public String getTitle(){
        return title;
    }
    public String getWriter(){
        return writer;
    }
    public String getContent() { return content;}
    public void setTitle(String title){
        this.title = title;
    }
    public void setWriter(String writer){
        this.writer = writer;
    }
    public void setContent(String content) {this.content = content;}


}
