package com.bwie.zss.persenter;

import android.content.Context;

/**
 * 1.类的用途
 * 2.@author棒棒糖：赵姗杉
 * 3.@date2017/9/28  21：01
 */

public interface ILoginPersenter {
    void servencr(Context context,String phone);
    void loginserver(Context context,String phone,String code);
}
