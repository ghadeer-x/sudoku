package com.ancheng.sudoku.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ancheng.sudoku.R;
import com.ancheng.sudoku.constant.GameSetting;
import com.ancheng.sudoku.utils.IntentTools;
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
    @BindView(R.id.tv_music_name)
    TextView tvMusicName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, Color.BLACK);

        initView();
        initListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        String musicName = GameSetting.getMusicName();
        String musicPath = GameSetting.getMusicPath();
        if (TextUtils.isEmpty(musicPath)) {
            tvMusicName.setVisibility(View.INVISIBLE);
        }else{
            tvMusicName.setVisibility(View.VISIBLE);
            tvMusicName.setText(musicName);
        }
    }

    private void initView() {
        switchButton.setChecked(GameSetting.isOpenMusicSwitch());
    }

    private void initListener() {
        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    GameSetting.openMusic();
                } else {
                    GameSetting.closeMusic();
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
                //    ToastUtils.showShort("选择音乐");
                startActivity(IntentTools.getMusicListActivityIntent(this));
                break;
        }
    }
}
