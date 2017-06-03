package com.ancheng.sudoku.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ancheng.sudoku.R;
import com.ancheng.sudoku.application.MyApplication;
import com.ancheng.sudoku.constant.InitConstants;
import com.ancheng.sudoku.fragment.FriendsFragment;
import com.ancheng.sudoku.fragment.HomeFragment;
import com.ancheng.sudoku.fragment.MineFragment;
import com.ancheng.sudoku.fragment.RankListFragment;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fl_content)
    FrameLayout flContent;
    List<Fragment> fragments;
    @BindView(R.id.ll_home)
    LinearLayout llHome;
    @BindView(R.id.ll_rank_list)
    LinearLayout llRankList;
    @BindView(R.id.ll_friends)
    LinearLayout llFriends;
    @BindView(R.id.ll_mine)
    LinearLayout llMine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, Color.BLACK);
        initData();
        switchFragment(0);
    }

    private void initData() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new RankListFragment());
        fragments.add(new FriendsFragment());
        fragments.add(new MineFragment());

        MyApplication.mSpUtils.put(InitConstants.LAST_LOGIN_TIME, System.currentTimeMillis());
    }
    /**
     * 切换显示的Fragment
     *
     * @param index
     */
    private void switchFragment(int index) {
        switchStateTab(index);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            //显示Fragment
            if (index == i) {
                if (fragment.isAdded()) {
                    transaction.show(fragment);
                } else {
                    transaction.add(R.id.fl_content, fragment);
                }
            } else {
                //隐藏Fragment
                if (fragment.isAdded()) {
                    transaction.hide(fragment);
                }
            }
        }
        transaction.commitAllowingStateLoss();
    }

    @OnClick({R.id.ll_home, R.id.ll_rank_list, R.id.ll_friends, R.id.ll_mine})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_home:
                //ToastUtils.showShort("home");
                switchFragment(0);
                break;
            case R.id.ll_rank_list:
                //ToastUtils.showShort("list");
                switchFragment(1);
                break;
            case R.id.ll_friends:
                //ToastUtils.showShort("friends");
                switchFragment(2);
                break;
            case R.id.ll_mine:
                //ToastUtils.showShort("mine");
                switchFragment(3);
                break;
        }
    }

    public void switchStateTab(int i) {
        switch (i) {
            case 0:
                stateSet(true, false, false, false);
                break;
            case 1:
                stateSet(false, true, false, false);
                break;
            case 2:
                stateSet(false, false, true, false);
                break;
            case 3:
                stateSet(false, false, false, true);
                break;
        }
    }

    public void stateSet(boolean home, boolean rankList, boolean friends, boolean mine) {
        llHome.setSelected(home);
        llRankList.setSelected(rankList);
        llFriends.setSelected(friends);
        llMine.setSelected(mine);
    }
}
