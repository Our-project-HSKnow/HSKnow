package com.example.hsknows.accountFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

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
                //TODO : floatingbutton 的点击效果——直接在代码加入卡片及更新内容
            }
        });

    }
}