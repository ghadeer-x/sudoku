package com.ancheng.sudoku.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ancheng.sudoku.R;
import com.ancheng.sudoku.adapter.MineScoreAdapter;
import com.ancheng.sudoku.model.UserManager;
import com.ancheng.sudoku.model.bean.Game_Score;
import com.apkfuns.logutils.LogUtils;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MineScoreActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private MineScoreAdapter mMineScoreAdapter;
    private List<Game_Score> mGame_scores;
    private static final String TAG = "MineScoreActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_score);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, Color.BLACK);

        mGame_scores = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMineScoreAdapter = new MineScoreAdapter(this,mGame_scores);
        recyclerView.setAdapter(mMineScoreAdapter);
        loadData();
    }

    private void loadData() {
        String userId = UserManager.getInstance().getUser_id();
        BmobQuery<Game_Score> query = new BmobQuery<>();
        query.addWhereEqualTo("userId", userId);
        query.findObjects(new FindListener<Game_Score>() {
            @Override
            public void done(List<Game_Score> list, BmobException e) {
                if (e == null) {
                    //更新成功
                    mGame_scores.clear();
                    mGame_scores.addAll(list);
                    LogUtils.tag(TAG).d(list);
                    mMineScoreAdapter.notifyDataSetChanged();
                } else {
                    LogUtils.tag(TAG).d(e);
                }
            }
        });
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
