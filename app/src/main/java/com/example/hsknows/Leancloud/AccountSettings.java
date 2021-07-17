package com.example.hsknows.Leancloud;

/*
    這個類用來處理賬號——wrk 2021.7.3
*/

import android.util.Log;

import java.util.List;

import cn.leancloud.LCObject;
import cn.leancloud.LCQuery;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


public class AccountSettings {

    public AccountSettings(){
        //構造方法

    }



    public static boolean IfExist(Number number){
        //判斷當前的賬號是否已經存在
        final boolean[] ifSame = {false};
        int num=number.intValue();

        Log.d("ASs","aaaaaaaaaaaaaaaaaaaa22222");
        {
            LCQuery<LCObject> query = new LCQuery<>("HSKnowsUser");
            query.whereEqualTo("account", num);
            query.findInBackground().subscribe(new Observer<List<LCObject>>() {


                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onNext(@NonNull List<LCObject> HSKUsers) {
                        Log.d("ASs","----"+HSKUsers.size());
                        ifSame[0] =true;
                }

                @Override
                public void onError(@NonNull Throwable e) {

                }

                @Override
                public void onComplete() {

                }

            });
        }
        Log.d("ASs","aaaaaaaaaaaaaaaaaaaa33333");
        return ifSame[0];
    }
}
