package com.ancheng.sudoku.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ancheng.sudoku.R;
import com.ancheng.sudoku.model.bean.Game_Score;
import com.apkfuns.logutils.LogUtils;

import java.util.List;

/**
 * author: ancheng
 * date: 2017/6/2
 * email: lzjtugjc@163.com
 */

public class MineScoreAdapter extends RecyclerView.Adapter<MineScoreAdapter.MineScoreViewHolder> {

    private List<Game_Score> mGame_scores;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private static final String TAG = "MineScoreAdapter";

    public MineScoreAdapter(Context context, List<Game_Score> game_scores) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mGame_scores = game_scores;
    }

    @Override
    public MineScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MineScoreViewHolder(mLayoutInflater.inflate(R.layout.item_mine_score, parent, false));
    }

    @Override
    public void onBindViewHolder(MineScoreViewHolder holder, int position) {
        holder.bindData(mGame_scores.get(position));
    }

    @Override
    public int getItemCount() {
        if (mGame_scores != null && mGame_scores.size() != 0) {
            LogUtils.tag(TAG).d(mGame_scores.size());
            return mGame_scores.size();
        } else {
            return 0;
        }
    }

    public class MineScoreViewHolder extends RecyclerView.ViewHolder {

        TextView tvLevel;
        TextView tvScore;
        TextView tvDuration;

        public MineScoreViewHolder(View itemView) {
            super(itemView);
            tvLevel = (TextView) itemView.findViewById(R.id.tv_level);
            tvScore = (TextView) itemView.findViewById(R.id.tv_score);
            tvDuration = (TextView) itemView.findViewById(R.id.tv_duration);
        }
        public void bindData(Game_Score game_score){
            tvLevel.setText(game_score.getLevel());
            tvScore.setText(game_score.getScore()+ "");
            tvDuration.setText(game_score.getTime().longValue()/1000/60 + "分钟");
        }
    }
}
