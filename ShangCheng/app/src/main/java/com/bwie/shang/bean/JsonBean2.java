package com.bwie.shang.bean;

import java.util.List;

/**
 * Created by Love_you on 2017/9/6 0006.
 */
public class JsonBean2 {
    public DataBean2 data;
    public class DataBean2{
        public List<AD1> ad1;
        public class AD1{
            public String image;
        }
        public List<AD5> ad5;
        public class AD5{
            public String image;
        }
    }
}
