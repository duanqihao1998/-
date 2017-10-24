package com.bwie.shang.bean;

import java.util.List;

/**
 * Created by Love_you on 2017/9/5 0005.
 */
public class JsonBean {
   public DataBean data;
    public class DataBean{
        public List<ItemListBean> itemList;

        public class ItemListBean{
            public String itemImage;//标题图片
            public String price;//价钱
            public String title;//商品标题
        }

    }

}
