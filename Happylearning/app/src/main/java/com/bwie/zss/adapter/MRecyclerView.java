package com.bwie.zss.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.zss.R;
import com.bwie.zss.javabean.Index_content_Data;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * 1.类的用途
 * 2.@author 赵姗杉
 * 3.@date 2017/10/5 15:17
 */

public class MRecyclerView extends RecyclerView.Adapter<MRecyclerView.ViewHolder> {

    private Context context;
    private List<Index_content_Data.DataBean.ListBean> list;

    public MRecyclerView(Context context, List<Index_content_Data.DataBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.index_recycleryview,null);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(context).load(list.get(position).getPic()).into(holder.image);
        holder.title.setText(list.get(position).getTitle());
        holder.content.setText(list.get(position).getIntro());
        holder.address.setText(list.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView address;
        private  TextView content;
        private TextView title;
        private ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.index_image);
            title = (TextView) itemView.findViewById(R.id.index_title);
            content = (TextView) itemView.findViewById(R.id.index_content);
            address = (TextView) itemView.findViewById(R.id.index_address);
        }
    }

}
