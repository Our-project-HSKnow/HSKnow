package com.example.hsknows.accountFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hsknows.CardImageInfor;
import com.example.hsknows.MyRecyclerAdapter;
import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Account_history_bookmarkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Account_history_bookmarkFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    int kind = 1; // 1为历史记录 2为书签

    public Account_history_bookmarkFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Account_history_bookmarkFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Account_history_bookmarkFragment newInstance(String param1, String param2) {
        Account_history_bookmarkFragment fragment = new Account_history_bookmarkFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View view =  inflater.inflate(R.layout.account_history_bookmark, container, false);

        recyclerView = view.findViewById(R.id.account_history_bookmark_recyclerview);
        initDatas(kind);
        initView();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        com.getbase.floatingactionbutton.FloatingActionButton history_floatingbutton = getActivity().findViewById(R.id.history_floatingbutton);
        com.getbase.floatingactionbutton.FloatingActionButton bookmark_floatingbuttonn = getActivity().findViewById(R.id.bookmark_floatingbutton);
        history_floatingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kind == 2)
                {
                    kind = 1;
                    initView();
                    initDatas(kind);
                }
            }
        });
        bookmark_floatingbuttonn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kind == 1)
                {
                    kind = 2;
                    initView();
                    recyclerView.setItemAnimator(new SlideInUpAnimator());
                    recyclerView.getItemAnimator().setAddDuration(300);
                    recyclerView.getItemAnimator().setAddDuration(300);
                    initDatas(kind);
                }
            }
        });
    }

    private void initView() {
        recyclerView = (RecyclerView)getActivity(). findViewById(R.id.account_history_bookmark_recyclerview);
    }

    private void initDatas(int kind) {
        //添加数据
        List<CardImageInfor> list = new ArrayList<>();
        list.add(new CardImageInfor("Title 1", "BLGS", new ArrayList<>(), kind));
        list.add(new CardImageInfor("Title 2", "BLGS", new ArrayList<>(), kind));
        list.add(new CardImageInfor("Title 3", "BLGS", new ArrayList<>(), kind));

        //设置列表显示方式
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置列表默认动画效果
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //绑定适配器
        MyRecyclerAdapter myAdapter = new MyRecyclerAdapter(list);
        recyclerView.setAdapter(myAdapter);
        //列表点击事件
        myAdapter.setOnItemClickLitener(new MyRecyclerAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "click"+ position +"item", Toast.LENGTH_SHORT).show();
            }
        });

    }

}