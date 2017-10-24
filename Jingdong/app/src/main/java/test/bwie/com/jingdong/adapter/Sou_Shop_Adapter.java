package test.bwie.com.jingdong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import test.bwie.com.jingdong.R;

/**
 * Created by Love_you on 2017/10/18 0018.
 */

public class Sou_Shop_Adapter extends RecyclerView.Adapter implements View.OnClickListener {
    private OnItemClickListener mOnItemClickListener = null;
    private Context context;
    private JSONArray data;

    public Sou_Shop_Adapter(Context context, JSONArray data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_recycleview_tiem, null);
        ViewHolder viewHolder=new ViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        holder.mTextView.setText(datas[position]);
        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
        if(holder instanceof ViewHolder){
            try {
                ((ViewHolder) holder).biaoti.setText(data.getJSONObject(position).getString("title"));
                String images = data.getJSONObject(position).getString("images");
                String[] split = images.split("\\|");
                if(split.length>1){
                    Picasso.with(context).load(split[1]).placeholder(R.mipmap.ic_launcher).into(((ViewHolder) holder).shangpin_image);
                }else {
                    Picasso.with(context).load(images).placeholder(R.mipmap.ic_launcher).into(((ViewHolder) holder).shangpin_image);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.length();
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(view,(int)view.getTag());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView shangpin_image;
        private final TextView biaoti;

        public ViewHolder(View itemView) {
            super(itemView);
            shangpin_image = itemView.findViewById(R.id.shangpin_image);
            biaoti = itemView.findViewById(R.id.biaoti);
        }
    }
    //define interface
    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
