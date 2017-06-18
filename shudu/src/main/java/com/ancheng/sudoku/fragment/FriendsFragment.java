package com.ancheng.sudoku.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ancheng.sudoku.R;
import com.ancheng.sudoku.adapter.FriendListAdapter;
import com.ancheng.sudoku.model.UserManager;
import com.ancheng.sudoku.model.bean.Friend_Relation;
import com.ancheng.sudoku.model.bean.User;
import com.ancheng.sudoku.utils.IntentTools;
import com.apkfuns.logutils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * author: ancheng
 * date: 2017/5/26
 * email: lzjtugjc@163.com
 */

public class FriendsFragment extends Fragment {

    @BindView(R.id.lv_friend_list)
    RecyclerView lvFriendList;
    @BindView(R.id.tv_add_friend)
    TextView tvAddFriend;
    Unbinder unbinder;
    private List<User> mUserList;
    private Context mContext;
    private FriendListAdapter mFriendListAdapter;
    private static final String TAG = "FriendsFragment";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        unbinder = ButterKnife.bind(this, view);
        mUserList = new ArrayList<>();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getContext();
        lvFriendList.setLayoutManager(new LinearLayoutManager(mContext));
        mFriendListAdapter = new FriendListAdapter(mUserList, getContext());
        lvFriendList.setAdapter(mFriendListAdapter);
        initListener();
    }

    private void initListener() {
        tvAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入添加好友界面
                startActivity(IntentTools.getAddFriendActivity(getContext()));
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }

    private void loadData() {
//
//        LogUtils.tag(TAG).d("查询数据");
//        final String userId = UserManager.getInstance().getUser_id();
//        BmobQuery<User> bmobQuery = new BmobQuery<>();
//        Friend_Relation friend_relation = new Friend_Relation();
//        friend_relation.setUserId(userId);
//        bmobQuery.addWhereRelatedTo("userId", new BmobPointer(friend_relation));
//        bmobQuery.findObjects(new FindListener<User>() {
//            @Override
//            public void done(List<User> list, BmobException e) {
//                if (e == null) {
//                    mUserList.clear();
//                    mUserList.addAll(list);
//                    LogUtils.tag(TAG).d(list);
//                    mFriendListAdapter.notifyDataSetChanged();
//                } else {
//                    ToastUtils.showLong("请检查网络！");
//                    LogUtils.tag(TAG).d(e);
//                }
//            }
//        });

        final String userId = UserManager.getInstance().getUser_id();
        List<Friend_Relation> friendRelationLists = new ArrayList<>();
        BmobQuery<Friend_Relation> eq1 = new BmobQuery<>();
        eq1.addWhereEqualTo("userId", userId);
        BmobQuery<Friend_Relation> eq2 = new BmobQuery<>();
        eq2.addWhereEqualTo("friendId", userId);
        final List<BmobQuery<Friend_Relation>> queries = new ArrayList<>();
        queries.add(eq1);
        queries.add(eq2);
        BmobQuery<Friend_Relation> mainQuery = new BmobQuery<>();
        mainQuery.or(queries);
        mainQuery.findObjects(new FindListener<Friend_Relation>() {
            @Override
            public void done(List<Friend_Relation> list, BmobException e) {
                if (e == null) {
                    LogUtils.tag(TAG).d("查询成功：" + list.toString());
                    mUserList.clear();
                    //查询成功
                    for (int i = 0; i < list.size(); i++) {
                        Friend_Relation friend_relation = list.get(i);
                        String userId1 = friend_relation.getUserId();
                        String friendId = friend_relation.getFriendId();
                        String queryId = null;
//                        LogUtils.tag(TAG).d("userID : " + userId);
//                        LogUtils.tag(TAG).d("userId1 : " + userId1);
//                        LogUtils.tag(TAG).d("friendId : " + friendId);
                        if (TextUtils.equals(userId,userId1)) {
                            queryId = friendId;
                        } else {
                            queryId = userId1;
                        }
//                        LogUtils.tag(TAG).d("queryId : " + queryId);
                        BmobQuery<User> query = new BmobQuery<>();
                        query.addWhereEqualTo("user_id", queryId);
                        query.findObjects(new FindListener<User>() {
                            @Override
                            public void done(List<User> list, BmobException e) {
                                if (e == null) {
                                    //更新成功
                                    if (list.size() != 0) {
                                        mUserList.add(list.get(0));
                                        LogUtils.tag(TAG).d("好友列表" + list.get(0).getPhone_number());
                                        mFriendListAdapter.notifyDataSetChanged();
                                    }
                                } else {
                                    LogUtils.tag(TAG).d(e);
                                }
                            }
                        });
                    }
                } else {
                    LogUtils.tag(TAG).d("查询失败:   " + e);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
