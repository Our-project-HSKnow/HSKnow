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
    Button button_exit;
    Button close_button;
    FloatingActionButton button_tran_log;
    CardView login_card;
    CardView register_card;

    EditText register_name;
    EditText register_account;
    EditText register_password;

    TextView username;
    TextView account;
    TextView password;
    TextView textView_username;
    TextView textView_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        Button close_button = (Button)findViewById(R.id.log_button_exit);
        MotionLayout motionLayout = (MotionLayout)findViewById(R.id.log_motionlayout);
        int status = 0;

        //登陆界面
        final EditText editText_name=(EditText) findViewById(R.id.edittext_name);
        final EditText editText_pwd=(EditText) findViewById(R.id.edittext_pwd);
        button_login = (Button) findViewById(R.id.button_login);
        button_tran_log = (FloatingActionButton) findViewById(R.id.button_tran_log);
        button_exit = (Button) findViewById(R.id.log_button_exit);
        login_card = (CardView)findViewById(R.id.log_card);
        textView_username = (TextView)findViewById(R.id.textView_username);
        textView_password = (TextView)findViewById(R.id.textView_password);
        //注册界面
        register_card = (CardView)findViewById(R.id.register_card);
        register_name = (EditText) findViewById(R.id.register_name);
        register_account = (EditText) findViewById(R.id.register_account);
        register_password = (EditText) findViewById(R.id.register_password);
        button_register = (Button) findViewById(R.id.button_registe);
        username = (TextView)findViewById(R.id.username);
        account = (TextView)findViewById(R.id.account);
        password = (TextView)findViewById(R.id.password);


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
                    ChangeVisible(true);
                }
                else if (register_card.getAlpha() == 1)
                {
                    ChangeVisible(false);
                }
            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int i, int i1, float v) {

            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int i) {
                if (register_card.getAlpha() == 0)
                {
                    ChangeVisible(false);
                }
                else if (register_card.getAlpha() == 1)
                {
                    ChangeVisible(true);
                }
            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int i, boolean b, float v) {

            }
        });

    }
    public void ChangeVisible (boolean visible)
    {
        if (visible == true)
        {
            register_card.setVisibility(View.VISIBLE);
            register_account.setVisibility(View.VISIBLE);
            register_name.setVisibility(View.VISIBLE);
            register_password.setVisibility(View.VISIBLE);
            button_register.setVisibility(View.VISIBLE);
            username.setVisibility(View.VISIBLE);
            account.setVisibility(View.VISIBLE);
            password.setVisibility(View.VISIBLE);
        }
        else
        {
            register_card.setVisibility(View.GONE);
            register_account.setVisibility(View.GONE);
            register_name.setVisibility(View.GONE);
            register_password.setVisibility(View.GONE);
            button_register.setVisibility(View.GONE);
            username.setVisibility(View.GONE);
            account.setVisibility(View.GONE);
            password.setVisibility(View.GONE);
        }
    }


}
