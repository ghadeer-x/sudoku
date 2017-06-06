package com.ancheng.sudoku.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ancheng.sudoku.R;
import com.ancheng.sudoku.adapter.RankListAdapter;
import com.ancheng.sudoku.model.bean.Game_Score;
import com.ancheng.sudoku.model.bean.RankInfo;
import com.ancheng.sudoku.model.bean.User;
import com.ancheng.sudoku.utils.ToastUtils;
import com.apkfuns.logutils.LogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

import static cn.smssdk.utils.b.e;

/**
 * author: ancheng
 * date: 2017/5/26
 * email: lzjtugjc@163.com
 */

public class RankListFragment extends Fragment {

    @BindView(R.id.lv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.tv_friends)
    TextView tvFriends;
    private boolean isAllRank = true;
    private List<RankInfo> mRankInfos;
    private RankListAdapter mRankListAdapter;
    private static final String TAG = "RankListFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rank_list, container, false);
        ButterKnife.bind(this, view);
        tvAll.setSelected(true);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRankInfos = new ArrayList<>();
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        mRankListAdapter = new RankListAdapter(getContext(),mRankInfos);
        rvList.setAdapter(mRankListAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateData();
    }

    /**
     * 查询数据库，更新数据
     */
    private void updateData() {
        if (isAllRank) {
            final List<RankInfo> rankInfos = new ArrayList<>();
            BmobQuery<User> bmobQuery = new BmobQuery<>();
            bmobQuery.findObjects(new FindListener<User>() {
                @Override
                public void done(final List<User> list, BmobException e) {
                    if (e == null) {
                        LogUtils.tag(TAG).d("list +========== " + list.size());
                        for (int i = 0; i < list.size(); i++) {
                            final User user = list.get(i);
                            BmobQuery<Game_Score> scoreBmobQuery = new BmobQuery<Game_Score>();
                            scoreBmobQuery.sum(new String[]{"score"});
                           // scoreBmobQuery.addWhereEqualTo("userId", user.getUser_id());
                            final int finalI = i;
                            scoreBmobQuery.findStatistics(Game_Score.class, new QueryListener<JSONArray>() {
                                @Override
                                public void done(JSONArray jsonArray, BmobException e) {
                                    if (e == null) {
                                        if (jsonArray != null) {
                                            try {
                                                JSONObject obj = jsonArray.getJSONObject(0);
                                                int sum = obj.getInt("_sumScore");//_(关键字)+首字母大写的列名
                                                rankInfos.get(finalI).setUser(user);
                                                rankInfos.get(finalI).setScore(sum);
                                                if(finalI == list.size()){
                                                    mRankInfos.clear();
                                                    mRankInfos.addAll(rankInfos);
                                                    mRankListAdapter.notifyDataSetChanged();
                                                }
                                            } catch (JSONException e1) {
                                                e1.printStackTrace();
                                            }
                                        }else{
                                            ToastUtils.showLong("暂时无数据");
                                        }
                                    }else{
                                        LogUtils.tag(TAG).d(e);
                                    }

                                }
                            });
                        }
                    } else {
                        LogUtils.tag(TAG).d("查询失败");
                    }
                }
            });
        } else {
            LogUtils.tag(TAG).d("查询失败： " + e);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.tv_all, R.id.tv_friends})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_all:
                tvAll.setSelected(true);
                tvFriends.setSelected(false);
                isAllRank = true;
                updateData();
                break;
            case R.id.tv_friends:
                tvAll.setSelected(false);
                tvFriends.setSelected(true);
                isAllRank = false;
                updateData();
                break;
        }
    }
}
