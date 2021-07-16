package com.example.hsknows.Leancloud;

/*
    這個類用來處理賬號——wrk 2021.7.3
*/

import android.util.Log;

import java.util.List;

import cn.leancloud.LCObject;
import cn.leancloud.LCQuery;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class AccountSettings {

    public AccountSettings(){
        //構造方法

    }

    public static boolean IfExist(Number number){
        //注意！外界調取這個方法只能調用它，不可調用下面那個
        int[] counts={0};
        Log.d("ASs","aaaaaaaaaaaaaaaaaaaa11111");

        counts[0]=GetAccountCounts(number,counts);

        boolean b=false;

        if(counts[0] == 0){ b=false; }
        else{ b=true; }
        return b;

    }

    public static int GetAccountCounts(Number number,int[] counts){
        //判斷當前的賬號是否已經存在

        int num=number.intValue();

        Log.d("ASs","aaaaaaaaaaaaaaaaaaaa22222");
        {
            LCQuery<LCObject> query = new LCQuery<>("HSKnowsUser");
            query.whereEqualTo("account", num);
            query.findInBackground().subscribe(new Observer<List<LCObject>>() {
                public void onSubscribe(Disposable disposable) {}
                public void onNext(List<LCObject> HSKUsers) {
                    Log.d("ASs","----"+HSKUsers.size());
                }
                public void onError(Throwable throwable) {}
                public void onComplete() {}
            });
        }
        Log.d("ASs","aaaaaaaaaaaaaaaaaaaa33333");
        return counts[0];
    }
}
