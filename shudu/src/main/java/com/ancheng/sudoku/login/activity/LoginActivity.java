package com.ancheng.sudoku.login.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.ancheng.sudoku.R;
import com.ancheng.sudoku.constant.GameConstant;
import com.ancheng.sudoku.login.I.ILoginActivity;
import com.ancheng.sudoku.login.presenter.LoginPresenter;
import com.ancheng.sudoku.utils.IntentTools;
import com.ancheng.sudoku.utils.ToastUtils;
import com.apkfuns.logutils.LogUtils;
import com.jaeger.library.StatusBarUtil;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements ILoginActivity {

    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_user_pwd)
    EditText etUserPwd;
    private LoginPresenter mLoginPresenter;
    private Tencent mTencent;
    private static final String TAG = "LoginActivity";
    BaseUiListener loglistener = new BaseUiListener();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, Color.BLACK);
        mTencent = Tencent.createInstance(GameConstant.TENCEN_APP_ID, this);
        mLoginPresenter = new LoginPresenter(this);
        initTencent();

    }

    private void initTencent() {
        // Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
        // 其中APP_ID是分配给第三方应用的appid，类型为String。
        mTencent = Tencent.createInstance(GameConstant.TENCEN_APP_ID, this.getApplicationContext());
        // 1.4版本:此处需新增参数，传入应用程序的全局context，可通过activity的getApplicationContext方法获取
    }

    @OnClick({R.id.btn_login, R.id.tv_find_pwd, R.id.tv_register_user, R.id.iv_weibo, R.id.iv_qq, R.id.iv_weixin})
    public void onClick(View view) {
        String number = etUserName.getText().toString().trim();
        String password = etUserPwd.getText().toString().trim();
        switch (view.getId()) {
            case R.id.btn_login:
                mLoginPresenter.login(number, password);
//                startActivity(IntentTools.getMainActivityIntent(this));
                break;
            case R.id.tv_find_pwd:
                //找回密码
                startActivity(IntentTools.getForgotPasswordActivityIntent(LoginActivity.this));
                break;
            case R.id.tv_register_user:
                //注册用户
                startActivity(IntentTools.getRegisterAccountActivityIntent(LoginActivity.this));
                break;
            case R.id.iv_weibo:
                //微博登陆
                break;
            case R.id.iv_qq:
                //qq登陆
                tencentLogin();
                break;
            case R.id.iv_weixin:
                //微信登陆
                break;
        }
    }

    private void tencentLogin() {
        if (!mTencent.isSessionValid()) {
           mTencent.login(this,"all",loglistener);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void isLoginSucess(boolean isLogin, Object o) {
        if (isLogin) {
            startActivity(IntentTools.getMainActivityIntent(this));
            finish();
        } else {
            LogUtils.d(o);
            ToastUtils.showLong("账号或密码错误！");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

//        mTencent.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode,resultCode,data,loglistener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
//            LogUtils.tag(TAG).d("登陆成功");
            if (response == null) {
                ToastUtils.showLongSafe("登陆失败");
                LogUtils.tag(TAG).d("返回为空，登陆失败：");
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (jsonResponse != null && jsonResponse.length() == 0) {
                ToastUtils.showLongSafe("登陆失败");
                LogUtils.tag(TAG).d("返回为空，登陆失败：");
                return;
            }
//            ToastUtils.showLongSafe("登陆成功");
            //登陆成功
            JSONObject json = (JSONObject) response;
            if (json.has("expires_in")) {
                try {
                    String expires_in = json.getString("expires_in");
                    LogUtils.tag(TAG).d(expires_in);
                    LogUtils.tag(TAG).d(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            startActivity(IntentTools.getMainActivityIntent(LoginActivity.this));
            finish();
        }

        @Override

        public void onError(UiError e) {

        }

        @Override

        public void onCancel() {

        }

    }
}
