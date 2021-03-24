package com.example.hsknows;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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
                startActivity(intent);
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
                        navController.navigate(R.id.bookmark);
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
}