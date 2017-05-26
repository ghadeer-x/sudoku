package com.ancheng.sudoku.login.presenter;

import com.ancheng.sudoku.login.I.ILoginActivity;
import com.ancheng.sudoku.model.bean.User;
import com.apkfuns.logutils.LogUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * author: ancheng
 * date: 2017/5/19
 * email: lzjtugjc@163.com
 */

public class LoginPresenter {
    private ILoginActivity mILoginActivity;

    public LoginPresenter(ILoginActivity ILoginActivity) {
        mILoginActivity = ILoginActivity;
    }

    public void login(String name, String password) {
        LogUtils.d("name = %s,password = %s",name,password);
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("phone_number", name);
        query.addWhereEqualTo("password", password);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e != null) {
                    //查询失败
                    mILoginActivity.isLoginSucess(false, e);
                } else {
                    if (list.size() != 0) {
                        LogUtils.d(list);
                        mILoginActivity.isLoginSucess(true, null);
                    } else {
                        mILoginActivity.isLoginSucess(false, null);
                    }
                }
            }
        });
    }
}
