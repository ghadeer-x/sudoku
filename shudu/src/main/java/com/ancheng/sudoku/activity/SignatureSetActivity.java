package com.ancheng.sudoku.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.ancheng.sudoku.R;
import com.ancheng.sudoku.model.UserManager;
import com.ancheng.sudoku.model.bean.User;
import com.ancheng.sudoku.utils.ToastUtils;
import com.apkfuns.logutils.LogUtils;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class SignatureSetActivity extends AppCompatActivity {

    @BindView(R.id.et_signature)
    EditText etSignature;
    private static final String TAG = "SignatureSetActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature_set);
        ButterKnife.bind(this);

        StatusBarUtil.setColor(this, Color.BLACK);
        String signature = UserManager.getInstance().getSignature();
        if (TextUtils.isEmpty(signature)) {
            etSignature.setHint("请输入签名");
        } else {
            etSignature.setHint(signature);
        }
    }

    @OnClick({R.id.iv_back, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_save:
                final String signature = etSignature.getText().toString().trim();
                LogUtils.tag(TAG).d(signature);
                if (signature.isEmpty()) {
                    return;
                }

                String objectId = UserManager.getInstance().getObjectId();
                if (TextUtils.isEmpty(objectId)) {
                    return;
                }
                //保存信息
                User user = new User();
                user.setSignature(signature);
                user.update(objectId, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            ToastUtils.showLong("设置成功！");
                            UserManager.getInstance().setSignature(signature);
                        } else {
                            ToastUtils.showLong("设置失败！");
                            LogUtils.tag(TAG).e(e);
                        }
                    }
                });
                break;
        }
    }
}
