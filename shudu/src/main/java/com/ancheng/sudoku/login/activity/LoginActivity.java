package com.ancheng.sudoku.login.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.ancheng.sudoku.R;
import com.ancheng.sudoku.login.I.ILoginActivity;
import com.ancheng.sudoku.login.presenter.LoginPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements ILoginActivity {

    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_user_pwd)
    EditText etUserPwd;
    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mLoginPresenter = new LoginPresenter(this);

    }

    @OnClick({R.id.btn_login, R.id.tv_find_pwd, R.id.tv_register_user, R.id.iv_weibo, R.id.iv_qq, R.id.iv_weixin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                mLoginPresenter.login(etUserName.getText().toString().trim(),etUserName.getText().toString().trim());
                //登陆
                break;
            case R.id.tv_find_pwd:
                //找回密码
                break;
            case R.id.tv_register_user:
                //注册用户
                break;
            case R.id.iv_weibo:
                //微博登陆
                break;
            case R.id.iv_qq:
                //qq登陆
                break;
            case R.id.iv_weixin:
                //微信登陆
                break;
        }
    }

    @Override
    public void isLoginSucess(boolean isLogin) {
        if (isLogin) {
            //登陆成功

        } else {
            //登陆失败
        }
    }
}
