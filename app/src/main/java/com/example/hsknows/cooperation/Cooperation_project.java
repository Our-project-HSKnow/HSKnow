package com.example.hsknows.cooperation;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hsknows.CardImageInfor_cooperation_project;
import com.example.hsknows.MyRecyclerAdapter_cooperation_project;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Cooperation_project#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Cooperation_project extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //列表内容
    RecyclerView recyclerView;
    List<CardImageInfor_cooperation_project> list = new ArrayList<>();
    MyRecyclerAdapter_cooperation_project myAdapter;

    //搜索框
    private ImageView ivSearch, ivClose;
    private EditText edSearch;
    private RelativeLayout laySearch;
    private AutoTransition autoTransition;
    private int width;

    public Cooperation_project() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Cooperation_project.
     */
    // TODO: Rename and change types and number of parameters
    public static Cooperation_project newInstance(String param1, String param2) {
        Cooperation_project fragment = new Cooperation_project();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //顶部搜索框
        initView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.cooperation_project, container, false);

        recyclerView = view.findViewById(R.id.cooperation_project_recycleview);
        initDatas();
        return view;
    }
    /*
    列表内容=======================================================================================
     */
    private void initDatas() {
        //添加测试数据
        for(int i = 0; i < 20; i++)
        {
            list.add(new CardImageInfor_cooperation_project("Project ", "BLGS"));
        }

        //设置列表显示方式
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置列表默认动画效果
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //绑定适配器
        myAdapter = new MyRecyclerAdapter_cooperation_project(list);
        recyclerView.setAdapter(myAdapter);

        //列表点击事件
        myAdapter.setOnItemClickLitener(new MyRecyclerAdapter_cooperation_project.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "click"+ position +"item", Toast.LENGTH_SHORT).show();
            }
        });

    }
    //添加问题
    public void addData(String title, String author){
        myAdapter.addData(title, author);
    }
    //删除所有问题
    public void delDatas(){
        myAdapter.delData();
    }
    //删除最后位置问题
    public void removeData(){
        myAdapter.removeData(myAdapter.getItemCount());
    }


    /*
    搜索框=========================================================================================
     */

    private void initView() {
        laySearch = (RelativeLayout) getActivity().findViewById(R.id.cooperation_project_header);
        ivSearch = (ImageView) getActivity().findViewById(R.id.cooperation_project_searchimage);
        ivClose = (ImageView) getActivity().findViewById(R.id.cooperation_project_closeimage);
        edSearch = (EditText) getActivity().findViewById(R.id.cooperation_project_searchtext);

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initExpand();
            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initClose();
            }
        });


        WindowManager manager = getActivity().getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;  //获取屏幕的宽度 像素

        /**
         * 输入法键盘的搜索监听
         */
        edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String city = edSearch.getText().toString();
                    if (!TextUtils.isEmpty(city)) {
                        Toast.makeText(getActivity(),city,Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(),"请输入内容",Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
    }

    /*设置伸展状态时的布局*/
    @SuppressLint("ClickableViewAccessibility")
    public void initExpand() {

        edSearch.setVisibility(View.VISIBLE);
        ivClose.setVisibility(View.VISIBLE);
        ConstraintLayout.LayoutParams LayoutParams = (ConstraintLayout.LayoutParams) laySearch.getLayoutParams();
        LayoutParams.width = dip2px(px2dip(width)-40);
        LayoutParams.setMargins(dip2px(0), dip2px(0), dip2px(0), dip2px(0));
        laySearch.setPadding(14,0,14,0);
        laySearch.setLayoutParams(LayoutParams);

        edSearch.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                edSearch.setFocusable(true);
                edSearch.setFocusableInTouchMode(true);
                return false;
            }
        });
        //开始动画
        beginDelayedTransition(laySearch);

    }

    /*设置收缩状态时的布局*/
    private void initClose() {
        edSearch.setVisibility(View.GONE);
        edSearch.setText("");
        ivClose.setVisibility(View.GONE);

        ConstraintLayout.LayoutParams LayoutParams = (ConstraintLayout.LayoutParams) laySearch.getLayoutParams();
        LayoutParams.width = dip2px(48);
        LayoutParams.height = dip2px(48);
        LayoutParams.setMargins(dip2px(0), dip2px(0), dip2px(0), dip2px(0));
        laySearch.setLayoutParams(LayoutParams);

        //隐藏键盘
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
        edSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edSearch.setCursorVisible(true);
            }
        });
        //开始动画
        beginDelayedTransition(laySearch);
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void beginDelayedTransition(ViewGroup view) {
        autoTransition = new AutoTransition();
        autoTransition.setDuration(500);
        TransitionManager.beginDelayedTransition(view,autoTransition);
    }

    // dp 转成 px
    private int dip2px(float dpVale) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpVale * scale + 0.5f);
    }

    // px 转成 dp
    private int px2dip(float pxValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}