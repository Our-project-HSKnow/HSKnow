package com.example.hsknows;


/*
这是在问题界面的评论个体
 */

import android.widget.Button;

public class CardImageInfor_problem_comment {
    private String time; //发布事件
    private String author; //题目作者
    private String content; //问题概述
    private int level;//楼数
    public Button jvbao;

    public CardImageInfor_problem_comment(String time, String author, String content,int level){
        this.time = time;
        this.author = author;
        this.content = content;
        this.level=level;
    }

    public String getTime(){
        return time;
    }
    public String getAuthor(){
        return author;
    }
    public String getContent() { return content;}
    public int getLevel(){return level;}
    public void setTime(String time){
        this.time = time;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public void setContent(String content) {this.content = content;}
    public void setLevel(int level) {this.level = level;}
}
