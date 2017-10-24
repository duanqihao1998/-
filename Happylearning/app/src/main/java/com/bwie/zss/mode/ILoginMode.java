package com.bwie.zss.mode;

import android.content.Context;

/**
 * 1.类的用途
 * 2.@author棒棒糖：赵姗杉
 * 3.@date2017/9/28  21：09
 */

public interface ILoginMode {
    void getPhone(Context context,String phone);
    void getLoginPhone(Context context,String phone,String code);
}
