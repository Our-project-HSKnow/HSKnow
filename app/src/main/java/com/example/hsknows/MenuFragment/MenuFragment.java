package com.example.hsknows.MenuFragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.hsknows.MyExpandListAdapter;
import com.example.hsknows.MyExpandableListView;
import com.example.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {

    public static int DrawerLayoutSelectedItemId = 1;

    public static int getDrawerLayoutSelectedItemId(){
        return DrawerLayoutSelectedItemId;
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView ivSearch, ivClose;
    private EditText edSearch;
    private RelativeLayout laySearch;
    private AutoTransition autoTransition;
    private int width;

    public MenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
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
        View view = inflater.inflate(R.layout.menu_fragment, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //科目卡片布局
        String[] subject = {"math", "physics", "computerscience", "building","economic", "electronictechnique", "politics"};
        for (int i = 0; i < subject.length; i++){
            initCard(subject[i]);
        }

        //顶部搜索框
        initView();
    }
    private void initCard(String kind_of_subject) {
        String[] groupString;
        String[][] childString;
        View parent = View.inflate(getActivity(), R.layout.menu_parent_layout_math, null);
        switch (kind_of_subject){
            case "math":
                groupString = new String[]{" "};
                childString = new String[][]{{"高等数学", "线性代数", "离散数学", "数理逻辑", "数学分析"}};
                MyExpandableListView expandableListView_math = (MyExpandableListView)getActivity().findViewById(R.id.math_list);
                expandableListView_math.setAdapter(new MyExpandListAdapter(getContext(), groupString, childString, kind_of_subject));
                expandableListView_math.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        Intent intent = new Intent(getActivity(), Menu_main_Activity.class);
                        intent.putExtra("SubjName",childString[groupPosition][childPosition]);//我也不知道为啥这么用就好使
                        //反正childString[groupPosition][childPosition]就好使
                        startActivity(intent);
                        return true;
                    }
                });
                break;
            case "physics":
                groupString = new String[]{" "};
                childString = new String[][]{{"大学物理", "天体物理学"}};
                MyExpandableListView expandableListView_physics = (MyExpandableListView)getActivity().findViewById(R.id.physics_list);
                expandableListView_physics.setAdapter(new MyExpandListAdapter(getContext(), groupString, childString, kind_of_subject));
                expandableListView_physics.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        Intent intent = new Intent(getActivity(), Menu_main_Activity.class);
                        intent.putExtra("SubjName",childString[groupPosition][childPosition]);
                        startActivity(intent);
                        return true;
                    }
                });
                break;
            case "computerscience":

                groupString = new String[]{" "};
                childString = new String[][]{{"计算机导论","数据结构","C语言程序设计","数据库系统","算法设计"}};
                MyExpandableListView expandableListView_cs = (MyExpandableListView)getActivity().findViewById(R.id.CS_list);
                expandableListView_cs.setAdapter(new MyExpandListAdapter(getContext(), groupString, childString, kind_of_subject));
                expandableListView_cs.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        Intent intent = new Intent(getActivity(), Menu_main_Activity.class);
                        intent.putExtra("SubjName",childString[groupPosition][childPosition]);
                        startActivity(intent);
                        return true;
                    }
                });
                break;
            case "building":
                groupString = new String[]{" "};
                childString = new String[][]{{"建设中"}};
                MyExpandableListView expandableListView_building = (MyExpandableListView)getActivity().findViewById(R.id.building_list);
                expandableListView_building.setAdapter(new MyExpandListAdapter(getContext(), groupString, childString, kind_of_subject));
                expandableListView_building.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        Intent intent = new Intent(getActivity(), Menu_main_Activity.class);
                        intent.putExtra("SubjName",childString[groupPosition][childPosition]);
                        startActivity(intent);
                        return true;
                    }
                });
                break;
            case "economic":
                groupString = new String[]{" "};
                childString = new String[][]{{"建设中"}};
                MyExpandableListView expandableListView_economic = (MyExpandableListView)getActivity().findViewById(R.id.economics_list);
                expandableListView_economic.setAdapter(new MyExpandListAdapter(getContext(), groupString, childString, kind_of_subject));
                expandableListView_economic.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        Intent intent = new Intent(getActivity(), Menu_main_Activity.class);
                        intent.putExtra("SubjName",childString[groupPosition][childPosition]);
                        startActivity(intent);
                        return true;
                    }
                });
                break;
            case "electronictechnique":
                groupString = new String[]{" "};
                childString = new String[][]{{"建设中"}};
                MyExpandableListView expandableListView_et = (MyExpandableListView)getActivity().findViewById(R.id.electronictechnique_list);
                expandableListView_et.setAdapter(new MyExpandListAdapter(getContext(), groupString, childString, kind_of_subject));
                expandableListView_et.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        Intent intent = new Intent(getActivity(), Menu_main_Activity.class);
                        intent.putExtra("SubjName",childString[groupPosition][childPosition]);
                        startActivity(intent);
                        return true;
                    }
                });
                break;
            case "politics":
                groupString = new String[]{" "};
                childString = new String[][]{{"建设中"}};
                MyExpandableListView expandableListView_politics = (MyExpandableListView)getActivity().findViewById(R.id.politics_list);
                expandableListView_politics.setAdapter(new MyExpandListAdapter(getContext(), groupString, childString, kind_of_subject));
                expandableListView_politics.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        Intent intent = new Intent(getActivity(), Menu_main_Activity.class);
                        intent.putExtra("SubjName",childString[groupPosition][childPosition]);
                        startActivity(intent);
                        return true;
                    }
                });
                break;
            default:
                Log.e("MainActivity", "subject didn't be totally initialize in the function initcard");
        }
    }


    private void initView() {
        laySearch = (RelativeLayout) getActivity().findViewById(R.id.menu_header);
        ivSearch = (ImageView) getActivity().findViewById(R.id.iv_search);
        ivClose = (ImageView) getActivity().findViewById(R.id.iv_close);
        edSearch = (EditText) getActivity().findViewById(R.id.ed_search);

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

