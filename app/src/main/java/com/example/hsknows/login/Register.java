package com.example.hsknows.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import org.litepal.LitePal;

import java.util.List;

//注册
public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register);
    //向数据库插入数据
        final EditText edit_name = (EditText) findViewById(R.id.edit_name);
        final EditText edit_account = (EditText) findViewById(R.id.edit_account);
        final EditText edit_pwd = (EditText) findViewById(R.id.edit_pwd);
        Button button_comfirm = (Button) findViewById(R.id.button_comfirm);
        Button button_return = (Button) findViewById(R.id.button_return);

        LitePal.getDatabase();

        final List<com.example.hsknows.login.userInformation> allUser = LitePal.findAll(com.example.hsknows.login.userInformation.class);
        button_comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.example.hsknows.login.userInformation user = new com.example.hsknows.login.userInformation();
                user.setUsername(edit_name.getText().toString());
                user.setAccountNumber(edit_account.getText().toString());
                user.setPassword(edit_pwd.getText().toString());
                user.save();
                Toast.makeText(com.example.hsknows.login.Register.this,"用户创建成功",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        button_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
