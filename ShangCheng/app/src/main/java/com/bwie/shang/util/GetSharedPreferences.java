package com.bwie.shang.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Love_you on 2017/9/14 0014.
 */
public class GetSharedPreferences {

    //用来保存数据信息
    public static SharedPreferences mSharedPreferences;
    public static SharedPreferences.Editor mEditor;
    public static void init(Context context) {
        mSharedPreferences = context.getSharedPreferences("system", Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }
}
