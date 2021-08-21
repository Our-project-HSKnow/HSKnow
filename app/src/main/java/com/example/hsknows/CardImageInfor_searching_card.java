package com.example.hsknows;


/*
这是在问题浏览列表的问题个体
 */

public class CardImageInfor_searching_card {
    private String title; //卡片标题
    private String author; //题目作者
    private String summarization; //问题概述
    private String time;
    private int reward;// 悬赏积分

    public CardImageInfor_searching_card(String title, String author, String summarization, String time, int reward){
        this.title = title;
        this.author = author;
        this.summarization = summarization;
        this.time = time;
        this.reward = reward;
    }

    public String getTitle(){
        return title;
    }
    public String getAuthor(){
        return author;
    }
    public String getSummarization() { return summarization;}
    public String getTime(){return time;}
    public int getReward(){return reward;}
    public void setTitle(String title){
        this.title = title;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public void setSummarization(String summarization) {this.summarization = summarization;}
    public void setTime(String time){this.time=time;}
    public void setReward(int reward){this.reward = reward;}
}
