package com.example.hsknows.login;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.litepal.LitePal;

import java.util.List;

//登录界面  name pwd login register 控件的作用如后缀
public class MainActivity extends AppCompatActivity {

    public static final boolean[] state = {false, true};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        Button close_button = (Button)findViewById(R.id.log_button_exit);

        //登陆界面
        final EditText editText_name=(EditText) findViewById(R.id.edittext_name);
        final EditText editText_pwd=(EditText) findViewById(R.id.edittext_pwd);
        Button button_login = (Button) findViewById(R.id.button_login);
        FloatingActionButton button_tran_log = (FloatingActionButton) findViewById(R.id.button_tran_log);
        Button button_exit = (Button) findViewById(R.id.log_button_exit);
        CardView login_card = (CardView)findViewById(R.id.log_card);

        //注册界面
        final EditText register_name = (EditText) findViewById(R.id.register_name);
        final EditText register_account = (EditText) findViewById(R.id.register_account);
        final EditText register_password = (EditText) findViewById(R.id.register_password);
        Button button_register = (Button) findViewById(R.id.button_registe);

        //动画
        ObjectAnimator objectAnimator_button = ObjectAnimator.ofFloat(button_tran_log, "rotation", 225f);
        objectAnimator_button.setDuration(800);
        ObjectAnimator objectAnimator_layout = ObjectAnimator.ofFloat(login_card, "alpha", 1f, 0f);
        objectAnimator_layout.setDuration(800);

        //反转动画
        ObjectAnimator reversal_objectAnimator_button = ObjectAnimator.ofFloat(button_tran_log, "rotation", -225f);
        reversal_objectAnimator_button.setDuration(800);
        ObjectAnimator reversal_objectAnimator_layout = ObjectAnimator.ofFloat(login_card, "alpha", 0f, 1f);
        reversal_objectAnimator_layout.setDuration(800);

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

        button_tran_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state[1] == true){
                    objectAnimator_button.start();
                    objectAnimator_layout.start();
                    state[1] = false;
                }else{
                    reversal_objectAnimator_button.start();
                    reversal_objectAnimator_layout.start();
                    state[1] = true;
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

                reversal_objectAnimator_button.start();
                reversal_objectAnimator_layout.start();
            }
        });

    }
}
