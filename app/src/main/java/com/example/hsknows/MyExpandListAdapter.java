package com.example.hsknows;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.myapplication.R;


public class MyExpandListAdapter extends BaseExpandableListAdapter {
    private Context mcontext;
    public String[] groupString;
    public String[][] childString;
    public String subject;

    public MyExpandListAdapter(Context mcontext, String[] groupString, String[][] childString, String subject){
        this.mcontext = mcontext;
        this.groupString = groupString;
        this.childString = childString;
        this.subject = subject;
    }

    @Override
    // 获取分组的个数
    public int getGroupCount() {
        return groupString.length;
    }

    //获取指定分组中的子选项的个数
    @Override
    public int getChildrenCount(int groupPosition) {
        return childString[groupPosition].length;
    }

    //        获取指定的分组数据
    @Override
    public Object getGroup(int groupPosition) {
        return groupString[groupPosition];
    }

    //获取指定分组中的指定子选项数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childString[groupPosition][childPosition];
    }

    //获取指定分组的ID, 这个ID必须是唯一的
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //获取子选项的ID, 这个ID必须是唯一的
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //分组和子选项是否持有稳定的ID, 就是说底层数据的改变会不会影响到它们
    @Override
    public boolean hasStableIds() {
        return true;
    }
    /**
     *
     * 获取显示指定组的视图对象
     *
     * @param groupPosition 组位置
     * @param isExpanded 该组是展开状态还是伸缩状态
     * @param convertView 重用已有的视图对象
     * @param parent 返回的视图对象始终依附于的视图组
     */
// 获取显示指定分组的视图
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null){
            int resID = R.layout.menu_parent_layout_math;
            switch (subject){
                case "math":
                    resID = R.layout.menu_parent_layout_math;
                    break;
                case "physics":
                    resID = R.layout.menu_parent_layout_physics;
                    break;
                case "computerscience":
                    resID = R.layout.menu_parent_layout_cs;
                    break;
                case "building":
                    resID = R.layout.menu_parent_layout_building;
                    break;
                case "economic":
                    resID = R.layout.menu_parent_layout_economic;
                    break;
                case "electronictechnique":
                    resID = R.layout.menu_parent_layout_electronictechnique;
                    break;
                case "politics":
                    resID = R.layout.menu_parent_layout_politics;
                    break;
            }
            convertView = LayoutInflater.from(parent.getContext()).inflate(resID,parent,false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.tvTitle = (ConstraintLayout)convertView.findViewById(R.id.label_group_normal);
            convertView.setTag(groupViewHolder);
        }else {
            groupViewHolder = (GroupViewHolder)convertView.getTag();
        }
        ;
        return convertView;
    }
    /**
     *
     * 获取一个视图对象，显示指定组中的指定子元素数据。
     *
     * @param groupPosition 组位置
     * @param childPosition 子元素位置
     * @param isLastChild 子元素是否处于组中的最后一个
     * @param convertView 重用已有的视图(View)对象
     * @param parent 返回的视图(View)对象始终依附于的视图组
     * @return
     * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, android.view.View,
     *      android.view.ViewGroup)
     */

    //取得显示给定分组给定子位置的数据用的视图
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_child_layout,parent,false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.tvTitle = (TextView)convertView.findViewById(R.id.expand_child);
            convertView.setTag(childViewHolder);

        }else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        childViewHolder.tvTitle.setText(childString[groupPosition][childPosition]);
        return convertView;
    }

    //指定位置上的子元素是否可选中
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class GroupViewHolder {
        ConstraintLayout tvTitle;
    }

    static class ChildViewHolder {
        TextView tvTitle;

    }
}
