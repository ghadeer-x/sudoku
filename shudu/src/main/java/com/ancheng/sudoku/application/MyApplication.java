package com.ancheng.sudoku.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.ancheng.sudoku.constant.InitConstants;
import com.ancheng.sudoku.model.bean.User;
import com.ancheng.sudoku.utils.GlideImageLoader;
import com.ancheng.sudoku.utils.SPUtils;
import com.ancheng.sudoku.utils.Utils;
import com.apkfuns.logutils.LogUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;

import cn.bmob.v3.Bmob;
import cn.smssdk.SMSSDK;

/**
 * author: ancheng
 * date: 2017/5/1
 * email: lzjtugjc@163.com
 */

public class MyApplication extends Application {

    private static Context mContext;
    private SharedPreferences mSharedPreferences;
    public static SPUtils mSpUtils;

    public User getUser() {
        return mUser;
    }

    public static void setUser(User user) {
        mUser = user;
    }

    private static User mUser = null;

    public static boolean isLogin() {
        long lastLoginTime = MyApplication.mSpUtils.getLong(InitConstants.LAST_LOGIN_TIME, 0L);
        long currentTime = System.currentTimeMillis();
        long twoDayTime = 3 * 24 * 60 * 60 * 1000;
        if ((currentTime - lastLoginTime) > twoDayTime) {
            return true;
        } else {
            return false;
        }
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
        initImagePicker();
        initShareSDK();
        Utils.init(getApplicationContext());
        mSpUtils = new SPUtils(getClass().getName());

        LogUtils.getLogConfig()
                .configAllowLog(true)
                .configTagPrefix("Sudoku")
                .configShowBorders(true)
                .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}");
    }

    /**
     * 初始化图片选择器
     */
    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
        imagePicker.setMultiMode(false);

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

    private void initShareSDK() {
        SMSSDK.initSDK(this, "1e2d14e70bb54", "2102a4e2f011ca6d9cd230ed1766439b");
    }
}
