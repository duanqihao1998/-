package com.bwie.shang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.shang.R;
import com.bwie.shang.bean.LaolishiBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Love_you on 2017/9/11 0011.
 */
public class SuosuoRecycleViewAdapter extends RecyclerView.Adapter<SuosuoRecycleViewAdapter.MyViewHolder>{
    private Context context;
    List<LaolishiBean.Laolishi.GT> goods_list;
    private MyItemClickListener mItemClickListener;

    public SuosuoRecycleViewAdapter(Context context, List<LaolishiBean.Laolishi.GT> goods_list) {
        this.context = context;
        this.goods_list = goods_list;
    }

    //加载布局
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shangpinshow, null);
        MyViewHolder myViewHolder=new MyViewHolder(view, mItemClickListener);
        return myViewHolder;
    }

    //空间赋值
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String title = goods_list.get(position).goods_name;
        String img = goods_list.get(position).goods_image_url;
        String price = goods_list.get(position).goods_price;
        Log.i("wodetitle",price);
        holder.textView.setText(title);
        holder.price.setText(price);
        ViewGroup.LayoutParams layoutParams = holder.imageView.getLayoutParams();
        if(position==0){
            layoutParams.height=200;
        }
        holder.imageView.setLayoutParams(layoutParams);
        Picasso.with(context).load(img).placeholder(R.drawable.ic_launcher).into(holder.imageView);
    }

    //条目的数量
    @Override
    public int getItemCount() {
        return goods_list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textView;
        private ImageView imageView;
        private TextView price;
        private MyItemClickListener mListener;

        public MyViewHolder(View itemView, MyItemClickListener myItemClickListener) {
            super(itemView);
            textView =  (TextView) itemView.findViewById(R.id.textview);
            imageView =  (ImageView) itemView.findViewById(R.id.iv_imageview);
            price = (TextView) itemView.findViewById(R.id.price);
            this.mListener = myItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getPosition());
            }
        }
    }
    /**
     * 创建一个回调接口
     */
    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * 在activity里面adapter就是调用的这个方法,将点击事件监听传递过来,并赋值给全局的监听
     *
     * @param myItemClickListener
     */
    public void setItemClickListener(MyItemClickListener myItemClickListener) {
        this.mItemClickListener = myItemClickListener;
    }
}
