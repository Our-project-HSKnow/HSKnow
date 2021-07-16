package com.example.hsknows.login;

import android.animation.Animator;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.motion.widget.MotionLayout;

import com.example.hsknows.Leancloud.AccountSettings;
import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.litepal.LitePal;

import java.util.List;

import cn.leancloud.LCObject;
import cn.leancloud.LeanCloud;

//登录界面  name pwd login register 控件的作用如后缀
public class MainActivity extends AppCompatActivity {

    public static final boolean[] state = {false, true};

    private Animator mShowAnim;     // 显示视图的揭露动画
    private Animator mHideAnim;     // 隐藏视图的揭露动画

    Button button_login;
    Button button_register;
    Button close_button;
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

    public boolean ifLogin;//是否已經登錄

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        motionLayout = (MotionLayout)findViewById(R.id.log_motionlayout);

        close_button = (Button)findViewById(R.id.log_button_exit);


        //如果已經登錄了，就不用在初始化了，但此處邏輯應當再完善----2021.7.12 wrk
        ifLogin=false;
        if(!ifLogin){
            LeanCloud.initialize(this,
                    "a47aIWgkSdQF6xSk2j5UPUJl-gzGzoHsz",
                    "rQW7dM4UUMJauT3S7WAzIEl8",
                    "https://a47aiwgk.lc-cn-n1-shared.com");
            ifLogin=true;
        }


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

        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(com.example.hsknows.login.userInformation user:allUser){
                    Log.d("MainActivity",user.getUsername());
                    Log.d("MainActivity",user.getPassword());
                    Log.d("MainActivity",editText_name.getText().toString());
                    Log.d("MainActivity",editText_pwd.getText().toString());
                    if(user.getUsername().equals(editText_name.getText().toString())&&user.getPassword().equals(editText_pwd.getText().toString())){
                        Toast.makeText(com.example.hsknows.login.MainActivity.this,"登陆成功",
                                Toast.LENGTH_SHORT).show();
                        state[0] = true;
                        finish();
                    }
                    else if(user.getAccountNumber().equals(editText_name.getText().toString())&&user.getPassword().equals(editText_pwd.getText().toString())){
                        Toast.makeText(com.example.hsknows.login.MainActivity.this,"登陆成功",
                                Toast.LENGTH_SHORT).show();
                        state[0] = true;
                        finish();
                    }
                }
            }
        });

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //初始化LeanCloud

                userInformation user = new userInformation();
                user.setUsername(register_name.getText().toString());
                user.setAccountNumber(register_account.getText().toString());
                user.setPassword(register_password.getText().toString());
                user.save();
                String rgsr_name=register_name.getText().toString();
                String rgsr_account=register_account.getText().toString();
                String rgsr_password=register_password.getText().toString();
                Number curr_account=Integer.parseInt(rgsr_account);
                if(TextUtils.isEmpty(rgsr_name) || TextUtils.isEmpty(rgsr_account) || TextUtils.isEmpty(rgsr_password)){
                    Toast.makeText(MainActivity.this, "账号内容不能为空", Toast.LENGTH_SHORT).show();
                }
                else if(! rgsr_account.matches("\\p{Digit}+")){
                    //如果賬號不是純數字
                    //上述用正則表達式判斷
                    Toast.makeText(MainActivity.this, "賬號account只能使用純數字！", Toast.LENGTH_SHORT).show();
                }
                else if(AccountSettings.IfExist(curr_account)){
                    //如果賬號已經存在了
                    Toast.makeText(MainActivity.this, "該賬號已被註冊，請直接登錄或換一個賬號註冊。", Toast.LENGTH_SHORT).show();
                }
                else{


                    LCObject new_user=new LCObject("HSKnowsUser");
                    new_user.put("account",Integer.parseInt(rgsr_account));
                    new_user.put("name",rgsr_name);
                    new_user.put("password",rgsr_password);
                    new_user.saveInBackground().subscribe();

                    Toast.makeText(MainActivity.this, "用户创建成功", Toast.LENGTH_SHORT).show();
                    motionLayout.transitionToStart();
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


}
