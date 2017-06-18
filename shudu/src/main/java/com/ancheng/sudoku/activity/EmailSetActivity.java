package com.ancheng.sudoku.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.ancheng.sudoku.R;
import com.ancheng.sudoku.model.bean.User;
import com.ancheng.sudoku.utils.RegexUtils;
import com.ancheng.sudoku.utils.ToastUtils;
import com.ancheng.sudoku.model.UserManager;
import com.apkfuns.logutils.LogUtils;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class EmailSetActivity extends AppCompatActivity {

    @BindView(R.id.et_emial)
    EditText etEmial;
    private static final String TAG = "EmailSetActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_set);
        ButterKnife.bind(this);

        StatusBarUtil.setColor(this, Color.BLACK);
        String email = UserManager.getInstance().getEmail();
        if (TextUtils.isEmpty(email)) {
            etEmial.setHint("请输入邮箱号");
        } else {
            etEmial.setHint(email);
        }
    }

    @OnClick({R.id.iv_back, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_save:
                final String email = etEmial.getText().toString().trim();
                LogUtils.tag(TAG).d(email);
                if (email.isEmpty()) {
                    return;
                }

                if (!RegexUtils.isEmail(email) || email == null) {
                    ToastUtils.showLong("邮箱格式错误，请重新输入！");
                    return;
                }

                String objectId = UserManager.getInstance().getObjectId();
                if (TextUtils.isEmpty(objectId)) {
                    return;
                }
                //保存信息
                User user = new User();
                user.setEmail(email);
                user.update(objectId, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            ToastUtils.showLong("设置成功！");
                            UserManager.getInstance().setEmail(email);
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
