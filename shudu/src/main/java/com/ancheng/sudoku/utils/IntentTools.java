package com.ancheng.sudoku.utils;

import android.content.Context;
import android.content.Intent;

import com.ancheng.sudoku.activity.AddFriendActivity;
import com.ancheng.sudoku.activity.EmailSetActivity;
import com.ancheng.sudoku.activity.GameListActivity;
import com.ancheng.sudoku.activity.GameViewActivity;
import com.ancheng.sudoku.activity.HelpActivity;
import com.ancheng.sudoku.activity.MainActivity;
import com.ancheng.sudoku.activity.MineInfoActivity;
import com.ancheng.sudoku.activity.MineScoreActivity;
import com.ancheng.sudoku.activity.MusicListActivity;
import com.ancheng.sudoku.activity.NickNameSetActivity;
import com.ancheng.sudoku.activity.SettingActivity;
import com.ancheng.sudoku.activity.SignatureSetActivity;
import com.ancheng.sudoku.constant.GameConstant;
import com.ancheng.sudoku.forgotpassword.activity.ForgotPasswordActivity;
import com.ancheng.sudoku.login.activity.LoginActivity;
import com.ancheng.sudoku.register.activity.RegisterAccountActivity;

import cz.romario.opensudoku.gui.SudokuListActivity;

/**
 * author: ancheng
 * date: 2017/5/20
 * email: lzjtugjc@163.com
 */

public class IntentTools {

    public static Intent getMainActivityIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        return intent;
    }

    public static Intent getLoginActivityIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, LoginActivity.class);
        return intent;
    }

    public static Intent getRegisterAccountActivityIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, RegisterAccountActivity.class);
        return intent;
    }


    public static Intent getForgotPasswordActivityIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ForgotPasswordActivity.class);
        return intent;
    }

    /**
     * 帮助页面
     *
     * @param context
     * @return
     */
    public static Intent getHelpActivityIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, HelpActivity.class);
        return intent;
    }

    /**
     * 我的个人信息
     *
     * @param context
     * @return
     */
    public static Intent getMineInfoActivityIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MineInfoActivity.class);
        return intent;
    }

    /**
     * 我的历史成绩
     *
     * @param context
     * @return
     */
    public static Intent getMineScoreActivityIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MineScoreActivity.class);
        return intent;
    }

    /**
     * 我的设置
     *
     * @param context
     * @return
     */
    public static Intent getSettingActivityIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, SettingActivity.class);
        return intent;
    }

//    public static Intent getGameMenuListActivityIntent(Context context, int level) {
//        Intent intent = new Intent();
//        intent.putExtra("level", level);
//        intent.setClass(context, GameMenuListActivity1.class);
//        return intent;
//    }


//    Intent i = new Intent(this, SudokuListActivity.class);
//                i.putExtra(SudokuListActivity.EXTRA_FOLDER_ID, 0);
//    startActivity(i);

//    public static Intent getMineInfoSaveActivityIntent(Context context,String type) {
//        Intent intent = new Intent();
//        intent.putExtra(NickNameSetActivity.DATA_TYPE,type);
//        intent.setClass(context, NickNameSetActivity.class);
//        return intent;
//    }

    /**
     * 更改昵称
     *
     * @param context
     * @return
     */
    public static Intent getNickNameSetActivityIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, NickNameSetActivity.class);
        return intent;
    }

    /**
     * 绑定邮箱
     *
     * @param context
     * @return
     */
    public static Intent getEmailSetActivityIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, EmailSetActivity.class);
        return intent;
    }

    /**
     * 更改签名信息
     *
     * @param context
     * @return
     */
    public static Intent getSignatureSetActivityIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, SignatureSetActivity.class);
        return intent;
    }

    /**
     * 游戏列表
     */
    public static Intent getGameMenuListActivityIntent(Context context, Long floderId) {
        Intent intent = new Intent();
        intent.putExtra(SudokuListActivity.EXTRA_FOLDER_ID, floderId);
        intent.setClass(context, GameListActivity.class);
        return intent;
    }


    /**
     * 游戏列表
     */
    public static Intent getGameViewActivityIntent(Context context, long gameLevel, long gameId, int position) {
        Intent intent = new Intent();
        intent.putExtra(GameConstant.GAME_LEVEL, gameLevel);
        intent.putExtra(GameViewActivity.EXTRA_SUDOKU_ID, gameId);
        intent.putExtra(GameConstant.GAME_ID, position);
        intent.setClass(context, GameViewActivity.class);
        return intent;
    }


    /**
     * 游戏列表
     */
    public static Intent getMusicListActivityIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MusicListActivity.class);
        return intent;
    }

    /**
     * 添加好友
     */
    public static Intent getAddFriendActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, AddFriendActivity.class);
        return intent;
    }
}
