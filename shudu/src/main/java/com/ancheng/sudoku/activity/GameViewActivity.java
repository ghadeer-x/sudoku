package com.ancheng.sudoku.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ancheng.sudoku.R;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameViewActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_setting)
    ImageView ivSetting;
    @BindView(R.id.tv_num_1)
    TextView tvNum1;
    @BindView(R.id.tv_num_2)
    TextView tvNum2;
    @BindView(R.id.tv_num_3)
    TextView tvNum3;
    @BindView(R.id.tv_num_4)
    TextView tvNum4;
    @BindView(R.id.tv_num_5)
    TextView tvNum5;
    @BindView(R.id.tv_num_6)
    TextView tvNum6;
    @BindView(R.id.tv_num_7)
    TextView tvNum7;
    @BindView(R.id.tv_num_8)
    TextView tvNum8;
    @BindView(R.id.tv_num_9)
    TextView tvNum9;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, Color.BLACK);
    }

    @OnClick({R.id.iv_back, R.id.iv_setting, R.id.tv_num_1, R.id.tv_num_2, R.id.tv_num_3, R.id.tv_num_4, R.id.tv_num_5, R.id.tv_num_6, R.id.tv_num_7, R.id.tv_num_8, R.id.tv_num_9, R.id.tv_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                break;
            case R.id.iv_setting:
                break;
            case R.id.tv_num_1:
                break;
            case R.id.tv_num_2:
                break;
            case R.id.tv_num_3:
                break;
            case R.id.tv_num_4:
                break;
            case R.id.tv_num_5:
                break;
            case R.id.tv_num_6:
                break;
            case R.id.tv_num_7:
                break;
            case R.id.tv_num_8:
                break;
            case R.id.tv_num_9:
                break;
            case R.id.tv_cancel:
                break;
        }
    }
}
