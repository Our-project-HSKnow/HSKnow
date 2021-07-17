package com.example.hsknows.cooperation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

public class Cooperation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cooperation_main);


        //主界面及底部导航栏
        BottomNavigationView bottomNavigationView = findViewById(R.id.cooperation_bottomNavigationView);
        NavController navController = Navigation.findNavController(this, R.id.fragment2);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(bottomNavigationView.getMenu()).build();
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                MenuItem project = bottomNavigationView.getMenu().findItem(R.id.project);
                project.setIcon(R.drawable.ic_buttom_home);
                MenuItem mine = bottomNavigationView.getMenu().findItem(R.id.mine);
                mine.setIcon(R.drawable.ic_buttom_menu);

                switch (item.getItemId()){
                    case R.id.project:
                        bottomNavigationView.setVisibility(View.VISIBLE);
                        item.setIcon(R.drawable.ic_buttom_home_fill);
                        navController.navigate(R.id.project);
                        break;
                    case R.id.mine:
                        bottomNavigationView.setVisibility(View.VISIBLE);
                        item.setIcon(R.drawable.ic_buttom_menu_fill);
                        Intent intent = new Intent(Cooperation.this, Cooperation_Module.class);
                        startActivity(intent);
                        navController.navigate(R.id.mine);
                        break;
                }
                return false;
            }
        });
    }


}