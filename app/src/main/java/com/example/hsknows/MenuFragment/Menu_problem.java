package com.example.hsknows.MenuFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hsknows.CardImageInfor_problem_comment;
import com.example.hsknows.Leancloud.UselessThings;
import com.example.hsknows.MyRecyclerAdapter_problem_comment;
import com.example.myapplication.R;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.leancloud.LCObject;
import cn.leancloud.LCQuery;
import cn.leancloud.LCUser;
import cn.leancloud.LeanCloud;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static java.lang.Math.max;

public class Menu_problem extends AppCompatActivity {

    ImageView imageView; //问题图片
    TextView place_title; //问题标题
    TextView place_author; //问题提出者
    TextView place_content; //问题内容
    TextView place_time;
    TextView place_subj;
    Button upload_comment;

    public String title;
    public String content;
    public String uploader_nickname;
    public String time_of_upload;
    public String subject;
    public String objid;

    public List<String> comment_level;


    EditText place_comment;//評論
    TextView comment_watcher;
    RecyclerView recyclerView;
    List<CardImageInfor_problem_comment> list = new ArrayList<>(); //评论列表
    MyRecyclerAdapter_problem_comment myAdapter;
    Button finish_button;

    String login_token;//這是個用作登陸的token

    public int listObjHasLoaded = 0;//已經加載內容條數
    public int total_comments;
    public String check_if_reply="回复\\d+楼.*";//用於檢索對話框的內容是否回復某人

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_problem);

        /*问题设置*/
        place_title = (TextView)findViewById(R.id.menu_problem_title);
        place_author = (TextView)findViewById(R.id.menu_problem_author);
        place_content = (TextView)findViewById(R.id.menu_problem_content);
        place_time = (TextView)findViewById(R.id.menu_problem_time);
        place_subj = (TextView)findViewById(R.id.menu_problem_subject);
        finish_button=(Button)findViewById(R.id.finish_btn);
        upload_comment=(Button)findViewById(R.id.upload_a_comment);
        place_comment=(EditText)findViewById(R.id.comment_space);
        comment_watcher=(TextView)findViewById(R.id.comment_watcher);



        finish_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        place_comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) { }
            @Override
            public void beforeTextChanged(CharSequence text, int start, int count,int after) { }
            @Override
            public void afterTextChanged(Editable edit) {
                //edit  输入结束呈现在输入框中的信息
                int count=edit.toString().length();
                comment_watcher.setText(count+"/300");
            }
        });

        // 获得问题具体内容
        objid = getObjid();
        getProblem(objid);

        /*评论设置*/
        recyclerView = (RecyclerView)findViewById(R.id.menu_problem_comment_content);

        initDatas();
        initComments();

        RefreshLayout refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);


        /*下滑刷新*/
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                listObjHasLoaded = 0;//刷新時，它自動歸零
                myAdapter.notifyDataSetChanged();
                delDatas();
                myAdapter.notifyDataSetChanged();
                initComments();
            }
        });
        /*上滑加載更多數據*/
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                initComments();
            }
        });
        //登陸leancloud
        LeanCloud.initialize(this,
                "a47aIWgkSdQF6xSk2j5UPUJl-gzGzoHsz",
                "rQW7dM4UUMJauT3S7WAzIEl8",
                "https://a47aiwgk.lc-cn-n1-shared.com");

        //點擊上傳按鈕後
        upload_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment=place_comment.getText().toString().trim();
                if(TextUtils.isEmpty(comment)){
                    //如果標題與內容至少有一個為空，則會認為不能上傳
                    Toast.makeText(Menu_problem.this,"評論不能為空！",Toast.LENGTH_SHORT).show();
                }else{
                    LCUser currentUser = LCUser.getCurrentUser();
                    if (currentUser == null) {
                        Toast.makeText(Menu_problem.this,"請先登錄或註冊賬號！",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        //獲取用戶token用於登錄
                        login_token= LCUser.getCurrentUser().getSessionToken();

                        LCUser.becomeWithSessionTokenInBackground(login_token).subscribe(new Observer<LCUser>() {
                            public void onSubscribe(Disposable disposable) {}
                            public void onNext(LCUser user) {
                                // 修改 currentUser
                                LCUser.changeCurrentUser(user, true);
                                String uploader_account=user.getUsername();
                                String uploader_nickname= (String) user.get("user_nickname");

                                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                Calendar calendar = Calendar.getInstance();
                                Date date = calendar.getTime();
                                String current_time = sdf.format(date);

                                // 构建对象
                                LCObject todo = new LCObject("Comment");

                                // 为属性赋值
                                todo.put("content", comment);
                                todo.put("uploader_account",uploader_account);
                                todo.put("uploader_nickname",uploader_nickname);
                                todo.put("time",current_time);
                                todo.put("question_id",objid);//所屬問題的id
                                todo.put("its_level",total_comments+1);

                                //判斷它的its_father，並保存
                                if(comment.matches(check_if_reply)){
                                    int father=UselessThings.FindItsFather(comment);
                                    todo.put("its_father",father);
                                }else{
                                    todo.put("its_father",0);
                                }

                                // 将对象保存到云端
                                todo.saveInBackground().subscribe(new Observer<LCObject>() {
                                    public void onSubscribe(Disposable disposable) {}
                                    public void onNext(LCObject todo) {
                                        // 成功保存之后，执行其他逻辑
                                        Toast.makeText(Menu_problem.this,"上傳評論成功！",Toast.LENGTH_SHORT).show();
                                        //清空輸入框，並放下鍵盤
                                        place_comment.getText().clear();
                                        InputMethodManager imm = (InputMethodManager) Menu_problem.this
                                                .getSystemService(Context.INPUT_METHOD_SERVICE);
                                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                                    }
                                    public void onError(Throwable throwable) {
                                        // 异常处理
                                    }
                                    public void onComplete() {}
                                });
                            }
                            public void onError(Throwable throwable) {
                                // session token 无效
                                Toast.makeText(Menu_problem.this,"請先登錄或註冊賬號！",Toast.LENGTH_SHORT).show();
                            }
                            public void onComplete() {}
                        });
                    }
                }
            }
        });
    }
    // 获得intent传入的问题位置
    private String getObjid(){
        Intent intent = getIntent();
        return intent.getStringExtra("objid");
    }

    //通过传入的点击事件发生位置找到问题具体内容
    private void getProblem(String objid){
        LCQuery<LCObject> query = new LCQuery<>("Question");
        query.getInBackground(objid).subscribe(new Observer<LCObject>() {
            public void onSubscribe(Disposable disposable) {}
            public void onNext(LCObject problem) {
                title=problem.getString("title");
                content=problem.getString("content");
                uploader_nickname=problem.getString("uploader_nickname");
                time_of_upload=problem.getString("time");
                subject=problem.getString("subject");
                place_title.setText(title);
                place_author.setText("作者："+uploader_nickname);
                place_content.setText(content);
                place_time.setText(time_of_upload);
                place_subj.setText(subject);
            }
            public void onError(Throwable throwable) {}
            public void onComplete() {}
        });

    }

    // 初始化评论列表
    private void initDatas() {

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


                place_comment.setFocusable(true);
                place_comment.setFocusableInTouchMode(true);
                place_comment.requestFocus();
                InputMethodManager imm = (InputMethodManager) place_comment.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                String current_str=place_comment.getText().toString().trim();

                if(current_str.matches(check_if_reply)){
                    int position_of_cut=current_str.indexOf("：");//必須是中文冒號
                    current_str=current_str.substring(position_of_cut+1);
                }
                current_str="回复"+(total_comments-position)+"楼： "+current_str;
                place_comment.setText(current_str);
                place_comment.setSelection(current_str.length());


            }
        });

    }

    public void initComments(){

        LCQuery<LCObject> query = new LCQuery<>("Comment");
        query.whereEqualTo("question_id",objid );
        query.findInBackground().subscribe(new Observer<List<LCObject>>() {
            public void onSubscribe(Disposable disposable) {}
            public void onNext(List<LCObject> comments) {

                total_comments = comments.size();

                int i=comments.size() -1 -listObjHasLoaded;
                int end=max(i-10,-1);
                int thisTimeLoaded=i-end;//這次加載了多少條信息
                listObjHasLoaded+=thisTimeLoaded;

                /*
                Log.d("Menu_problem","comments size:  "+comments.size());
                Log.d("Menu_problem","i:  "+i);
                Log.d("Menu_problem","end:  "+end);
                Log.d("Menu_problem","this time loaded:  "+thisTimeLoaded);
                 */


                for(; i >end; i--)
                {
                    String content= (String) comments.get(i).get("content");
                    String time= (String) comments.get(i).get("time");
                    String uploader_nickname= "作者："+(String) comments.get(i).get("uploader_nickname");
                    addData(time,uploader_nickname,content,i+1);
                }
                if(thisTimeLoaded <= 0){
                    Toast.makeText(Menu_problem.this,"没有更多内容了",Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(Menu_problem.this,"加载了"+thisTimeLoaded+"条内容",Toast.LENGTH_SHORT).show();
                }
                Log.d("sadsadssa","fefewf");
            }
            public void onError(Throwable throwable) {}
            public void onComplete() {}
        });



    }
    //添加评论
    public void addData(String time, String author, String content,int level){
        myAdapter.addData(time, author, content,level);
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