package com.ancheng.sudoku.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ancheng.sudoku.R;
import com.ancheng.sudoku.model.UserManager;
import com.ancheng.sudoku.model.bean.Friend_Relation;
import com.ancheng.sudoku.model.bean.User;
import com.ancheng.sudoku.utils.ToastUtils;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * author: ancheng
 * date: 2017/6/5
 * email: lzjtugjc@163.com
 */

public class AddFriendAdapter extends RecyclerView.Adapter<AddFriendAdapter.RankListViewHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<User> mUser;

    public AddFriendAdapter(Context context, List<User> user) {
        mContext = context;
        mUser = user;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RankListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_add_friend_list, parent, false);
        return new RankListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RankListViewHolder holder, int position) {
        holder.bingData(mUser, position);
    }

    @Override
    public int getItemCount() {
        if (mUser != null && mUser.size() != 0) {
            return mUser.size();
        } else {
            return 0;
        }
    }

    public class RankListViewHolder extends RecyclerView.ViewHolder {
        CircleImageView civAvatar;
        TextView tvNickName;
        TextView tvSignature;
        TextView tvAdd;

        public RankListViewHolder(View itemView) {
            super(itemView);
            civAvatar = (CircleImageView) itemView.findViewById(R.id.civ_avatar);
            tvAdd = (TextView) itemView.findViewById(R.id.tv_add);
            tvNickName = (TextView) itemView.findViewById(R.id.tv_nick_name);
            tvSignature = (TextView) itemView.findViewById(R.id.tv_signature);
        }

        public void bingData(List<User> users, int position) {
            final User user = users.get(position);
            String nickName = user.getNickName();
            String signature = user.getSignature();
            tvAdd.setText("添加好友");
            tvNickName.setText(nickName);
            if (TextUtils.isEmpty(nickName)) {
                tvNickName.setText("数独大师");
            } else {
                tvNickName.setText(nickName);
            }
            civAvatar.setImageResource(R.drawable.avatar1);

            if (TextUtils.isEmpty(signature)) {
                tvSignature.setVisibility(View.INVISIBLE);
                tvSignature.setText("这个人很懒，什么也没有留下！");
            } else {
                tvSignature.setVisibility(View.VISIBLE);
                tvSignature.setText(signature);
            }

            tvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //添加数据到数据库中
                    Friend_Relation friend_relation = new Friend_Relation();
                    friend_relation.setUserId(UserManager.getInstance().getUser_id());
                    friend_relation.setFriendId(user.getUser_id());

                    friend_relation.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if (e == null) {
                                //注册成功
                                ToastUtils.showLongSafe("添加成功");
                                tvAdd.setText("已是好友");
                                tvAdd.setEnabled(false);
                            } else {
                                //注册失败
                                ToastUtils.showLongSafe("添加失败，请检查网络！");
                            }
                        }
                    });
                }
            });

        }
    }
}
