package com.ancheng.sudoku.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ancheng.sudoku.R;
import com.ancheng.sudoku.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fl_content)
    FrameLayout flContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.ll_home, R.id.ll_rank_list, R.id.ll_friends, R.id.ll_mine})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_home:
                ToastUtils.show(this,"home");
                break;
            case R.id.ll_rank_list:
                Toast.makeText(this,"list",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_friends:
                Toast.makeText(this,"friends",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_mine:
                Toast.makeText(this,"mine",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
