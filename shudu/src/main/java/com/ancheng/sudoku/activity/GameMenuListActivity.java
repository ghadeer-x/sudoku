package com.ancheng.sudoku.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.ancheng.sudoku.R;
import com.apkfuns.logutils.LogUtils;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.romario.opensudoku.bean.GameInfo;

public class GameMenuListActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.lv_game_list)
    ListView lvGameList;
    private int mLevel;
    private GameInfo mGameInfo;
    private Gson mGson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu_list);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, Color.BLACK);
        mGameInfo = new GameInfo();
        parseIntent();
        initView();
        LogUtils.d(mGameInfo);
    }

    private void initView() {
        switch (mLevel) {
            case 1:
                tvTitle.setText("初级");
                mGameInfo = mGson.fromJson(new InputStreamReader(getStrFromAssets("easy.json")), GameInfo.class);
//                Intent i = new Intent(this, SudokuListActivity.class);
//                i.putExtra(SudokuListActivity.EXTRA_FOLDER_ID, 0);
//                startActivity(i);
                break;
            case 2:
                tvTitle.setText("中级");
                mGameInfo = mGson.fromJson(new InputStreamReader(getStrFromAssets("medium.json")),GameInfo.class);
                break;
            case 3:
                tvTitle.setText("高级");
                mGameInfo = mGson.fromJson(new InputStreamReader(getStrFromAssets("hard.json")),GameInfo.class);
                break;
        }
    }

    private void parseIntent() {
        Intent intent = getIntent();
        mLevel = intent.getIntExtra("level", 1);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
    /**
     * @description 通过assets文件获取json数据，这里写的十分简单，没做循环判断。
     *
     * @return Json数据（String）
     */
    private InputStream getStrFromAssets(String name) {
        InputStream inputStream = null;
        try {
             inputStream = getAssets().open(name);
             return inputStream;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
