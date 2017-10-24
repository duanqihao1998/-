package com.bwie.zss.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.zss.R;
import com.bwie.zss.javabean.PayItemData;

import java.util.List;

/**
 * 1. 类的用途
 * 2. @author 赵姗杉
 * 3. @date 2017/10/5 16:27
 */

public class ChangePayApater extends BaseAdapter{
    Context context;
    List<PayItemData> payItemBeen;

    public ChangePayApater(Context context, List<PayItemData> payItemBeen) {
        this.context = context;
        this.payItemBeen = payItemBeen;
    }

    @Override
    public int getCount() {
        return payItemBeen.size();
    }

    @Override
    public Object getItem(int i) {
        return payItemBeen.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view==null){
             holder = new ViewHolder();
            view = View.inflate(context, R.layout.pay_item,null);
            holder.pay_img = view.findViewById(R.id.pay_img);
            holder.tv_title = view.findViewById(R.id.pay_title);
            holder.tv_con = view.findViewById(R.id.pay_con);
            holder.check = view.findViewById(R.id.pay_ck);
            view.setTag(holder);
        }else{
          holder = (ViewHolder) view.getTag();
        }
        final PayItemData bean = payItemBeen.get(i);
        holder.pay_img.setImageResource(bean.getImage());
        holder.tv_title.setText(bean.getTitle());
        holder.tv_con.setText(bean.getContent());
        holder.check.setChecked(bean.isChecked());


        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = holder.check.isChecked();
                for (int i= 0;i<payItemBeen.size();i++){
                    payItemBeen.get(i).setChecked(false);
                }
                bean.setChecked(checked);
                holder.check.setChecked(checked);
                notifyDataSetChanged();
            }
        });
        return view;
    }
    class ViewHolder{
        ImageView pay_img;
        TextView tv_title;
        TextView tv_con;
        CheckBox check;
    }

}
