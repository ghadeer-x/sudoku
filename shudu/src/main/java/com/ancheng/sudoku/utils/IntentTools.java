package com.ancheng.sudoku.utils;

import android.content.Context;
import android.content.Intent;

import com.ancheng.sudoku.activity.MainActivity;
import com.ancheng.sudoku.forgotpassword.activity.ForgotPasswordActivity;
import com.ancheng.sudoku.login.activity.LoginActivity;
import com.ancheng.sudoku.register.activity.RegisterAccountActivity;

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
}
