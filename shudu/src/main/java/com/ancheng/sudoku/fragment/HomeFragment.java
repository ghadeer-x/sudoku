package com.ancheng.sudoku.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ancheng.sudoku.R;
import com.ancheng.sudoku.utils.IntentTools;
import com.apkfuns.logutils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.romario.opensudoku.db.FolderColumns;
import cz.romario.opensudoku.db.SudokuDatabase;
import cz.romario.opensudoku.game.FolderInfo;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * author: ancheng
 * date: 2017/5/22
 * email: lzjtugjc@163.com
 */

public class HomeFragment extends Fragment {
    private SudokuDatabase mDatabase;
    private Cursor mCursor;
    private static final String TAG = "HomeFragment";
    private List<FolderInfo> mFolderInfos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDatabase = new SudokuDatabase(getApplicationContext());
        mCursor = mDatabase.getFolderList();

        if (mCursor.getCount() > 0) {
            mFolderInfos = new ArrayList<>();
            while (mCursor.moveToNext()) {
                FolderInfo folderInfo = new FolderInfo();
                long id = mCursor.getLong(mCursor.getColumnIndex(FolderColumns._ID));
                String name = mCursor.getString(mCursor.getColumnIndex(FolderColumns.NAME));
                folderInfo.id = id;
                folderInfo.name = name;
                LogUtils.tag(TAG).d(folderInfo);
                mFolderInfos.add(folderInfo);
            }
        }
        mCursor.close();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDatabase.close();
    }

    @OnClick({R.id.tv_primary, R.id.tv_middle, R.id.tv_high})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_primary:
                //ToastUtils.showShort("初级");
//                startActivity(IntentTools.getGameMenuListActivityIntent(getContext(), 1));
//                Intent i = new Intent(getContext(), SudokuListActivity.class);
//                i.putExtra(SudokuListActivity.EXTRA_FOLDER_ID, mFolderInfos.get(0).id);
//                startActivity(i);
                startActivity(IntentTools.getGameMenuListActivityIntent(getContext(),mFolderInfos.get(0).id));
                break;
            case R.id.tv_middle:
                //ToastUtils.showShort("中级");
//                startActivity(IntentTools.getGameMenuListActivityIntent(getContext(), 2));
//                Intent i1 = new Intent(getContext(), SudokuListActivity.class);
//                i1.putExtra(SudokuListActivity.EXTRA_FOLDER_ID, mFolderInfos.get(1).id);
//                startActivity(i1);
                startActivity(IntentTools.getGameMenuListActivityIntent(getContext(),mFolderInfos.get(1).id));
                break;
            case R.id.tv_high:
                //ToastUtils.showShort("高级");
//                 startActivity(IntentTools.getGameMenuListActivityIntent(getContext(), 3));
//                Intent i2 = new Intent(getContext(), SudokuListActivity.class);
//                i2.putExtra(SudokuListActivity.EXTRA_FOLDER_ID, mFolderInfos.get(2).id);
//                startActivity(i2);
                startActivity(IntentTools.getGameMenuListActivityIntent(getContext(),mFolderInfos.get(2).id));
                break;
        }
    }
}
