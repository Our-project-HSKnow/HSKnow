package com.example.hsknows.accountFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.hsknows.login.userInformation;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

public class AccountActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_fragment);

        //返回主界面按钮
        Button button = (Button) findViewById(R.id.account_back_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView account_image = (ImageView)findViewById(R.id.account_image);
        account_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AccountActivity.this, "Login", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AccountActivity.this, com.example.hsknows.login.MainActivity.class);

                //該活動要返回一個對象，為一個用戶

                startActivityForResult(intent,1);

            }

        });

        ConstraintLayout Account_header = findViewById(R.id.account_header);

        //底部栏
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView_account);
        NavController navController = Navigation.findNavController(this, R.id.account_center);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(bottomNavigationView.getMenu()).build();
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.setting:
                        navController.navigate(R.id.setting);
                        break;
                    case R.id.bookmark:
                        navController.navigate(R.id.account_history_bookmark);
                        break;
                    case R.id.tool:
                        navController.navigate(R.id.tool);
                        break;
                    case R.id.message:
                        navController.navigate(R.id.message);
                        break;
                    case R.id.account_main:
                        navController.navigate(R.id.account_main);
                        break;
                    default:
                }
                return false;
            }
        });


    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1 && resultCode == RESULT_OK) {
            //頂部
            TextView nameplace=(TextView)findViewById(R.id.textView_name) ;

            userInformation HSKUser=new userInformation();

            String name=data.getStringExtra("name");
            String pwd=data.getStringExtra("password");
            String account=data.getStringExtra("account");
            //名字會顯示在頂部
            nameplace.setText(name);
        }
    }
}