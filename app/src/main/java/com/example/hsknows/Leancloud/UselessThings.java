package com.example.hsknows.Leancloud;

public class UselessThings {
    /*
    wrk創建，專門用於一些小功能
     */
    /**
     *找到這個評論的父親
     * 評論樣式應該是 回復xx樓（如回復3樓）這時就要找到這個xx（如例子中的3）
     */
    public static int FindItsFather(String str){
        int begin=str.indexOf("复");
        int end=str.indexOf("楼");
        String str1=str.substring(begin+1,end);
        int father=0;
        try{
            father=Integer.parseInt(str1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return father;

    }
}
