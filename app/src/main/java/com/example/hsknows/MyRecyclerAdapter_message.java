package com.example.hsknows;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/25.
 */
public class MyRecyclerAdapter_message extends RecyclerView.Adapter<MyRecyclerAdapter_message.ViewHolder> {

    private List<CardImageInfor_message> list;

    public MyRecyclerAdapter_message(List<CardImageInfor_message> list) {
        this.list = list;
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
        return list.size();
    }

    /**
     * 初始化布局视图
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_message_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * 绑定视图组件数据
     */
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.writer.setText(list.get(position).getWriter());
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
        public TextView title;
        public TextView writer;
        public TextView content;
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.account_message_title);
            writer = (TextView) itemView.findViewById(R.id.account_message_writer);
            content = (TextView) itemView.findViewById(R.id.account_message_content);
        }
    }

}
