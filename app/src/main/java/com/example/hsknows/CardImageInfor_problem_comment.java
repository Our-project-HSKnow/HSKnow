package com.example.hsknows;


/*
这是在问题界面的评论个体
 */

public class CardImageInfor_problem_comment {
    private String time; //发布事件
    private String author; //题目作者
    private String content; //问题概述

    public CardImageInfor_problem_comment(String time, String author, String content){
        this.time = time;
        this.author = author;
        this.content = content;
    }

    public String getTime(){
        return time;
    }
    public String getAuthor(){
        return author;
    }
    public String getContent() { return content;}
    public void setTime(String time){
        this.time = time;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public void setContent(String content) {this.content = content;}
}
