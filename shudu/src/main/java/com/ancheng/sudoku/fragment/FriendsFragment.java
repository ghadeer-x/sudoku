package com.ancheng.sudoku.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ancheng.sudoku.R;
import com.ancheng.sudoku.adapter.FriendListAdapter;
import com.ancheng.sudoku.model.UserManager;
import com.ancheng.sudoku.model.bean.Friend_Relation;
import com.ancheng.sudoku.model.bean.User;
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

    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }

    private void loadData() {
        List<Friend_Relation> friendRelationLists = new ArrayList<>();
        final String userId = UserManager.getInstance().getUser_id();
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
                    mUserList.clear();
                    //查询成功
                    for (int i = 0; i < list.size(); i++) {
                        Friend_Relation friend_relation = list.get(i);
                        String userId1 = friend_relation.getUserId();
                        String friendId = friend_relation.getFriendId();
                        String queryId = null;
                        if (userId.equals(userId1)) {
                            queryId = friendId;
                        } else {
                            queryId = userId;
                        }
                        BmobQuery<User> query = new BmobQuery<>();
                        query.addWhereEqualTo("userId", queryId);
                        query.findObjects(new FindListener<User>() {
                            @Override
                            public void done(List<User> list, BmobException e) {
                                if (e == null) {
                                    //更新成功
                                    if (list.size() != 0) {
                                        mUserList.add(list.get(0));
                                        LogUtils.tag(TAG).d(list);
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
