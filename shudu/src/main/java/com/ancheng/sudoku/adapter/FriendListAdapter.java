package com.ancheng.sudoku.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ancheng.sudoku.R;
import com.ancheng.sudoku.model.bean.User;
import com.ancheng.sudoku.utils.ToastUtils;
import com.apkfuns.logutils.LogUtils;
import com.bumptech.glide.Glide;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * author: ancheng
 * date: 2017/6/2
 * email: lzjtugjc@163.com
 */

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.FriendListViewHolder> {
    private List<User> mUserList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private static final String TAG = "FriendListAdapter";

    public FriendListAdapter(List<User> userList, Context context) {
        mUserList = userList;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public FriendListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FriendListViewHolder(mLayoutInflater.inflate(R.layout.item_friend_list, parent, false));
    }

    @Override
    public void onBindViewHolder(FriendListViewHolder holder, int position) {
        holder.bindData(mUserList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mUserList != null && mUserList.size() != 0) {
            return mUserList.size();
        } else {
            return 0;
        }
    }

    public class FriendListViewHolder extends RecyclerView.ViewHolder {
        CircleImageView civAvatar;
        TextView tvNickName;

        public FriendListViewHolder(View itemView) {
            super(itemView);
            civAvatar = (CircleImageView) itemView.findViewById(R.id.civ_avatar);
            tvNickName = (TextView) itemView.findViewById(R.id.tv_nick_name);

           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   //TODO   进入聊天界面
                   ToastUtils.showLong("聊天");
               }
           });
        }



        public void bindData(User u) {
            BmobFile avatar = u.getAvatar();
            String nickName = u.getNickName();

            if (avatar != null){
                LogUtils.tag(TAG).d("imageUrl : " + avatar.getUrl());
            }

            Glide.with(mContext)
                    .load(avatar.getUrl())
                    .error(R.mipmap.default_image)
                    .into(civAvatar);

            if (TextUtils.isEmpty(nickName)) {
                tvNickName.setText("数独大师");
            } else {
                tvNickName.setText(nickName);
            }
        }
    }
}
