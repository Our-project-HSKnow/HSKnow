package com.example.hsknows.MenuFragment;
/**
 * 这个是发布问题的界面，包含标题，内容
 * 待开发：上传照片
 */

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.leancloud.LCObject;
import cn.leancloud.LCUser;
import cn.leancloud.LeanCloud;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class Post_A_Problem extends AppCompatActivity {
    TextView majorTitle;//主標題，顯示在學科名稱的位置上
    Button ButtonOfUpload;//上傳按鈕
    EditText EditTitle;//標題的EditText框
    EditText EditContent;//內容的EditText框


    String login_token;//這是個用作登陸的token


    //Button testsjk;//測試數據庫


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_a_question);

        /*接收上一個活動傳遞進來的學科名字，並打印在標題的textview的位置*/
        Intent intent1=getIntent();
        String SubjName=intent1.getStringExtra("SubjName");
        majorTitle = (TextView)findViewById(R.id.ShowSubject);
        majorTitle.setText(SubjName);//輸入標題（即學科名稱）

        //找到標題和內容的EditText
        EditTitle=(EditText)findViewById(R.id.ThisIsTitle);
        EditContent=(EditText)findViewById(R.id.ThisIsText);

        //登陸leancloud
        LeanCloud.initialize(this,
                "a47aIWgkSdQF6xSk2j5UPUJl-gzGzoHsz",
                "rQW7dM4UUMJauT3S7WAzIEl8",
                "https://a47aiwgk.lc-cn-n1-shared.com");

        ButtonOfUpload=(Button)findViewById(R.id.Upload);
        ButtonOfUpload.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //如果點擊了上傳按鈕
                String title=EditTitle.getText().toString();
                String content=EditContent.getText().toString();
                if(TextUtils.isEmpty(title) || TextUtils.isEmpty(content)){
                    //如果標題與內容至少有一個為空，則會認為不能上傳
                    Toast.makeText(Post_A_Problem.this,"标题或正文不能为空！",Toast.LENGTH_SHORT).show();
                }else{


                    LCUser currentUser = LCUser.getCurrentUser();

                    if (currentUser == null) {
                        Toast.makeText(Post_A_Problem.this,"請先登錄或註冊賬號！",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        //獲取用戶token用於登錄

                        login_token= LCUser.getCurrentUser().getSessionToken();


                        LCUser.becomeWithSessionTokenInBackground(login_token).subscribe(new Observer<LCUser>() {
                            public void onSubscribe(Disposable disposable) {}
                            public void onNext(LCUser user) {
                                // 修改 currentUser
                                LCUser.changeCurrentUser(user, true);
                                String uploader_account=user.getUsername();
                                String uploader_nickname= (String) user.get("user_nickname");

                                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                Calendar calendar = Calendar.getInstance();
                                Date date = calendar.getTime();
                                String current_time = sdf.format(date);

                                // 构建对象
                                LCObject todo = new LCObject("Question");

                                // 为属性赋值
                                todo.put("title", title);
                                todo.put("content", content);
                                todo.put("uploader_account",uploader_account);
                                todo.put("uploader_nickname",uploader_nickname);
                                todo.put("time",current_time);
                                todo.put("subject",SubjName);


                                // 将对象保存到云端
                                todo.saveInBackground().subscribe(new Observer<LCObject>() {
                                    public void onSubscribe(Disposable disposable) {}
                                    public void onNext(LCObject todo) {
                                        // 成功保存之后，执行其他逻辑
                                        Log.d("Post_A_Problem","Successfully uploaded!");
                                        Toast.makeText(Post_A_Problem.this,"上傳問題成功！",Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                    public void onError(Throwable throwable) {
                                        // 异常处理
                                    }
                                    public void onComplete() {}
                                });



                            }
                            public void onError(Throwable throwable) {
                                // session token 无效
                                Toast.makeText(Post_A_Problem.this,"請先登錄或註冊賬號！",Toast.LENGTH_SHORT).show();
                            }
                            public void onComplete() {}
                        });
                    }









                }


            }
        });




    }
}