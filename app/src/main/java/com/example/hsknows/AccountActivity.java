package com.example.hsknows;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

public class AccountActivity extends AppCompatActivity {
    boolean nightmode = false;

    private ImageView imageView_historyarrow;
    private ImageView imageView_messagearrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_fragment);

        //返回主界面按钮
        Button button = (Button)findViewById(R.id.account_back_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //底部栏
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView_account);
        NavController navController = Navigation.findNavController(this, R.id.fragment_account);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(bottomNavigationView.getMenu()).build();
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nightmode:
                        MenuItem nightmode_item = bottomNavigationView.getMenu().findItem(R.id.nightmode);
                        if (nightmode == true){
                            nightmode_item.setIcon(R.drawable.ic_nightmode_sun);
                            nightmode = false;
                            Log.d("AccountActivity", "false nightmode");
                        }
                        else{
                            nightmode_item.setIcon(R.drawable.ic_nightmode_moon);
                            nightmode = true;
                        }
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