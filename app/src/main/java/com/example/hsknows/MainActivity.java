package com.example.hsknows;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.hsknows.accountFragment.AccountActivity;
import com.example.hsknows.cooperation.Cooperation;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

import cn.leancloud.LCUser;
import cn.leancloud.LeanCloud;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //登陸leancloud
        LeanCloud.initialize(this,
                "a47aIWgkSdQF6xSk2j5UPUJl-gzGzoHsz",
                "rQW7dM4UUMJauT3S7WAzIEl8",
                "https://a47aiwgk.lc-cn-n1-shared.com");

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
                    case R.id.cooperation:
                        Intent intent0 = new Intent(MainActivity.this, Cooperation.class);
                        startActivity(intent0);
                        break;
                    case R.id.account:
                        Intent intent1 = new Intent(MainActivity.this, AccountActivity.class);
                        startActivityForResult(intent1,4);
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 4:
                if (resultCode == RESULT_OK) {


                    //獲取用戶token用於登錄
                    String login_token= LCUser.getCurrentUser().getSessionToken();
                    Log.d("aaaaaaaaaa","***1"+login_token);


                    LCUser.becomeWithSessionTokenInBackground(login_token).subscribe(new Observer<LCUser>() {
                        public void onSubscribe(Disposable disposable) {}
                        public void onNext(LCUser user) {
                            // 修改 currentUser
                            LCUser.changeCurrentUser(user, true);

                        }
                        public void onError(Throwable throwable) {
                            // session token 无效
                        }
                        public void onComplete() {}
                    });


                }
                break;
            default:
                break;
        }

    }
}

