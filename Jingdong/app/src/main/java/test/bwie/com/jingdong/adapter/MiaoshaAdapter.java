package test.bwie.com.jingdong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import test.bwie.com.jingdong.Bean.Miaosha;
import test.bwie.com.jingdong.R;

/**
 * Created by Love_you on 2017/10/18 0018.
 */

public class MiaoshaAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Miaosha> list;

    public MiaoshaAdapter(Context context, List<Miaosha> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_item, null);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder){
            ((ViewHolder) holder).shangname.setText(list.get(position).getTitle());
            Picasso.with(context).load(list.get(position).getImage()).placeholder(R.mipmap.ic_launcher).into(((ViewHolder) holder).shangimage);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView shangimage;
        private TextView shangname;

        public ViewHolder(View itemView) {
            super(itemView);
            shangimage = itemView.findViewById(R.id.shangimage);
            shangname = itemView.findViewById(R.id.shangname);
        }
    }
}
