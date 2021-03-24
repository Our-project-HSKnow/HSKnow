package com.example.hsknows;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //主界面及底部导航栏
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this, R.id.fragment);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(bottomNavigationView.getMenu()).build();
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                MenuItem home = bottomNavigationView.getMenu().findItem(R.id.home);
                home.setIcon(R.drawable.ic_buttom_home);
                MenuItem menu = bottomNavigationView.getMenu().findItem(R.id.menu);
                menu.setIcon(R.drawable.ic_buttom_menu);
                MenuItem account = bottomNavigationView.getMenu().findItem(R.id.account);
                account.setIcon(R.drawable.ic_buttom_account);
                switch (item.getItemId()){
                    case R.id.home:
                        bottomNavigationView.setVisibility(View.VISIBLE);
                        item.setIcon(R.drawable.ic_buttom_home_fill);
                        navController.navigate(R.id.home);
                        break;
                    case R.id.menu:
                        bottomNavigationView.setVisibility(View.VISIBLE);
                        item.setIcon(R.drawable.ic_buttom_menu_fill);
                        navController.navigate(R.id.menu);
                        break;
                    case R.id.account:
                        Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                        startActivity(intent);
                        break;
                    default:
                }
                return false;
            }
        });

        Button bottom_button = (Button)findViewById(R.id.bottom_button);
        bottom_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (bottomNavigationView.getVisibility() == View.VISIBLE) {
                    bottomNavigationView.setVisibility(View.GONE);
                    bottom_button.setForeground(getDrawable(R.drawable.ic_account_back_up));
                }else {
                    bottomNavigationView.setVisibility(View.VISIBLE);
                    bottom_button.setForeground(getDrawable(R.drawable.ic_account_back));
                }
            }
        });

    }
}

