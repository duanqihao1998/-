package test.bwie.com.jingdong.api;

/**
 * Created by Love_you on 2017/10/17 0017.
 */

public class API {
    public static String regin="http://120.27.23.105/user/reg";//用户注册
    public static String login="http://120.27.23.105/user/login";//用户登陆
    public static String getAd="http://120.27.23.105/ad/getAd";//首页广告
    public static String search="http://120.27.23.105/product/searchProducts?keywords=";//搜索商品
    public static String getProductDetail="http://120.27.23.105/product/getProductDetail";//商品详情
    public static String addCart="http://120.27.23.105/product/addCart";//添加购物车
    public static String getCarts="http://120.27.23.105/product/getCarts";//查询数据库
    public static String deleteCart="http://120.27.23.105/product/deleteCart";//删除购物车物品
    public static String getCatagory="http://120.27.23.105/product/getCatagory";//(一级接口)用于首页九宫格，和底部页签分类页
    public static String getProductCatagory="http://120.27.23.105/product/getProductCatagory";//(二级接口)
}
