package com.example.hsknows;

import java.util.ArrayList;

public class CardImageInfor {
    private String title; //卡片标题
    private String writer; //题目作者
    private ArrayList<String> mark; //问题标记

    public CardImageInfor(String title, String writer, ArrayList<String> mark, int kind){
        this.title = title;
        this.writer = writer;
        this.mark = mark;
    }

    public String getTitle(){
        return title;
    }
    public String getWriter(){
        return writer;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setWriter(String writer){
        this.writer = writer;
    }



}
