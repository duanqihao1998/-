package test.bwie.com.jingdong.P;

import test.bwie.com.jingdong.M.MyLoginModle;
import test.bwie.com.jingdong.V.MyLoginView;

/**
 * Created by Love_you on 2017/10/18 0018.
 */

public class MyLoginPresenter implements LoginPresenter {
    private MyLoginView myLoginView;
    private MyLoginModle myLoginModle;

    public MyLoginPresenter(MyLoginView myLoginView) {
        this.myLoginView = myLoginView;
        myLoginModle=new MyLoginModle();
    }

    @Override
    public void getData(String phone, String pass) {
        myLoginModle.JiaoYan(phone,pass,myLoginView);
    }
}
