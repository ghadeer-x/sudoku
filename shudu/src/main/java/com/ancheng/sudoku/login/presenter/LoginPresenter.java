package com.ancheng.sudoku.login.presenter;

import com.ancheng.sudoku.login.I.ILoginActivity;
import com.ancheng.sudoku.model.UserManager;
import com.ancheng.sudoku.model.bean.User;
import com.ancheng.sudoku.utils.RegexUtils;
import com.ancheng.sudoku.utils.ToastUtils;
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
    private static final String TAG = "LoginPresenter";

    public LoginPresenter(ILoginActivity ILoginActivity) {
        mILoginActivity = ILoginActivity;
    }

    public void login(String name, String password) {
        String accountString = "phone_number";
        //验证账号是否是邮箱
        if (RegexUtils.isEmail(name)) {
            accountString = "email";
        } else if (RegexUtils.isMobileExact(name)) {
            accountString = "phone_number";
        } else {
            ToastUtils.showLong("账户名格式错误，请重新输入！");
            return;
        }
        LogUtils.tag(TAG).d("name = %s,password = %s", name, password);
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo(accountString, name);
        query.addWhereEqualTo("password", password);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e != null) {
                    //查询失败
                    mILoginActivity.isLoginSucess(false, e);
                } else {
                    if (list.size() != 0) {
                        LogUtils.tag(TAG).d(list);
                        UserManager instance = UserManager.getInstance();
                        instance.setUser(list.get(0));
                        mILoginActivity.isLoginSucess(true, null);
                    } else {
                        mILoginActivity.isLoginSucess(false, null);
                    }
                }
            }
        });
    }
}
