package com.ancheng.sudoku.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.ancheng.sudoku.utils.SPUtils;
import com.ancheng.sudoku.utils.Utils;
import com.apkfuns.logutils.LogUtils;

import cn.bmob.v3.Bmob;

/**
 * author: ancheng
 * date: 2017/5/1
 * email: lzjtugjc@163.com
 */

public class MyApplication extends Application {

    private static Context mContext;
    private SharedPreferences mSharedPreferences;
    public static SPUtils mSpUtils;

    public static boolean isLogin(){
        return false;
    }

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //提供以下两种方式进行初始化操作：
        mContext = getApplicationContext();
        initBmob();
        initShareSDK();
        Utils.init(getApplicationContext());
        mSpUtils = new SPUtils(getClass().getName());

        LogUtils.getLogConfig()
                .configAllowLog(true)
                .configTagPrefix("Sudoku")
                .configShowBorders(true)
                .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}");
    }

    private void initBmob() {
        //第一：默认初始化
        Bmob.initialize(getApplicationContext(), "44824830d4cc69adff431ed685dfc456");
        // 注:自v3.5.2开始，数据sdk内部缝合了统计sdk，开发者无需额外集成，传渠道参数即可，不传默认没开启数据统计功能
        //Bmob.initialize(this, "Your Application ID","bmob");

        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        //BmobConfig config =new BmobConfig.Builder(this)
        ////设置appkey
        //.setApplicationId("Your Application ID")
        ////请求超时时间（单位为秒）：默认15s
        //.setConnectTimeout(30)
        ////文件分片上传时每片的大小（单位字节），默认512*1024
        //.setUploadBlockSize(1024*1024)
        ////文件的过期时间(单位为秒)：默认1800s
        //.setFileExpiration(2500)
        //.build();
        //Bmob.initialize(config);
    }

    private void initShareSDK(){
        //SMSSDK.initSDK(this, "1e2d14e70bb54", "2102a4e2f011ca6d9cd230ed1766439b");
    }
}
