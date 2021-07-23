package com.example.hsknows.login;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.motion.widget.MotionLayout;

import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.litepal.LitePal;

import java.util.List;

import cn.leancloud.LCUser;
import cn.leancloud.LeanCloud;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

//登录界面  name pwd login register 控件的作用如后缀
public class MainActivity extends AppCompatActivity {

    public static final boolean[] state = {false, true};

    private Animator mShowAnim;     // 显示视图的揭露动画
    private Animator mHideAnim;     // 隐藏视图的揭露动画

    Button button_login;
    Button button_register;
    Button close_button;
    Button log_out_button;
    FloatingActionButton button_tran_log;
    CardView login_card;
    CardView register_card;
    EditText register_name;
    EditText register_account;
    EditText register_password;
    EditText editText_name;
    EditText editText_pwd;

    MotionLayout motionLayout;

    TextView account;
    TextView username;
    TextView passward;
    TextView log_username;
    TextView log_passward;

    public boolean ifLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        motionLayout = (MotionLayout)findViewById(R.id.log_motionlayout);
        close_button = (Button)findViewById(R.id.log_button_exit);
        log_out_button=(Button)findViewById(R.id.log_out);

        LeanCloud.initialize(this,
                "a47aIWgkSdQF6xSk2j5UPUJl-gzGzoHsz",
                "rQW7dM4UUMJauT3S7WAzIEl8",
                "https://a47aiwgk.lc-cn-n1-shared.com");

        //登陆界面
        editText_name=(EditText) findViewById(R.id.edittext_name);
        editText_pwd=(EditText) findViewById(R.id.edittext_pwd);
        button_login = (Button) findViewById(R.id.button_login);
        button_tran_log = (FloatingActionButton) findViewById(R.id.button_tran_log);
        login_card = (CardView)findViewById(R.id.log_card);
        log_username = (TextView)findViewById(R.id.log_username);
        log_passward = (TextView)findViewById(R.id.log_passward);

        //注册界面
        register_card = (CardView)findViewById(R.id.register_card);
        register_name = (EditText) findViewById(R.id.register_name);
        register_account = (EditText) findViewById(R.id.register_account);
        register_password = (EditText) findViewById(R.id.register_password);
        button_register = (Button) findViewById(R.id.button_registe);
        username = (TextView)findViewById(R.id.username);
        account = (TextView)findViewById(R.id.account);
        passward = (TextView)findViewById(R.id.password);


        LitePal.getDatabase();

        final List<com.example.hsknows.login.userInformation> allUser = LitePal.findAll(com.example.hsknows.login.userInformation.class);


