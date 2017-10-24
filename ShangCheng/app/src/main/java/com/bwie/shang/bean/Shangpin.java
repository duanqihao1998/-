package com.bwie.shang.bean;

import java.io.Serializable;

/**
 * Created by Love_you on 2017/9/16 0016.
 */
public class Shangpin implements Serializable {
    public int count;//数量
    public int id;//商品id

    public Shangpin(int count, int id) {
        this.count = count;
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public int getId() {
        return id;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setId(int id) {
        this.id = id;
    }
}
