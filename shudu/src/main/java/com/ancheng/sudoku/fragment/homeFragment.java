package com.ancheng.sudoku.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ancheng.sudoku.R;
import com.ancheng.sudoku.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author: ancheng
 * date: 2017/5/22
 * email: lzjtugjc@163.com
 */

public class homeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.tv_primary, R.id.tv_middle, R.id.tv_high})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_primary:
                ToastUtils.show(getContext(),"初级");
                break;
            case R.id.tv_middle:
                ToastUtils.show(getContext(),"初级");
                break;
            case R.id.tv_high:
                ToastUtils.show(getContext(),"初级");
                break;
        }
    }
}
