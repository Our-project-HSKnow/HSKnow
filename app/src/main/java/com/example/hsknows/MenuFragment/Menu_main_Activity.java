package com.example.hsknows.MenuFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hsknows.CardImageInfor_problem_card;
import com.example.hsknows.MyRecyclerAdapter_problem_card;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.LCObject;
import cn.leancloud.LCQuery;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class Menu_main_Activity extends AppCompatActivity {

    ImageButton imgbtn1;//这是那个标着加号的button，点这个发表问题

    RecyclerView recyclerView;
    List<CardImageInfor_problem_card> list = new ArrayList<>();
    MyRecyclerAdapter_problem_card myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = LayoutInflater.from(this);

        setContentView(R.layout.menu_main_activity);
        MotionLayout main_layout = (MotionLayout)findViewById(R.id.menu_main_motionlayout);

        /*接收上一个活动传递进来的学科名字，并打印在标题的textview的位置*/
        Intent intent1=getIntent();
        String SubjName=intent1.getStringExtra("SubjName");

        TextView SubjTitle;
        SubjTitle = (TextView)findViewById(R.id.menu_title);
        SubjTitle.setText(SubjName);

        ImageButton back_button = (ImageButton)findViewById(R.id.menu_main_back_button);
        recyclerView = (RecyclerView)findViewById(R.id.menu_main_recycleview);
        initDatas(SubjName);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Menu_main_Activity.this, "Back", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        imgbtn1 = (ImageButton)findViewById(R.id.menu_main_postnew_button);//imgbtn1点进去是发布一个问题
        imgbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Menu_main_Activity.this,Post_A_Problem.class);
                intent1.putExtra("SubjName",SubjName);
                startActivity(intent1);

            }

        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1 && resultCode == RESULT_OK) {

/*
            //表示傳回來了標題與內容
            String Title = data.getStringExtra("BackTitle");
            String Content = data.getStringExtra("BackContent");
            Log.d("Menu_main_Activity", Title);
            Log.d("Menu_main_Activity", Content);

 */
        }
    }

    private void initDatas(String nameOfSubj) {
        //添加测试数据

        LCQuery<LCObject> query = new LCQuery<>("Question");
        query.whereEqualTo("subject", nameOfSubj);
        query.findInBackground().subscribe(new Observer<List<LCObject>>() {
            public void onSubscribe(Disposable disposable) {}
            public void onNext(List<LCObject> questions) {
                Log.d("Menu_main_Activity", "aaa---"+String.valueOf(questions.size()));
                for(int i = 0; i < questions.size(); i++)
                {
                    String title= (String) questions.get(i).get("title");
                    String content= (String) questions.get(i).get("title");
                    String summarization;
                    if(content.length() < 20){
                        summarization=content;
                    }
                    else{
                        summarization=content.substring(0,20)+"...";
                    }
                    String uploader_nickname= (String) questions.get(i).get("uploader_nickname");

                    //list.add(new CardImageInfor_problem_card(title, uploader_nickname, summarization));
                    addData(title, uploader_nickname, summarization);

                    //list.add(new CardImageInfor_problem_card("Title 3", "BLGS", "一阶导， 二阶导， 三阶导， 四阶导， 导导导导"));
                }
            }
            public void onError(Throwable throwable) {}
            public void onComplete() {}
        });

        /*
        for(int i = 0; i < 20; i++)
        {
            list.add(new CardImageInfor_problem_card("Title 3", "BLGS", "一阶导， 二阶导， 三阶导， 四阶导， 导导导导"));
        }
         */

        //设置列表显示方式
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置列表默认动画效果
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //绑定适配器
        myAdapter = new MyRecyclerAdapter_problem_card(list);
        recyclerView.setAdapter(myAdapter);

        //列表点击事件
        myAdapter.setOnItemClickLitener(new MyRecyclerAdapter_problem_card.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent =new Intent(Menu_main_Activity. this,Menu_problem.class);
                intent.putExtra("problem", position);
                startActivity(intent);//启动Activity
                finish();
            }
        });

    }
    //添加问题
    public void addData(String title, String author, String summarization){
        myAdapter.addData(title, author, summarization);
    }
    //删除所有问题
    public void delDatas(){
        myAdapter.delData();
    }
    //删除最后位置问题
    public void removeData(){
        myAdapter.removeData(myAdapter.getItemCount());
    }
}



