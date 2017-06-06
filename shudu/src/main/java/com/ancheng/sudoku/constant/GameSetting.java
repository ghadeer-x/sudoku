package com.ancheng.sudoku.constant;

import com.ancheng.sudoku.application.MyApplication;

/**
 * author: ancheng
 * date: 2017/5/26
 * email: lzjtugjc@163.com
 */

public class GameSetting {

    public static boolean isOpenMusicSwitch() {
        return MyApplication.mSpUtils.getBoolean(GameConstant.IS_OPEN_BG_MUSIC, true);
    }

    public static void closeMusic() {
        MyApplication.mSpUtils.put(GameConstant.IS_OPEN_BG_MUSIC, false);
    }

    public static void openMusic() {
        MyApplication.mSpUtils.put(GameConstant.IS_OPEN_BG_MUSIC, true);
    }

    public static void putMusicPath(String musicPath) {
        MyApplication.mSpUtils.put(GameConstant.BG_MUSIC_PATH, musicPath);
    }

    public static String getMusicPath() {
        return MyApplication.mSpUtils.getString(GameConstant.BG_MUSIC_PATH);
    }

    public static void putMusicName(String musicName) {
        MyApplication.mSpUtils.put(GameConstant.BG_MUSIC_NAME, musicName);
    }

    public static String getMusicName() {
        return MyApplication.mSpUtils.getString(GameConstant.BG_MUSIC_NAME);
    }
}