        LCUser currentUser = LCUser.getCurrentUser();
        if (currentUser != null) { ifLogin=true; }
        else { ifLogin=false; }


        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.putExtra("ifLogin",ifLogin);
                setResult(RESULT_OK,intent1);
                finish();
            }
        });

        log_out_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LCUser currentUser = LCUser.getCurrentUser();
                if (currentUser == null) {
                    Toast.makeText(MainActivity.this,"請先登錄或註冊賬號！",Toast.LENGTH_SHORT).show();

                } else{
                    LCUser.logOut();
                    Toast.makeText(MainActivity.this,"退出賬號成功！",Toast.LENGTH_SHORT).show();
                }
                ifLogin=false;

            }
        });




        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LCUser currentUser = LCUser.getCurrentUser();

                String lgn_name=editText_name.getText().toString();
                String lgn_pwd=editText_pwd.getText().toString();
                if(TextUtils.isEmpty(lgn_name) || TextUtils.isEmpty(lgn_pwd)){
                    Toast.makeText(MainActivity.this, "账号内容不能为空", Toast.LENGTH_SHORT).show();
                }
                else if(currentUser != null){
                    //如果當前賬號尚未退出
                    Toast.makeText(MainActivity.this, "請先退出當前賬號！", Toast.LENGTH_SHORT).show();
                }
                else {
                    LCUser.logIn(lgn_name, lgn_pwd).subscribe(new Observer<LCUser>() {
                        public void onSubscribe(Disposable disposable) {}
                        public void onNext(LCUser HSKUser) {
                            // 登录成功
                            Toast.makeText(MainActivity.this, "登陸成功", Toast.LENGTH_SHORT).show();

                            ifLogin=true;
                            Intent intent1=new Intent();
                            intent1.putExtra("ifLogin",ifLogin);
                            setResult(RESULT_OK,intent1);
                            finish();
                        }
                        public void onError(Throwable throwable) {
                            // 登录失败（可能是密码错误）
                            Toast.makeText(MainActivity.this, "賬號或密碼錯誤", Toast.LENGTH_SHORT).show();
                        }
                        public void onComplete() {}
                    });

                }

            }
        });

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //初始化LeanCloud

                LCUser currUser = LCUser.getCurrentUser();

                String rgsr_name=register_name.getText().toString();
                String rgsr_account=register_account.getText().toString();
                String rgsr_password=register_password.getText().toString();

                if(TextUtils.isEmpty(rgsr_name) || TextUtils.isEmpty(rgsr_account) || TextUtils.isEmpty(rgsr_password)){
                    Toast.makeText(MainActivity.this, "账号内容不能为空", Toast.LENGTH_SHORT).show();
                }
                else if(currUser != null){
                    //如果當前賬號尚未退出
                    Toast.makeText(MainActivity.this, "請先退出當前賬號！", Toast.LENGTH_SHORT).show();
                }
                else if(! rgsr_account.matches("\\p{Digit}+")){
                    //如果賬號不是純數字
                    //上述用正則表達式判斷
                    Toast.makeText(MainActivity.this, "賬號account只能使用純數字！", Toast.LENGTH_SHORT).show();
                }
                else{
                    //创建实例
                    //這裡的setUsername實際上是賬號
                    LCUser HSKUser=new LCUser();

                    HSKUser.setUsername(rgsr_account);
                    HSKUser.setPassword(rgsr_password);
                    HSKUser.put("user_nickname",rgsr_name);

                    HSKUser.signUpInBackground().subscribe(new Observer<LCUser>() {
                        public void onSubscribe(Disposable disposable) {}
                        public void onNext(LCUser HSKUser) {
                            // 注册成功
                            Toast.makeText(MainActivity.this, "用户创建成功", Toast.LENGTH_SHORT).show();

                            ifLogin=true;
                            //傳入intent
                            Intent intent1=new Intent();
                            intent1.putExtra("ifLogin",ifLogin);
                            intent1.putExtra("Account",rgsr_account);
                            intent1.putExtra("Password",rgsr_password);
                            setResult(RESULT_OK,intent1);
                            finish();
                        }
                        public void onError(Throwable throwable) {
                            // 注册失败（通常是因为用户名已被使用）
                            Toast.makeText(MainActivity.this, "賬號已被使用！請直接登錄或更換賬號以註冊。", Toast.LENGTH_SHORT).show();
                        }
                        public void onComplete() {}
                    });
                    //motionLayout.transitionToStart();
                }

            }
        });

        motionLayout.setTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int i, int i1) {
                if (register_card.getAlpha() == 0)
                {
                    ChangeVisibility(true);
                }
                else if(register_card.getAlpha() == 1)
                {
                    ChangeVisibility(false);
                }
            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int i, int i1, float v) {

            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int i) {
                if (register_card.getAlpha() == 0)
                {
                    ChangeVisibility(false);
                }
                else if(register_card.getAlpha() == 1)
                {
                    ChangeVisibility(true);
                }
            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int i, boolean b, float v) {

            }
        });
    }
    public void ChangeVisibility(boolean visible)
    {
        if (visible == true)
        {
            username.setVisibility(View.VISIBLE);
            account.setVisibility(View.VISIBLE);
            passward.setVisibility(View.VISIBLE);
            register_card.setVisibility(View.VISIBLE);
            register_name.setVisibility(View.VISIBLE);
            register_account.setVisibility(View.VISIBLE);
            register_password.setVisibility(View.VISIBLE);
            button_register.setVisibility(View.VISIBLE);

            login_card.setVisibility(View.GONE);
            log_username.setVisibility(View.GONE);
            log_passward.setVisibility(View.GONE);
            button_login.setVisibility(View.GONE);
            editText_name.setVisibility(View.GONE);
            editText_pwd.setVisibility(View.GONE);
        }
        else
        {
            username.setVisibility(View.GONE);
            account.setVisibility(View.GONE);
            passward.setVisibility(View.GONE);
            register_card.setVisibility(View.GONE);
            register_name.setVisibility(View.GONE);
            register_account.setVisibility(View.GONE);
            register_password.setVisibility(View.GONE);
            button_register.setVisibility(View.GONE);

            login_card.setVisibility(View.VISIBLE);
            log_username.setVisibility(View.VISIBLE);
            log_passward.setVisibility(View.VISIBLE);
            button_login.setVisibility(View.VISIBLE);
            editText_name.setVisibility(View.VISIBLE);
            editText_pwd.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent1=new Intent();
        intent1.putExtra("ifLogin",ifLogin);
        setResult(RESULT_OK,intent1);
        finish();
    }
}
