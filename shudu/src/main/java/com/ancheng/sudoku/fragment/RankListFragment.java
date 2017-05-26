package com.ancheng.sudoku.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ancheng.sudoku.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author: ancheng
 * date: 2017/5/26
 * email: lzjtugjc@163.com
 */

public class RankListFragment extends Fragment {

    @BindView(R.id.lv_list)
    ListView lvList;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.tv_friends)
    TextView tvFriends;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rank_list, container,false);
        ButterKnife.bind(this, view);
        tvAll.setSelected(true);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.tv_all, R.id.tv_friends})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_all:
                tvAll.setSelected(false);
                tvFriends.setSelected(true);
                break;
            case R.id.tv_friends:
                tvAll.setSelected(true);
                tvFriends.setSelected(false);
                break;
        }
    }
}
