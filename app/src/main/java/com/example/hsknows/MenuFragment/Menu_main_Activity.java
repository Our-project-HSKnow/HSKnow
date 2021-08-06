package com.example.hsknows.MenuFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
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
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.LCObject;
import cn.leancloud.LCQuery;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static java.lang.Math.max;

public class Menu_main_Activity extends AppCompatActivity {

    ImageButton imgbtn1;//这是那个标着加号的button，点这个发表问题

    RecyclerView recyclerView;
    List<CardImageInfor_problem_card> list = new ArrayList<>();
    List<String> list_of_objid= new ArrayList<>();//存儲object id 的list
    MyRecyclerAdapter_problem_card myAdapter;
    ImageButton refreshBtn;
    String SubjName;

    public int listObjHasLoaded = 0;//已經加載內容條數

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = LayoutInflater.from(this);

        setContentView(R.layout.menu_main_activity);
        MotionLayout main_layout = (MotionLayout)findViewById(R.id.menu_main_motionlayout);

        /*接收上一个活动传递进来的学科名字，并打印在标题的textview的位置*/
        Intent intent1=getIntent();
        SubjName=intent1.getStringExtra("SubjName");

        TextView SubjTitle;
        SubjTitle = (TextView)findViewById(R.id.menu_title);
        SubjTitle.setText(SubjName);

        ImageButton back_button = (ImageButton)findViewById(R.id.menu_main_back_button);
        refreshBtn=(ImageButton)findViewById(R.id.menu_main_refresh_button);
        recyclerView = (RecyclerView)findViewById(R.id.menu_main_recycleview);
        initDatas(SubjName);//加載數據

        RefreshLayout refreshLayout = findViewById(R.id.refreshLayout);
        /*下滑刷新*/
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                listObjHasLoaded = 0;//刷新時，它自動歸零
                list_of_objid.clear();
                delDatas();
                initDatas(SubjName);
            }
        });
        /*上滑加載更多數據*/
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                initDatas(SubjName);
            }
        });

        //設置按鈕旋轉
        final RotateAnimation animation = new RotateAnimation(0.0f, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration( 900 );//旋轉時間
        refreshBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                refreshBtn.startAnimation( animation );//開始旋轉
                listObjHasLoaded = 0;//刷新時，它自動歸零
                list_of_objid.clear();
                delDatas();
                initDatas(SubjName);
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        }
    }

    private void initDatas(String nameOfSubj) {
        //添加测试数据

        LCQuery<LCObject> query = new LCQuery<>("Question");
        query.whereEqualTo("subject", nameOfSubj);
        query.findInBackground().subscribe(new Observer<List<LCObject>>() {
            public void onSubscribe(Disposable disposable) {}
            public void onNext(List<LCObject> questions) {
                int i=questions.size() -1 -listObjHasLoaded;
                int end=max(i-10,-1);
                int thisTimeLoaded=i-end;//這次加載了多少條信息
                listObjHasLoaded+=thisTimeLoaded;

                Log.d("Menu_main_Activity","question size:  "+questions.size());
                Log.d("Menu_main_Activity","i:  "+i);
                Log.d("Menu_main_Activity","end:  "+end);
                Log.d("Menu_main_Activity","this time loaded:  "+thisTimeLoaded);
                for(; i >end; i--)
                {

                    list_of_objid.add((String) questions.get(i).getObjectId());
                    String title= (String) questions.get(i).get("title");
                    String content= (String) questions.get(i).get("content");
                    String time= (String) questions.get(i).get("time");
                    String summarization;
                    if(content.length() < 20){
                        summarization="摘要："+content;
                    }
                    else{
                        summarization="摘要："+content.substring(0,20)+"...";
                    }
                    String uploader_nickname= "作者："+(String) questions.get(i).get("uploader_nickname");
                    addData(title, uploader_nickname, summarization,time);

                }
                if(thisTimeLoaded <= 0){
                    Toast.makeText(Menu_main_Activity.this,"没有更多内容了",Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(Menu_main_Activity.this,"加载了"+thisTimeLoaded+"条内容",Toast.LENGTH_SHORT).show();
                }
                Log.d("sadsadssa","fefewf");
            }
            public void onError(Throwable throwable) {}
            public void onComplete() {}
        });



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
                String objid=list_of_objid.get(position);
                intent.putExtra("objid", objid);
                startActivity(intent);//启动Activity

            }
        });

    }
    //添加问题
    public void addData(String title, String author, String summarization,String time){
        myAdapter.addData(title, author, summarization,time);
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



