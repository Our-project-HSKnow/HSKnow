package com.example.hsknows.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import org.litepal.LitePal;

import java.util.List;

//登录界面  name pwd login register 控件的作用如后缀
public class MainActivity extends AppCompatActivity {

    public static final boolean[] state = {false};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        final EditText editText_name=(EditText) findViewById(R.id.edittext_name);
        final EditText editText_pwd=(EditText) findViewById(R.id.edittext_pwd);
        Button button_login = (Button) findViewById(R.id.button_login);
        Button button_register = (Button) findViewById(R.id.button_register);
        Button button_exit = (Button) findViewById(R.id.button_exit);
        LitePal.getDatabase();




        final List<com.example.hsknows.login.userInformation> allUser = LitePal.findAll(com.example.hsknows.login.userInformation.class);


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
                Intent intent = new Intent(com.example.hsknows.login.MainActivity.this, com.example.hsknows.login.Register.class);
                startActivity(intent);
            }
        });


        button_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }); //注册后回到此活动点击后此活动结束，但回到了注册活动
    }
}
