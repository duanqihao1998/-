package com.bwie.shang.bean;

/**
 * Created by Love_you on 2017/9/17 0017.
 */
public class ShangpinQueren {
    public String goods_image_url;//商品图片
    public String goods_name;//商品名称
    public String goods_price;//商品的价格
    public String store_goods_total;//商总价

    public ShangpinQueren(String goods_image_url, String goods_name, String goods_price, String store_goods_total) {
        this.goods_image_url = goods_image_url;
        this.goods_name = goods_name;
        this.goods_price = goods_price;
        this.store_goods_total = store_goods_total;
    }

    public String getGoods_image_url() {
        return goods_image_url;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public String getStore_goods_total() {
        return store_goods_total;
    }

    public void setGoods_image_url(String goods_image_url) {
        this.goods_image_url = goods_image_url;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public void setStore_goods_total(String store_goods_total) {
        this.store_goods_total = store_goods_total;
    }
}
