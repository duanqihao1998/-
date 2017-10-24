package com.bwie.shang.app;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.xutils.DbManager;
import org.xutils.x;

import java.io.File;

import imageloaderapp.bwie.com.mylibrary.ImageLoaderUtils;

/**
 * Created by Love_you on 2017/8/31 0031.
 */
public class MyAppliction extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration configuration = ImageLoaderUtils.getConfiguration(this);
        ImageLoader.getInstance().init(configuration);
        x.Ext.init(this);
    }
    //初始化DaoConfig
    public static DbManager getDb() {
        //创建DaoConfig
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig().setDbName("bw.db").setDbDir(new File("/mnt/sdcard")).setDbVersion(1);

        DbManager db = x.getDb(daoConfig);
        return db;
    }
}
