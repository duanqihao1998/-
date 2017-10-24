package okhttp3utilsstudy.text.com.okhttp3utils.networkutils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 1. 类的用途
 * 2. @author 王毅博
 * 3. @date 2017/9/20 15:36
 */

public class NetWorkUtils {

    public static  boolean isNetWork(Context context){
        //获取联网管理器
        ConnectivityManager connectivityManager= (ConnectivityManager)context. getSystemService(Context.CONNECTIVITY_SERVICE);
        //网络信息
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null){
            return true;
        }
        return false;
    }
}
