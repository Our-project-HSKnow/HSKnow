package com.example.hsknows;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.example.hsknows.math.MathFragment1;
import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Menu_mainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Menu_mainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Menu_mainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Menu_mainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Menu_mainFragment newInstance(String param1, String param2) {
        Menu_mainFragment fragment = new Menu_mainFragment();
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

        View view = inflater.inflate(R.layout.menu_main_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TabLayout tabLayout;
        ViewPager2 viewPager2;
        List<String> titles = new ArrayList<>();
        List<Fragment> fragments = new ArrayList<>();


        switch (MenuFragment.getDrawerLayoutSelectedItemId()){
            case 1:
                titles.add(String.valueOf(R.string.senior_math));
                titles.add(String.valueOf(R.string.linear_algebra));
                titles.add(String.valueOf(R.string.Discrete_math));
                titles.add(String.valueOf(R.string.Mathematical_logic));
                titles.add(String.valueOf(R.string.Mathematical_analysis));
                break;
            case 2:
                titles.add(String.valueOf(R.string.college_physics));
                titles.add(String.valueOf(R.string.Astrophysics));
                break;
            case 3:
                titles.add(String.valueOf(R.string.Introduction_to_Computer_Science));
                titles.add(String.valueOf(R.string.data_structure));
                titles.add(String.valueOf(R.string.C_Programming));
                titles.add(String.valueOf(R.string.DBS));
                titles.add(String.valueOf(R.string.algorithm_design));
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
        }
        for (int i=0; i<titles.size(); i++){
            fragments.add(MathFragment1.newInstance(mParam1, mParam2));
        }

        tabLayout = getActivity().findViewById(R.id.math_tablelayout);
        viewPager2 = getActivity().findViewById(R.id.viewpager);

    }
}