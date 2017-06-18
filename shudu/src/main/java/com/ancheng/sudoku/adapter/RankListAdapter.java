package com.ancheng.sudoku.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ancheng.sudoku.R;
import com.ancheng.sudoku.model.bean.RankInfo;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * author: ancheng
 * date: 2017/6/5
 * email: lzjtugjc@163.com
 */

public class RankListAdapter extends RecyclerView.Adapter<RankListAdapter.RankListViewHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<RankInfo> mRankInfos;

    public RankListAdapter(Context context, List<RankInfo> rankInfos) {
        mContext = context;
        mRankInfos = rankInfos;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RankListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_rank_list, parent, false);
        return new RankListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RankListViewHolder holder, int position) {
        holder.bingData(mRankInfos, position);
    }

    @Override
    public int getItemCount() {
        if (mRankInfos != null && mRankInfos.size() != 0) {
            return mRankInfos.size();
        } else {
            return 0;
        }
    }

    public class RankListViewHolder extends RecyclerView.ViewHolder {
        CircleImageView civAvatar;
        TextView tvRanking;
        TextView tvScore;
        TextView tvNickName;
        TextView tvSignature;

        public RankListViewHolder(View itemView) {
            super(itemView);
            civAvatar = (CircleImageView) itemView.findViewById(R.id.civ_avatar);
            tvRanking= (TextView) itemView.findViewById(R.id.tv_ranking);
            tvScore = (TextView) itemView.findViewById(R.id.tv_score);
            tvNickName = (TextView) itemView.findViewById(R.id.tv_nick_name);
            tvSignature = (TextView) itemView.findViewById(R.id.tv_signature);
        }

        public void bingData(List<RankInfo> rankInfos, int position) {
            RankInfo rankInfo = rankInfos.get(position);

            String nickName = rankInfo.getUser().getNickName();
            String signature = rankInfo.getUser().getSignature();
            int score = rankInfo.getScore();

            civAvatar.setImageResource(R.drawable.avatar1);
//            if (rankInfo.getUser().getAvatar() == null) {
//
//            } else {
//                Glide.with(mContext)
//                        .load(rankInfo.getUser().getAvatar().getFileUrl())
//                        .error(R.drawable.avatar1)
//                        .into(civAvatar);
//            }
            if (TextUtils.isEmpty(nickName)) {
                tvNickName.setText("数独大师");
            } else {
                tvNickName.setText(nickName);
            }

            if (TextUtils.isEmpty(signature)) {
                tvSignature.setVisibility(View.INVISIBLE);
                tvSignature.setText("");
            } else {
                tvSignature.setVisibility(View.VISIBLE);
                tvSignature.setText(signature);
            }

            if (score == 0) {
                tvScore.setText("0");
            } else {
                tvScore.setText(score + "");
            }
            tvRanking.setText(position + 1 + "");
        }
    }
}
