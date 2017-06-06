package com.ancheng.sudoku.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ancheng.sudoku.R;
import com.ancheng.sudoku.fragment.ResetGameDialogFragment;
import com.ancheng.sudoku.utils.IntentTools;
import com.ancheng.sudoku.utils.TimeUtils;
import com.apkfuns.logutils.LogUtils;

import java.util.List;

import cz.romario.opensudoku.db.SudokuDatabase;
import cz.romario.opensudoku.game.SudokuGame;
import cz.romario.opensudoku.gui.GameTimeFormat;
import cz.romario.opensudoku.gui.SudokuBoardView;

/**
 * author: ancheng
 * date: 2017/6/2
 * email: lzjtugjc@163.com
 */

public class GameMenuListAdapter extends RecyclerView.Adapter<GameMenuListAdapter.GameMenuListViewHolder> {

    private List<SudokuGame> mSudokuGames;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private FragmentManager mFragmentManager;
    private SudokuDatabase mSudokuDatabase;
    private static final String TAG = "GameMenuListAdapter";
    private long mGameLevel;

    public GameMenuListAdapter(List<SudokuGame> sudokuGames, Context context, FragmentManager supportFragmentManager, SudokuDatabase sudokuDatabase, long gameLevel) {
        mSudokuGames = sudokuGames;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mFragmentManager = supportFragmentManager;
        mSudokuDatabase = sudokuDatabase;
        mGameLevel = gameLevel;
    }

    @Override
    public GameMenuListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GameMenuListViewHolder(mLayoutInflater.inflate(R.layout.item_game_list, parent, false));
    }

    @Override
    public void onBindViewHolder(GameMenuListViewHolder holder, int position) {
        holder.bindData(mSudokuGames.get(position), position);
    }

    @Override
    public int getItemCount() {
        if (mSudokuGames != null && mSudokuGames.size() != 0) {
            return mSudokuGames.size();
        } else {
            return 0;
        }
    }

    public class GameMenuListViewHolder extends RecyclerView.ViewHolder {

        SudokuBoardView sudokuBoard;
        TextView tvState;
        TextView tvTime;
        TextView tvLastPlayed;
        TextView tvCreated;
        TextView tvNote;
        View view;

        public GameMenuListViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
            view = itemView;
        }

        private void initView(View itemView) {
            sudokuBoard = (SudokuBoardView) itemView.findViewById(R.id.sudoku_board);
            tvState = (TextView) itemView.findViewById(R.id.state);
            tvTime = (TextView) itemView.findViewById(R.id.time);
            tvLastPlayed = (TextView) itemView.findViewById(R.id.last_played);
            tvCreated = (TextView) itemView.findViewById(R.id.created);
            tvNote = (TextView) itemView.findViewById(R.id.note);
        }

        public void bindData(final SudokuGame sudokuGame, final int position) {
            //设置游戏界面
            sudokuBoard.setCells(sudokuGame.getCells());
            sudokuBoard.setReadOnly(true);
            sudokuBoard.setFocusable(false);
            //设置游戏状态
            int state = sudokuGame.getState();
            long lastPlayed = sudokuGame.getLastPlayed();
            String timeString = TimeUtils.millis2String(lastPlayed);
            long time = sudokuGame.getTime();

            LogUtils.tag(TAG).d("state : %d ,lastPlayed : %d ，time : %d", state, lastPlayed, time);
            if (state == SudokuGame.GAME_STATE_COMPLETED) {
                tvState.setVisibility(View.VISIBLE);
                tvLastPlayed.setVisibility(View.VISIBLE);
                tvTime.setVisibility(View.VISIBLE);

                tvState.setText("已解决");
                tvLastPlayed.setText("上次游戏时间：" + timeString);
                tvTime.setText(new GameTimeFormat().format(time));
                tvState.setTextColor(Color.BLACK);
                tvLastPlayed.setTextColor(Color.BLACK);
                tvTime.setTextColor(Color.BLACK);
            } else if (state == SudokuGame.GAME_STATE_NOT_STARTED) {
                tvState.setText("");
                tvState.setVisibility(View.INVISIBLE);
                tvLastPlayed.setVisibility(View.INVISIBLE);
                tvTime.setVisibility(View.INVISIBLE);

            } else if (state == SudokuGame.GAME_STATE_PLAYING) {

                tvState.setVisibility(View.VISIBLE);
                tvLastPlayed.setVisibility(View.VISIBLE);
                tvTime.setVisibility(View.VISIBLE);

                tvState.setText("游戏中");
                tvLastPlayed.setText("上次游戏时间：" + timeString);
                tvTime.setText(new GameTimeFormat().format(time));

                tvState.setTextColor(mContext.getResources().getColor(R.color.mainColor));
                tvLastPlayed.setTextColor(mContext.getResources().getColor(R.color.mainColor));
                tvTime.setTextColor(mContext.getResources().getColor(R.color.mainColor));
            }
            //设置游戏时间

//            long created = sudokuGame.getCreated();
//            tvCreated.setText("创建游戏日期：" + created);
            tvCreated.setVisibility(View.INVISIBLE);
            tvNote.setVisibility(View.INVISIBLE);
//            String note = sudokuGame.getNote();
//            tvNote.setText(note);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(IntentTools.getGameViewActivityIntent(mContext, mGameLevel, sudokuGame.getId(), position));
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
//                    ToastUtils.showLong("重置游戏");
                    ResetGameDialogFragment.getInstance(new ResetGameDialogFragment.DialogClickListener() {
                        @Override
                        public void cancel() {

                        }

                        @Override
                        public void confirm() {
                            if (mIResetGame != null) {
                                mIResetGame.onResetGameListener(sudokuGame.getId());
                            }
                        }
                    }).show(mFragmentManager, "ResetGameDialogFragment");
                    return false;
                }
            });
        }
    }

    private IResetGame mIResetGame;

    public void setOnResetGameListener(IResetGame iResetGame) {
        mIResetGame = iResetGame;
    }

    public interface IResetGame {
        void onResetGameListener(long gameID);
    }
}
