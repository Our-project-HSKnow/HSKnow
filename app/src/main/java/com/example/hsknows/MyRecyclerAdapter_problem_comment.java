package com.example.hsknows;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

/**
 * Created by Administrator on 2017/10/25.
 */
public class MyRecyclerAdapter_problem_comment extends RecyclerView.Adapter<MyRecyclerAdapter_problem_comment.ViewHolder> {

    private List<CardImageInfor_problem_comment> list;
    int number = 0; //评论数量

    public MyRecyclerAdapter_problem_comment(List<CardImageInfor_problem_comment> list) {
        this.list = list;
        number += list.size();
    }

    //新建点击事件接口
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public int getItemCount() {
        Log.d("Menu_main_Activity","Item.count : "+list.size());
        return list.size();
    }

    // 增加一个item
    public void addData(String title, String author, String content) {
        list.add(new CardImageInfor_problem_comment(title, author, content));
        notifyItemInserted(number);
    }

    //删除所有item
    public void delData(){
        list.clear();
        notifyAll();
    }

    // 删除对应item
    public void removeData(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 初始化布局视图
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_problem_comment_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * 绑定视图组件数据
     */
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.time.setText(list.get(position).getTime());
        holder.author.setText(list.get(position).getAuthor());
        holder.content.setText(list.get(position).getContent());
        if (mOnItemClickLitener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=holder.getAdapterPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView,pos);
                }
            });
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView time;
        public TextView author;
        public TextView content;
        public ViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.menu_problem_comment_card_time);
            author = (TextView) itemView.findViewById(R.id.menu_problem_comment_card_author);
            content = (TextView) itemView.findViewById(R.id.menu_problem_comment_card_content);
        }
    }

}
