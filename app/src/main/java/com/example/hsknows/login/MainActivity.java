package com.example.hsknows.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        motionLayout = (MotionLayout)findViewById(R.id.log_motionlayout);

        close_button = (Button)findViewById(R.id.log_button_exit);

        //登陆界面
        editText_name=(EditText) findViewById(R.id.edittext_name);
        editText_pwd=(EditText) findViewById(R.id.edittext_pwd);
        button_login = (Button) findViewById(R.id.button_login);
        button_tran_log = (FloatingActionButton) findViewById(R.id.button_tran_log);
        login_card = (CardView)findViewById(R.id.log_card);
        log_username = (TextView)findViewById(R.id.log_username);
        log_passward = (TextView)findViewById(R.id.log_password);

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
                com.example.hsknows.login.userInformation user = new com.example.hsknows.login.userInformation();
                user.setUsername(register_name.getText().toString());
                user.setAccountNumber(register_account.getText().toString());
                user.setPassword(register_password.getText().toString());
                user.save();
                Toast.makeText(com.example.hsknows.login.MainActivity.this, "用户创建成功", Toast.LENGTH_SHORT).show();
                motionLayout.transitionToStart();
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
