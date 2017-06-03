package com.ancheng.sudoku.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ancheng.sudoku.R;
import com.ancheng.sudoku.application.MyApplication;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.romario.opensudoku.bean.GameInfo;
import cz.romario.opensudoku.gui.SudokuBoardView;

/**
 * author: ancheng
 * date: 2017/5/29
 * email: lzjtugjc@163.com
 */

public class GameMenuListAdapter extends BaseAdapter {
    private GameInfo mGameInfo;

    public GameMenuListAdapter(GameInfo gameInfo) {
        mGameInfo = gameInfo;
    }

    @Override
    public int getCount() {
        if (mGameInfo != null && mGameInfo.getGames().size() != 0) {
            return mGameInfo.getGames().size();
        } else {
            return 0;
        }
    }

    @Override
    public GameInfo.GamesBean getItem(int position) {
        return mGameInfo.getGames().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            View view = RelativeLayout.inflate(convertView.getContext(), R.layout.activity_menu_list_item, parent);
            viewHolder = new ViewHolder(view);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String gameString = getItem(position).getGame();
        String level = mGameInfo.getLevel();
        long lastPlayTime = 0L;
        long palyTime = 0L;
        int state = 0;
        switch (level) {
            case "easy":
                state = MyApplication.mSpUtils.getInt("easy" + "state" + position, 0);
                lastPlayTime = MyApplication.mSpUtils.getLong("easy" + "lastPlayTime" + position, 0);
                palyTime = MyApplication.mSpUtils.getLong("easy" + "palyTime" + position, 0);
                break;
            case "medium":
                state = MyApplication.mSpUtils.getInt("medium" + "state" + position, 0);
                lastPlayTime = MyApplication.mSpUtils.getLong("medium" + "lastPlayTime" + position, 0);
                palyTime = MyApplication.mSpUtils.getLong("medium" + "palyTime" + position, 0);
                break;
            case "hard":
                state = MyApplication.mSpUtils.getInt("hard" + "state" + position, 0);
                lastPlayTime = MyApplication.mSpUtils.getLong("hard" + "lastPlayTime" + position, 0);
                palyTime = MyApplication.mSpUtils.getLong("hard" + "palyTime" + position, 0);
                break;
        }
        if (state == 0) {
            //未开始游戏
            viewHolder.state.setText("");
        } else if (state == 1) {
            //游戏中
            viewHolder.state.setText("游戏中");
        } else if (state == 2) {
            //游戏结束
            viewHolder.state.setText("游戏结束");
        }
        if (lastPlayTime == 0) {
            viewHolder.lastPlayed.setText("");
            viewHolder.time.setText("");
        } else {
            viewHolder.lastPlayed.setText(lastPlayTime + "");
            viewHolder.time.setText(palyTime + "");
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.sudoku_board)
        SudokuBoardView sudokuBoard;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.state)
        TextView state;
        @BindView(R.id.last_played)
        TextView lastPlayed;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
