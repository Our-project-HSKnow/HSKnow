package com.example.hsknows;

public class CardImageInfor_cooperation_project {
    private String title; //卡片标题
    private String author; //项目作者

    public CardImageInfor_cooperation_project(String title, String author){
        this.title = title;
        this.author = author;
    }

    public String getTitle(){
        return title;
    }
    public String getAuthor(){
        return author;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setAuthor(String author){
        this.author = author;
    }

}
