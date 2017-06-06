package com.ancheng.sudoku.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.ancheng.sudoku.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * author: ancheng
 * date: 2017/6/3
 * email: lzjtugjc@163.com
 */

public class ResetGameDialogFragment extends DialogFragment {

    Unbinder unbinder;

    public static interface DialogClickListener {
        void cancel();

        void confirm();
    }

    static DialogClickListener mListener;

    public static ResetGameDialogFragment getInstance(DialogClickListener listener) {
        ResetGameDialogFragment resetGameDialogFragment = new ResetGameDialogFragment();
        mListener = listener;
        return resetGameDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialgo_fragment_reset, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_cancel, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                mListener.cancel();
                dismiss();
                break;
            case R.id.tv_confirm:
                mListener.confirm();
                dismiss();
                break;
        }
    }
}
