package com.example.hsknows;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hsknows.MenuFragment.Menu_problem;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.LCObject;
import cn.leancloud.LCQuery;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static java.lang.Math.max;

public class HomeFragment extends Fragment {

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
    public int listObjHasLoaded = 0;//已經加載內容條數
    Button searchingButton;
    TextView keyword;
    RecyclerView recyclerView;
    List<CardImageInfor_searching_card> list;
    List<String> list_of_objid;//存儲object id 的list
    MyRecyclerAdapter_searching myAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        searchingButton = (Button) getActivity().findViewById(R.id.searching_button);
        keyword = (TextView) getActivity().findViewById(R.id.searching_bar);
        recyclerView = (RecyclerView)getActivity().findViewById(R.id.searching_recycleview);
        list = new ArrayList<>();
        list_of_objid= new ArrayList<>();//存儲object id 的list


        searchingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                research(keyword.getText().toString());
            }
        });
    }


    public void research(String keywords) {

        LCQuery<LCObject> query = new LCQuery<>("Question");//参数为查询的对象类,暂时默认为Question
        query.whereContains("content", keywords);//
        // query.whereEqualTo("")
        query.findInBackground().subscribe(new Observer<List<LCObject>>() {
            public void onSubscribe(Disposable disposable) {
            }

            public void onNext(List<LCObject> Comment) {
                //
                int Csize = Comment.size();
                int i;

                String author;  //作者，内容，回答时间，搜索返回结果用
                String content;
                String createTime;

                String size = String.valueOf(Csize);
                LCObject comment;

                Log.d("success", "结果条数：" + size);

                for (i = 0; i < Csize; i++) {
                    comment = Comment.get(i);
                    author = comment.getString("uploader_nickname");
                    content = comment.getString("content");
                    createTime = comment.getString("createAt");
                    Log.d("success", "author:" + author + "; content:" + content + "; createTime:" + createTime);


                }
            }

            public void onError(Throwable throwable) {
            }

            public void onComplete() {
            }
        });

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
/*
                Log.d("Menu_main_Activity","question size:  "+questions.size());
                Log.d("Menu_main_Activity","i:  "+i);
                Log.d("Menu_main_Activity","end:  "+end);
                Log.d("Menu_main_Activity","this time loaded:  "+thisTimeLoaded);

 */
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
                    //TODO 这里默认悬赏为 0
                    addData(title, uploader_nickname, summarization,time, 0);

                }
                if(thisTimeLoaded <= 0){
                    Toast.makeText(getActivity(),"没有更多内容了",Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(getActivity(),"加载了"+thisTimeLoaded+"条内容",Toast.LENGTH_SHORT).show();
                }
                Log.d("sadsadssa","fefewf");
            }
            public void onError(Throwable throwable) {}
            public void onComplete() {}
        });



        //设置列表显示方式
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置列表默认动画效果
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //绑定适配器
        myAdapter = new MyRecyclerAdapter_searching(list);
        recyclerView.setAdapter(myAdapter);

        //列表点击事件
        myAdapter.setOnItemClickLitener(new MyRecyclerAdapter_searching.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent =new Intent(getActivity(), Menu_problem.class);
                String objid=list_of_objid.get(position);
                intent.putExtra("objid", objid);
                startActivity(intent);//启动Activity

            }
        });

    }
    //添加问题
    public void addData(String title, String author, String summarization,String time, int reward){
        myAdapter.addData(title, author, summarization, time, reward);
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