package com.ancheng.sudoku.register.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.ancheng.sudoku.R;
import com.ancheng.sudoku.register.I.IRegisterView;
import com.ancheng.sudoku.register.presenter.RegisterPresenter;
import com.ancheng.sudoku.utils.ToastUtils;
import com.apkfuns.logutils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;

public class RegisterAccountActivity extends AppCompatActivity implements IRegisterView {


    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.et_user_pwd)
    EditText etUserPwd;
    @BindView(R.id.et_validate_code)
    EditText etValidateCode;
    private RegisterPresenter mRegisterPresenter;

//    cn.smssdk.EventHandler eh = new cn.smssdk.EventHandler() {
//        @Override
//        public void afterEvent(int event, int result, Object data) {
//
//            if (result == SMSSDK.RESULT_COMPLETE) {
//                //回调完成
//                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
//                    //提交验证码成功
//                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
//                    //获取验证码成功
//                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
//                    //返回支持发送验证码的国家列表
//                }
//            } else {
//                ((Throwable) data).printStackTrace();
//            }
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);
        ButterKnife.bind(this);
        mRegisterPresenter = new RegisterPresenter(this);
        //SMSSDK.registerEventHandler(eh); //注册短信回调
    }


    @OnClick({R.id.send_validate_code, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.send_validate_code:
                break;
            case R.id.btn_register:
                String number = etPhoneNumber.getText().toString().trim();
                String password = etUserPwd.getText().toString().trim();
                mRegisterPresenter.register(number, password);
                LogUtils.d("number = %s, password = %s", number, password);
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
        LogUtils.d((BmobException)e);
        ToastUtils.showLong("注册失败");
    }
}
