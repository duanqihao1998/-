package com.bwie.shang.bean;

import java.util.List;

/**
 * Created by Love_you on 2017/9/11 0011.
 */
public class JsonBeanSou {
    public JsonBeanSou1 result;
    public class JsonBeanSou1 {
        public JsonBeanSou2 wall;
        public class JsonBeanSou2 {
            public List<JSON> docs;
            public class JSON{
                public String img;//图片
                public String title;//商品名字
                public String price;//商品价格
            }
        }
    }
}
