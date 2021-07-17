package com.example.hsknows.MenuFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import com.example.myapplication.R;

public class Menu_main_Activity extends AppCompatActivity {

    ImageButton imgbtn1;//这是那个标着加号的button，点这个发表问题



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = LayoutInflater.from(this);

        setContentView(R.layout.menu_main_activity);
        MotionLayout main_layout = (MotionLayout)findViewById(R.id.menu_main_motionlayout);
        ScrollView menu_main_scrollview = (ScrollView)findViewById(R.id.menu_main_recycleview);
        ImageButton back_button = (ImageButton)findViewById(R.id.menu_main_back_button);
        LinearLayout menu_main_scrollview_LinearLayout = (LinearLayout)findViewById(R.id.menu_main_recycleview);



        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Menu_main_Activity.this, "Back", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        /*接收上一个活动传递进来的学科名字，并打印在标题的textview的位置*/
        Intent intent1=getIntent();
        String SubjName=intent1.getStringExtra("SubjName");

        TextView SubjTitle;
        SubjTitle = (TextView)findViewById(R.id.menu_title);
        SubjTitle.setText(SubjName);




        imgbtn1 = (ImageButton)findViewById(R.id.menu_main_postnew_button);//imgbtn1点进去是发布一个问题
        imgbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Menu_main_Activity.this,Post_A_Problem.class);
                intent1.putExtra("SubjName",SubjName);
                startActivity(intent1);

            }

        });


        menu_main_scrollview_LinearLayout.addView(inflater.inflate(R.layout.menu_main_activity_card, null));
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1 && resultCode == RESULT_OK) {

            //表示傳回來了標題與內容
            String Title = data.getStringExtra("BackTitle");
            String Content = data.getStringExtra("BackContent");
            Log.d("Menu_main_Activity", Title);
            Log.d("Menu_main_Activity", Content);
        }
    }
}

