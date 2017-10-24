package test.bwie.com.jingdong.P;

import test.bwie.com.jingdong.M.MyM;
import test.bwie.com.jingdong.M.MyModle;
import test.bwie.com.jingdong.V.MyIndexView;
import test.bwie.com.jingdong.V.MyView;

/**
 * Created by Love_you on 2017/10/17 0017.
 */

public class MyPresenter implements Presenter {
    /**
     * 注册
     */
    private MyView myView;
    private MyM myModle;

    public MyPresenter(MyView myView) {
        this.myView = myView;
        myModle = new MyModle();
    }
    @Override
    public void getData(String phone, String pass) {
        myModle.regin_user(phone, pass, myView);
    }

    /**
     * 首页
     */
    private MyIndexView myIndexView;
    private MyM indexModle;

    public MyPresenter(MyIndexView myIndexView) {
        this.myIndexView = myIndexView;
        indexModle=new MyModle();
    }
    @Override
    public void getIndex() {
        indexModle.Index(myIndexView);
    }
}
