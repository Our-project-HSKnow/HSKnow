package com.example.hsknows.Leancloud;

import android.util.Log;

import java.util.Arrays;

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

    /**
     * 尋找與itself有關的所有評論
     * @param tree 是二維數組  儲存結點和它的父親
     * @param valid_number 是數組的寬度，有多少個節點
     * @param itself 要尋找的結點
     * @return 以數組形式返回與當前節點相關的結點*/
    public static int[] FindAllRelatedComments(int[][] tree,int valid_number,int itself){
        int i=0,j;
        int answer[]=new int[valid_number];
        for(int k=0;k<valid_number;k++){ Log.d("bbb1","  "+answer[k]); }

        /*首先，尋找itself的所有父輩*/
        int another_itself=itself;
        while(another_itself != 0){
            Log.d("ccccc",""+another_itself);
            answer[i]=another_itself;
            another_itself=tree[1][another_itself-1];
            i++;
        }
        for(int k=0;k<valid_number;k++){ Log.d("bbb2","  "+answer[k]); }

        /*然後，尋找itself的所有子輩*/
        for(j=itself+1;j<valid_number;j++){
            if(IfFather(tree,j,itself)){
                answer[i]=j;
                i++;
            }
        }

        for(int k=0;k<valid_number;k++){ Log.d("bbb3","  "+answer[k]); }

        for(;i<valid_number;i++){
            answer[i]=valid_number+1;
        }

        for(int k=0;k<valid_number;k++){ Log.d("bbb4","  "+answer[k]); }

        Arrays.sort(answer);

        for(int k=0;k<valid_number;k++){ Log.d("bbb5","  "+answer[k]); }
        return answer;

    }

    public static boolean IfFather(int[][] tree,int curr,int itself){
        while(curr!=itself && curr!=0){
            curr=tree[1][curr-1];
        }
        if(curr==itself){return true;}
        else{return false;}
    }

}
