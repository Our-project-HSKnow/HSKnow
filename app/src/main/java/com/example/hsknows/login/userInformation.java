package com.example.hsknows.login;

import org.litepal.crud.LitePalSupport;

public class userInformation extends LitePalSupport {
    private  int id;
    private  String username;
    private  String accountNumber;
    private  String password;
    private  String sex;


    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id=id;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccountNumber(){
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber){
        this.accountNumber=accountNumber;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public String getPassword(){
        return password;
    }

    public void setSex(String sex){
        this.sex=sex;
    }

    public String getSex(){
        return sex;
    }
}
