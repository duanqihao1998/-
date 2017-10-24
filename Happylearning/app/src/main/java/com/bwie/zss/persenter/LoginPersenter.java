package com.bwie.zss.persenter;

import android.content.Context;

import com.bwie.zss.mode.ILoginMode;
import com.bwie.zss.mode.LoginMode;
import com.bwie.zss.view.ILoginView;

/**
 * 1.类的用途
 * 2.@author棒棒糖：赵姗杉
 * 3.@date2017/9/28  21：02
 */

public class LoginPersenter implements ILoginPersenter {
    private ILoginView iLoginView;
    private final ILoginMode iLoginMode;

    public LoginPersenter(ILoginView iLoginView) {
        this.iLoginView=iLoginView;
        iLoginMode = new LoginMode();
    }
    @Override
    public void servencr(Context context, String phone) {
        iLoginMode.getPhone(context,phone);
    }
    @Override
    public void loginserver(Context context, String phone, String code) {
        iLoginMode.getLoginPhone(context,phone,code);
    }


}
