package com.ancheng.sudoku.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.ancheng.sudoku.R;
import com.ancheng.sudoku.adapter.AddFriendAdapter;
import com.ancheng.sudoku.model.UserManager;
import com.ancheng.sudoku.model.bean.User;
import com.apkfuns.logutils.LogUtils;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class AddFriendActivity extends AppCompatActivity {

    @BindView(R.id.rv_friend_list)
    RecyclerView rvFriendList;
    @BindView(R.id.et_search_text)
    EditText etSearchText;
    List<User> mUserList;
    private AddFriendAdapter mAddFriendAdapter;
    private static final String TAG = "AddFriendActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, Color.BLACK);
        mUserList = new ArrayList<>();
        rvFriendList.setLayoutManager(new LinearLayoutManager(this));
        mAddFriendAdapter = new AddFriendAdapter(this,mUserList);
        rvFriendList.setAdapter(mAddFriendAdapter);

    }

    @OnClick({R.id.iv_back, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                //查询数据库
                final String number = etSearchText.getText().toString().trim();
                if(TextUtils.isEmpty(number)){
                    return;
                }
                LogUtils.tag(TAG).d("搜索按钮别点击了" + number);
                BmobQuery<User> query = new BmobQuery<>();
//                query.addWhereContains("phone_number", number);
                query.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {

                        if(e == null){
                            if(list != null && list.size()!=0){
//                                LogUtils.tag(TAG).d(list);
                                mUserList.clear();
                                for (User user : list) {
                                    if((!user.getUser_id().equals(UserManager.getInstance().getUser_id()))&& user.getPhone_number().contains(number)){
                                        mUserList.add(user);
                                    }
                                }
                                mAddFriendAdapter.notifyDataSetChanged();
                            }
                        }else{
                            LogUtils.tag(TAG).d(e);
                        }
                    }
                });
        }
    }
}
