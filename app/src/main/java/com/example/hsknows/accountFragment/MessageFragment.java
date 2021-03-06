package com.example.hsknows.accountFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hsknows.CardImageInfor_message;
import com.example.hsknows.MyRecyclerAdapter_message;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;

    public MessageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageFragment newInstance(String param1, String param2) {
        MessageFragment fragment = new MessageFragment();
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
        View view = inflater.inflate(R.layout.account_message, container, false);
        recyclerView = view.findViewById(R.id.account_message_recyclerview);
        initDatas(3);
        return view;
    }

    private void initDatas(int kind) {
        //????????????
        List<CardImageInfor_message> list = new ArrayList<>();
        list.add(new CardImageInfor_message("Title 1", "BLGS", "???????????? ???????????? ???????????? ???????????? ????????????", kind));
        list.add(new CardImageInfor_message("Title 2", "BLGS", "???????????? ???????????? ???????????? ???????????? ????????????", kind));
        list.add(new CardImageInfor_message("Title 3", "BLGS", "???????????? ???????????? ???????????? ???????????? ????????????", kind));

        //????????????????????????
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //??????????????????????????????
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //???????????????
        MyRecyclerAdapter_message myAdapter = new MyRecyclerAdapter_message(list);
        recyclerView.setAdapter(myAdapter);
        //??????????????????
        myAdapter.setOnItemClickLitener(new MyRecyclerAdapter_message.OnItemClickLitener(){
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "click"+ position +"item", Toast.LENGTH_SHORT).show();
            }
        });

    }
}