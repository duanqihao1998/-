package com.bwie.shang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.shang.Gouwuche.ShoppingCartAdapter;
import com.bwie.shang.Gouwuche.ShoppingCartBean;
import com.bwie.shang.R;
import com.bwie.shang.activity.DingDanxiangqingActivity;
import com.bwie.shang.activity.LoginAndRegisterActivity;
import com.bwie.shang.bean.GowucheLiebiao;
import com.bwie.shang.bean.Shangpin;
import com.bwie.shang.util.SharedUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import text.bwie.com.okhttpclient.util.OkHttp3Utils;

/**
 * Created by Love_you on 2017/8/31 0031.
 */
public class GouFragment extends Fragment implements View.OnClickListener
        , ShoppingCartAdapter.CheckInterface, ShoppingCartAdapter.ModifyCountInterface {

    Button btnBack;
    //全选
    CheckBox ckAll;
    //总额
    TextView tvShowPrice;
    //结算
    TextView tvSettlement;
    //编辑
    TextView btnEdit;//tv_edit

    ListView list_shopping_cart;
    private ShoppingCartAdapter shoppingCartAdapter;
    private boolean flag = false;
    private List<ShoppingCartBean> shoppingCartBeanList = new ArrayList<>();
    private boolean mSelect;
    private double totalPrice = 0.00;// 购买的商品总价
    private int totalCount = 0;// 购买的商品总数量
    private ListView listView;
    private View view;
    private SharedUtil instances;
    private String url="http://169.254.229.178/mobile/index.php?act=member_cart&op=cart_list";//商品列表
    private String key;
    private LinearLayout linearLayout1;
    private TextView textViewGouwuche;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                String json = (String) msg.obj;
                try {
                    JSONObject jsonObject=new JSONObject(json);
                    int code = jsonObject.getInt("code");
                    if (code==200){
                        Gson gson=new Gson();
                        GowucheLiebiao gowucheLiebiao = gson.fromJson(json, GowucheLiebiao.class);
                        GowucheLiebiao.GouwucheList datas = gowucheLiebiao.datas;
                        List<GowucheLiebiao.GouwucheList.Cartlist> cart_list = datas.cart_list;
                        goods = cart_list.get(0).goods;
                        shoppingCartBeanList.clear();
                        for (int i = 0; i < goods.size(); i++) {
                            ShoppingCartBean shoppingCartBean = new ShoppingCartBean();
                            shoppingCartBean.setShoppingName(goods.get(i).goods_name);
                            shoppingCartBean.setAttribute("黑白色");
                            double d = Double.parseDouble(goods.get(i).goods_price);
                            shoppingCartBean.setPrice(d);
                            int id = Integer.parseInt(goods.get(i).cart_id);
                            shoppingCartBean.setId(id);
                            int count = Integer.parseInt(goods.get(i).goods_num);
                            shoppingCartBean.setCount(count);
                            shoppingCartBean.setImageUrl(goods.get(i).goods_image_url);
                            shoppingCartBeanList.add(shoppingCartBean);
                        }
                        list_shopping_cart.setAdapter(shoppingCartAdapter);
                        shoppingCartAdapter.setShoppingCartBeanList(shoppingCartBeanList);
                        Toast.makeText(getActivity(), "购物车列表请求成功", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "购物车请求失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private Boolean config;
    private List<GowucheLiebiao.GouwucheList.Cartlist.Goods> goods;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_shopping_cart_activity,container,false);
        listView = (ListView) view.findViewById(R.id.list_shopping_cart);
        listView.setAdapter(shoppingCartAdapter);
        initView();
        textViewGouwuche = (TextView) view.findViewById(R.id.TextViewGouwuche);
        linearLayout1 = (LinearLayout) view.findViewById(R.id.linearLayout1);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        instances = SharedUtil.getInstances();
        config = (Boolean) instances.getValueByKey(getActivity(),"config",false);
        if(config ==true){
            key = (String) instances.getValueByKey(getActivity(), "key", "");
            Log.i("Gouwukey",key);
            linearLayout1.setVisibility(View.VISIBLE);
            initData();
        }else {
            textViewGouwuche.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), "还没有登陆", Toast.LENGTH_SHORT).show();
            textViewGouwuche.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(), LoginAndRegisterActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    //初始化控件
    private void initView() {
        btnBack= (Button) view.findViewById(R.id.btn_back);
        ckAll= (CheckBox) view.findViewById(R.id.ck_all);
        tvShowPrice= (TextView) view.findViewById(R.id.tv_show_price);
        tvSettlement= (TextView) view.findViewById(R.id.tv_settlement);
        btnEdit= (TextView) view.findViewById(R.id.bt_header_right);
        list_shopping_cart= (ListView) view.findViewById(R.id.list_shopping_cart);

        btnEdit.setOnClickListener(this);
        ckAll.setOnClickListener(this);
        tvSettlement.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    //初始化数据
    protected void initData() {
        HashMap<String,String> params=new HashMap<>();
        params.put("key",key);
        OkHttp3Utils.doPost(url, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Log.i("shangpin",json);
                Message message=new Message();
                message.obj=json;
                message.what=0;
                handler.sendMessage(message);
            }
        });

        for (int i = 0; i < 2; i++) {
//            ShoppingCartBean shoppingCartBean = new ShoppingCartBean();
//            shoppingCartBean.setShoppingName("上档次的T桖");
//            shoppingCartBean.setDressSize(20);
//            shoppingCartBean.setId(i);
//            shoppingCartBean.setPrice(30.6);
//            shoppingCartBean.setCount(1);
//            shoppingCartBean.setImageUrl("https://img.alicdn.com/bao/uploaded/i2/TB1YfERKVXXXXanaFXXXXXXXXXX_!!0-item_pic.jpg_430x430q90.jpg");
//            shoppingCartBeanList.add(shoppingCartBean);
        }
        for (int i = 0; i < 2; i++) {
//            ShoppingCartBean shoppingCartBean = new ShoppingCartBean();
//            shoppingCartBean.setShoppingName("瑞士正品夜光男女士手表情侣精钢带男表防水石英学生非天王星机械");
//            shoppingCartBean.setAttribute("黑白色");
//            shoppingCartBean.setPrice(89);
//            shoppingCartBean.setId(i+2);
//            shoppingCartBean.setCount(3);
//            shoppingCartBean.setImageUrl("https://gd1.alicdn.com/imgextra/i1/2160089910/TB2M_NSbB0kpuFjSsppXXcGTXXa_!!2160089910.jpg");
//            shoppingCartBeanList.add(shoppingCartBean);
        }
        shoppingCartAdapter = new ShoppingCartAdapter(getActivity());
        shoppingCartAdapter.setCheckInterface(this);
        shoppingCartAdapter.setModifyCountInterface(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //全选按钮
            case R.id.ck_all:
                if (shoppingCartBeanList.size() != 0) {
                    if (ckAll.isChecked()) {
                        for (int i = 0; i < shoppingCartBeanList.size(); i++) {
                            shoppingCartBeanList.get(i).setChoosed(true);
                        }
                        shoppingCartAdapter.notifyDataSetChanged();
                    } else {
                        for (int i = 0; i < shoppingCartBeanList.size(); i++) {
                            shoppingCartBeanList.get(i).setChoosed(false);
                        }
                        shoppingCartAdapter.notifyDataSetChanged();
                    }
                }
                statistics();
                break;
            case R.id.bt_header_right:
                flag = !flag;
                if (flag) {
                    btnEdit.setText("完成");
                    shoppingCartAdapter.isShow(false);
                } else {
                    btnEdit.setText("编辑");
                    shoppingCartAdapter.isShow(true);
                }
                break;
            case R.id.tv_settlement: //结算
                lementOnder();
                break;
            case R.id.btn_back:
                break;
        }
    }

    /**
     * 结算订单、支付
     */
    private void lementOnder() {
        List<Shangpin> lists=new ArrayList<>();
        //选中的需要提交的商品清单
        for (ShoppingCartBean bean:shoppingCartBeanList ){
            boolean choosed = bean.isChoosed();
            if (choosed){
                String shoppingName = bean.getShoppingName();
                int count = bean.getCount();
                double price = bean.getPrice();
                int size = bean.getDressSize();
                String attribute = bean.getAttribute();
                int id = bean.getId();
                Shangpin shangpin=new Shangpin(count,id);
                lists.add(shangpin);
            }
        }
        //跳转到支付界面
        Intent intent=new Intent(getActivity(), DingDanxiangqingActivity.class);
        intent.putExtra("lists", (Serializable) lists);
        startActivity(intent);
    }
    /**
     * 单选
     * @param position  组元素位置
     * @param isChecked 组元素选中与否
     */
    @Override
    public void checkGroup(int position, boolean isChecked) {
        shoppingCartBeanList.get(position).setChoosed(isChecked);
        if (isAllCheck())
            ckAll.setChecked(true);
        else
            ckAll.setChecked(false);
        shoppingCartAdapter.notifyDataSetChanged();
        statistics();
    }
    /**
     * 遍历list集合
     * @return
     */
    private boolean isAllCheck() {

        for (ShoppingCartBean group : shoppingCartBeanList) {
            if (!group.isChoosed())
                return false;
        }
        return true;
    }
    /**
     * 统计操作
     * 1.先清空全局计数器<br>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作
     * 3.给底部的textView进行数据填充
     */
    public void statistics() {
        totalCount = 0;
        totalPrice = 0.00;
        for (int i = 0; i < shoppingCartBeanList.size(); i++) {
            ShoppingCartBean shoppingCartBean = shoppingCartBeanList.get(i);
            if (shoppingCartBean.isChoosed()) {
                totalCount++;
                totalPrice += shoppingCartBean.getPrice() * shoppingCartBean.getCount();
            }
        }
        tvShowPrice.setText("合计:" + totalPrice);
        tvSettlement.setText("结算(" + totalCount + ")");
    }
    /**
     * 增加
     * @param position      组元素位置
     * @param showCountView 用于展示变化后数量的View
     * @param isChecked     子元素选中与否
     */
    @Override
    public void doIncrease(int position, View showCountView, boolean isChecked) {
        ShoppingCartBean shoppingCartBean = shoppingCartBeanList.get(position);
        int currentCount = shoppingCartBean.getCount();
        currentCount++;
        shoppingCartBean.setCount(currentCount);
        ((TextView) showCountView).setText(currentCount + "");
        shoppingCartAdapter.notifyDataSetChanged();
        statistics();
    }
    /**
     * 删减
     *
     * @param position      组元素位置
     * @param showCountView 用于展示变化后数量的View
     * @param isChecked     子元素选中与否
     */
    @Override
    public void doDecrease(int position, View showCountView, boolean isChecked) {
        ShoppingCartBean shoppingCartBean = shoppingCartBeanList.get(position);
        int currentCount = shoppingCartBean.getCount();
        if (currentCount == 1) {
            return;
        }
        currentCount--;
        shoppingCartBean.setCount(currentCount);
        ((TextView) showCountView).setText(currentCount + "");
        shoppingCartAdapter.notifyDataSetChanged();
        statistics();
    }
    /**
     * 删除
     *
     * @param position
     */
    @Override
    public void childDelete(int position) {
        String deleteurl="http://169.254.229.178/mobile/index.php?act=member_cart&op=cart_del";
        HashMap<String,String> parmas=new HashMap<>();
        parmas.put("key",key);
        parmas.put("cart_id",goods.get(position).cart_id);
        OkHttp3Utils.doPost(deleteurl, parmas, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.i("delete",string);
            }
        });

        shoppingCartBeanList.remove(position);
        shoppingCartAdapter.notifyDataSetChanged();
        statistics();
    }
}