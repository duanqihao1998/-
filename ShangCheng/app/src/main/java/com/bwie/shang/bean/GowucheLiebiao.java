package com.bwie.shang.bean;

import java.util.List;

/**
 * Created by Love_you on 2017/9/15 0015.
 */
public class GowucheLiebiao {
    public GouwucheList datas;
    public class GouwucheList{
        public List<Cartlist> cart_list;
        public class Cartlist{
            public List<Goods> goods;
            public class Goods{
                public String goods_image_url;//商品图片
                public String goods_name;//商品名称
                public String goods_price;//商品价格
                public String goods_num;//商品的数量
                public String cart_id;//商品id
                public String Attribute;//颜色
            }
        }
    }
}
