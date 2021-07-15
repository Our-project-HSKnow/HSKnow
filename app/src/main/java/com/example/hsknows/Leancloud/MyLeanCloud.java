package com.example.hsknows.Leancloud;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import cn.leancloud.LCObject;
import cn.leancloud.LeanCloud;

/*
wrk于2021.7.8添加，參照leancloud
 */

public class MyLeanCloud extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.test_leancloud);
        // 提供 this、App ID、App Key、Server Host 作为参数
        // 注意这里千万不要调用 cn.leancloud.core.LeanCloud 的 initialize 方法，否则会出现 NetworkOnMainThread 等错误。
        LeanCloud.initialize(this,
                "a47aIWgkSdQF6xSk2j5UPUJl-gzGzoHsz",
                "rQW7dM4UUMJauT3S7WAzIEl8",
                "https://a47aiwgk.lc-cn-n1-shared.com");

        //下面三行代碼用於測試Leancloud是否好使
        LCObject testObject = new LCObject("TestObject");
        testObject.put("words", "成功了嗎?");
        testObject.saveInBackground().subscribe();

        finish();
    }
}
