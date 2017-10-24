package com.bwie.shang.bean;

import java.util.List;

/**
 * Created by Love_you on 2017/9/14 0014.
 */
public class LaolishiBean {
    public Laolishi datas;
    public class Laolishi{
        public List<GT> goods_list;
        public class GT{
            public String goods_id;//商品ID
            public String goods_name;//商品的名称
            public String goods_image_url;//商品图片
            public String goods_price;//商品的价格
        }
    }
}
