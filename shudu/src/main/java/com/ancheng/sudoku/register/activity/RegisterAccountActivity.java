package com.ancheng.sudoku.register.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ancheng.sudoku.R;
import com.ancheng.sudoku.model.bean.User;
import com.ancheng.sudoku.register.I.IRegisterView;
import com.ancheng.sudoku.register.presenter.RegisterPresenter;
import com.ancheng.sudoku.utils.MD5Utils;
import com.ancheng.sudoku.utils.RegexUtils;
import com.ancheng.sudoku.utils.ToastUtils;
import com.apkfuns.logutils.LogUtils;
import com.jaeger.library.StatusBarUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;


public class RegisterAccountActivity extends AppCompatActivity implements IRegisterView {


    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.et_user_pwd)
    EditText etUserPwd;
    @BindView(R.id.et_validate_code)
    EditText etValidateCode;
    @BindView(R.id.send_validate_code)
    TextView sendValidateCode;
    private RegisterPresenter mRegisterPresenter;
    private String mNumber;
    private String mPassword;
    private String mCode;
    private static final String TAG = "RegisterAccountActivity";

    private CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            sendValidateCode.setText("重新发送(" + millisUntilFinished / 1000 + "s)");
        }

        @Override
        public void onFinish() {
            sendValidateCode.setEnabled(true);
            sendValidateCode.setText("发送验证码");
        }
    };

    EventHandler eh = new EventHandler() {

        @Override
        public void afterEvent(int event, int result, Object data) {
            LogUtils.tag(TAG).d("注册回调");
            if (result == SMSSDK.RESULT_COMPLETE) {
                LogUtils.tag(TAG).d("回调完成");
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    LogUtils.tag(TAG).d("提交验证码成功");
                    @SuppressWarnings("unchecked")
                    HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country");
                    String phone = (String) phoneMap.get("phone");

                    // 提交用户信息（此方法可以不调用）
                    String uid = MD5Utils.strToMD5(mNumber);
                    User user = new User();
                    user.setUser_id(uid);
                    user.setPhone_number(mNumber);
                    user.setPassword(mPassword);
                    user.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                ToastUtils.showLongSafe("注册成功！");
                            } else {
                                ToastUtils.showLongSafe("注册失败！");
                                LogUtils.tag(TAG).d("注册失败：" + e.getMessage());
                            }
                        }
                    });
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    LogUtils.tag(TAG).d("获取验证码成功");
//                    获取验证码成功
                    ToastUtils.showLongSafe("正在获取验证码，请稍后！");

                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    //返回支持发送验证码的国家列表
                    LogUtils.tag(TAG).d("国家列表");
                }
            } else {
                ((Throwable) data).printStackTrace();
                LogUtils.tag(TAG).d("回调失败" + ((Throwable) data).getMessage().toString());
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, Color.BLACK);
        mRegisterPresenter = new RegisterPresenter(this);
        SMSSDK.registerEventHandler(eh); //注册短信回调
    }


    @OnClick({R.id.send_validate_code, R.id.btn_register})
    public void onViewClicked(View view) {
        mNumber = etPhoneNumber.getText().toString().trim();
        mPassword = etUserPwd.getText().toString().trim();
        mCode = etValidateCode.getText().toString().trim();
        switch (view.getId()) {
            case R.id.send_validate_code:
                if (TextUtils.isEmpty(mNumber)) {
                    ToastUtils.showLong("手机号不能为空");
                    return;
                }
                if (RegexUtils.isMobileExact(mNumber)) {
                    sendValidateCode.setEnabled(false);
                    timer.start();
                    SMSSDK.getVerificationCode("86", mNumber);
                } else {
                    ToastUtils.showLong("请输入正确的手机号");
                }
                break;
            case R.id.btn_register:
                //mRegisterPresenter.register(number, password);
                RegisterPage registerPage = new RegisterPage();
                if (TextUtils.isEmpty(mNumber) || TextUtils.isEmpty(mPassword) || TextUtils.isEmpty(mCode)) {
                    ToastUtils.showLong("手机号、密码或者验证码不能为空");
                    return;
                }
                if (RegexUtils.isMobileSimple(mNumber)) {
                    ToastUtils.showShort("正在注册，请稍后！");
                    LogUtils.tag(TAG).d("手机号：" + mNumber);
                    LogUtils.tag(TAG).d("密码：" + mPassword);
                    LogUtils.tag(TAG).d("验证码：" + mCode);
                    SMSSDK.submitVerificationCode("86", mNumber, mCode);
                } else {
                    ToastUtils.showLong("请输入正确的手机号!");
                }
                break;
        }
    }

    @Override
    public void registerSuccessful() {
        //注成功 提示用户，并调转到登陆
        ToastUtils.showLong("注册成功");
        //startActivity(IntentTools.getLoginActivityIntent(this));
    }

    @Override
    public void registerFailure(Exception e) {
        //注册失败
        LogUtils.d((BmobException) e);
        ToastUtils.showLong("注册失败");
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.registerEventHandler(eh); //注册短信回调

    }
}
