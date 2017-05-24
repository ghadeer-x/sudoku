package com.ancheng.sudoku.application;

import android.app.Application;

/**
 * author: ancheng
 * date: 2017/5/1
 * email: lzjtugjc@163.com
 */

public class MyApplication extends Application {

    public static boolean isLogin(){
        return false;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

}
