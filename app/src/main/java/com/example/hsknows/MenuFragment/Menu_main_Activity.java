package com.example.hsknows.MenuFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

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
    List<String> list_of_objid= new ArrayList<>();//存儲object id 的list
    MyRecyclerAdapter_problem_card myAdapter;
    ImageButton refreshBtn;
    String SubjName;


/*
    private static final int REFRESH_COMPLETE = 0X110;
    private SwipeRefreshLayout mSwipeLayout;
    private ListView mListView;
    private ArrayAdapter<String> mAdapter;
    private List<String> mDatas = new ArrayList<String>(Arrays.asList("Java",
            "Javascript", "C++", "Ruby", "Json", "HTML"));
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
                    mDatas.addAll(Arrays.asList("Lucene", "Canvas", "Bitmap"));
                    mAdapter.notifyDataSetChanged();
                    mSwipeLayout.setRefreshing(false);
                    Toast.makeText(Menu_main_Activity.this, "刷新完毕", Toast.LENGTH_SHORT).show();
                    break;
            }
        };
    };
 */





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
/*
        //設置上滑刷新
        mListView = (ListView) findViewById(R.id.id_listview);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.id_swipe_ly);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 300);

                Toast.makeText(Menu_main_Activity.this, "正在刷新", Toast.LENGTH_SHORT).show();
            }
        });
        mSwipeLayout.setColorScheme(android.R.color.holo_green_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mDatas);
        mListView.setAdapter(mAdapter);
 */
        refreshBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
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
                for(int i = questions.size()-1; i >=0; i--)
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



