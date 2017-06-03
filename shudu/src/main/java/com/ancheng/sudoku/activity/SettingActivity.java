package com.ancheng.sudoku.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.ancheng.sudoku.R;
import com.ancheng.sudoku.application.MyApplication;
import com.ancheng.sudoku.constant.GameSettingConstants;
import com.ancheng.sudoku.utils.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.suke.widget.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.switch_button)
    SwitchButton switchButton;
    @BindView(R.id.rl_select_music)
    RelativeLayout rlSelectMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, Color.BLACK);
        initListener();
    }

    private void initListener() {
        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
//                    ToastUtils.showShort("打开");
                    MyApplication.mSpUtils.put(GameSettingConstants.IS_OPEN_BG_MUSIC, true);
                } else {
//                    ToastUtils.showShort("关闭");
                    MyApplication.mSpUtils.put(GameSettingConstants.IS_OPEN_BG_MUSIC, false);
                }
            }
        });
    }


    @OnClick({R.id.iv_back, R.id.rl_select_music})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_select_music:
                ToastUtils.showShort("选择音乐");
                break;
        }
    }
}
