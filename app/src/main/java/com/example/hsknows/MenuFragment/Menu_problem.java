package com.example.hsknows.MenuFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hsknows.CardImageInfor_problem_comment;
import com.example.hsknows.MyRecyclerAdapter_problem_comment;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class Menu_problem extends AppCompatActivity {

    ImageView imageView; //问题图片
    TextView title; //问题标题
    TextView author; //问题提出者
    TextView content; //问题内容

    RecyclerView recyclerView;
    List<CardImageInfor_problem_comment> list = new ArrayList<>(); //评论列表
    MyRecyclerAdapter_problem_comment myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_problem);

        /*
        问题设置
         */
        title = (TextView)findViewById(R.id.menu_problem_title);
        author = (TextView)findViewById(R.id.menu_problem_author);
        content = (TextView)findViewById(R.id.menu_problem_content);
        // 获得问题具体内容
        int position = getposition();
        getProblem(position);

        /*
        评论设置
         */

        recyclerView = (RecyclerView)findViewById(R.id.menu_problem_comment_content);
        initDatas();
    }
    // 获得intent传入的问题位置
    private int getposition(){
        Intent intent = getIntent();
        return intent.getIntExtra("problem", -1);
    }

    //TODO
    //通过传入的点击事件发生位置找到问题具体内容
    private void getProblem(int position){
        title.setText("Title");
        author.setText("BLGS");
        content.setText("这是一道很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长的题目");
    }

    // 初始化评论列表
    private void initDatas() {
        //添加测试数据
        for(int i = 0; i < 20; i++)
        {
            list.add(new CardImageInfor_problem_comment("2021年7月15日11点17分", "BLGS", "6666666666666666666666"));
        }

        //设置列表显示方式
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置列表默认动画效果
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //绑定适配器
        myAdapter = new MyRecyclerAdapter_problem_comment(list);
        recyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

        //列表点击事件
        myAdapter.setOnItemClickLitener(new MyRecyclerAdapter_problem_comment.OnItemClickLitener(){
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(Menu_problem.this, "click"+ position +"item", Toast.LENGTH_SHORT).show();
            }
        });

    }
    //添加评论
    public void addData(String title, String author, String content){
        myAdapter.addData(title, author, content);
    }
    //删除所有评论
    public void delDatas(){
        myAdapter.delData();
    }
    //删除最后位置评论
    public void removeData(){
        myAdapter.removeData(myAdapter.getItemCount());
    }
}