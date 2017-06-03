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

public class NickNameSetActivity extends AppCompatActivity {

    @BindView(R.id.et_nick_name)
    EditText etNickName;
    private static final String TAG = "NickNameSetActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nick_name_set);
        ButterKnife.bind(this);

        StatusBarUtil.setColor(this, Color.BLACK);
        String nickName = UserManager.getInstance().getNickName();
        if (TextUtils.isEmpty(nickName)) {
            etNickName.setHint("数独大师");
        } else {
            etNickName.setHint(nickName);
        }
    }

    @OnClick({R.id.iv_back, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_save:
                final String nickName = etNickName.getText().toString().trim();
                LogUtils.tag(TAG).d(nickName);
                if (nickName == null) {
                    return;
                }

                String objectId = UserManager.getInstance().getObjectId();
                if (TextUtils.isEmpty(objectId)) {
                    return;
                }

                //保存信息
                User user = new User();
                user.setNickName(nickName);
                user.update(objectId, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            ToastUtils.showLong("设置成功！");
                            UserManager.getInstance().setNickName(nickName);
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
