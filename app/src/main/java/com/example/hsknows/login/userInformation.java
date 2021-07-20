package com.example.hsknows.login;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.LitePalSupport;

public class userInformation extends LitePalSupport implements Parcelable {
    private  int id;
    private  String username;
    private  String accountNumber;
    private  String password;
    private  String sex;

    public userInformation() {

    }


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

    public userInformation(Parcel in) {
        id = in.readInt();
        username = in.readString();
        accountNumber = in.readString();
        password = in.readString();
        sex = in.readString();
    }

    public static final Parcelable.Creator<userInformation> CREATOR = new Creator<userInformation>() {
        @Override
        public userInformation createFromParcel(Parcel in) {
            return new userInformation(in);
        }

        @Override
        public userInformation[] newArray(int size) {
            return new userInformation[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(username);
        dest.writeString(accountNumber);
        dest.writeString(password);
        dest.writeString(sex);
    }
}
