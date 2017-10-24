package test.bwie.com.jingdong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import test.bwie.com.jingdong.Bean.Miaosha;
import test.bwie.com.jingdong.Bean.TuiJianBean;
import test.bwie.com.jingdong.R;

import static android.R.attr.data;

/**
 * Created by Love_you on 2017/10/18 0018.
 */

public class TuiJIanAdapter extends RecyclerView.Adapter{
    //定义三种常量  表示三种条目类型
    public static final int TYPE_PULL_IMAGE = 0;
    public static final int TYPE_RIGHT_IMAGE = 1;

    private Context context;
    private JSONArray list1;

    public TuiJIanAdapter(Context context, JSONArray list1) {
        this.context = context;
        this.list1 = list1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType==0){
            view =View.inflate(context, R.layout.type_pull_image,null);
            return new PullImageHolder(view);
        }else if(viewType==1) {
            view =View.inflate(context, R.layout.type_right_image,null);
            return new RightImageHolderextends(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            String images = list1.getJSONObject(position).getString("images");
            String title = list1.getJSONObject(position).getString("title");
            if(holder instanceof PullImageHolder){
                String[] split = images.split("\\|");
                ((PullImageHolder) holder).texttu.setText(title);
                Picasso.with(context).load(split[0]).placeholder(R.mipmap.ic_launcher).into(((PullImageHolder) holder).imageView1);
                Picasso.with(context).load(split[1]).placeholder(R.mipmap.ic_launcher).into(((PullImageHolder) holder).imageView2);
                Picasso.with(context).load(split[2]).placeholder(R.mipmap.ic_launcher).into(((PullImageHolder) holder).imageView3);
            }
            else if(holder instanceof RightImageHolderextends){
                ((RightImageHolderextends) holder).shop_title.setText(title);
                Picasso.with(context).load(images).placeholder(R.mipmap.ic_launcher).into(((RightImageHolderextends) holder).shop_image);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list1.length();
    }
    //根据条件返回条目的类型
    @Override
    public int getItemViewType(int position) {
        try {
            JSONObject jsonObject = list1.getJSONObject(position);
            String images = jsonObject.getString("images");
            String[] split = images.split("\\|");
            if(split.length>1){
                return TYPE_PULL_IMAGE;
            }else {
                return TYPE_RIGHT_IMAGE;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return TYPE_PULL_IMAGE;
    }
    /**
     * 创建二种ViewHolder
     */
    private class RightImageHolderextends extends RecyclerView.ViewHolder {

        private TextView shop_title;
        private ImageView shop_image;
        public RightImageHolderextends(View itemView) {
            super(itemView);
            shop_title = itemView.findViewById(R.id.shop_title);
            shop_image = itemView.findViewById(R.id.shop_image);
        }
    }

    private class PullImageHolder extends RecyclerView.ViewHolder {

        private TextView texttu;
        private ImageView imageView1;
        private ImageView imageView2;
        private ImageView imageView3;

        public PullImageHolder(View itemView) {
            super(itemView);
            texttu = itemView.findViewById(R.id.shang_title);
            imageView1 = itemView.findViewById(R.id.shang_Image1);
            imageView2 = itemView.findViewById(R.id.shang_Image2);
            imageView3 = itemView.findViewById(R.id.shang_Image3);
        }
    }
}
