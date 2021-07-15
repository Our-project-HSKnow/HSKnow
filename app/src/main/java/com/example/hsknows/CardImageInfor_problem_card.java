package com.example.hsknows;


/*
这是在问题浏览列表的问题个体
 */

public class CardImageInfor_problem_card {
    private String title; //卡片标题
    private String author; //题目作者
    private String summarization; //问题概述

    public CardImageInfor_problem_card(String title, String author, String summarization){
        this.title = title;
        this.author = author;
        this.summarization = summarization;
    }

    public String getTitle(){
        return title;
    }
    public String getAuthor(){
        return author;
    }
    public String getSummarization() { return summarization;}
    public void setTitle(String title){
        this.title = title;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public void setSummarization(String summarization) {this.summarization = summarization;}
}
