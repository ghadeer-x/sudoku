package com.ancheng.sudoku.register.presenter;

import com.ancheng.sudoku.model.bean.User;
import com.ancheng.sudoku.register.I.IRegisterView;
import com.ancheng.sudoku.utils.MD5Utils;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * author: ancheng
 * date: 2017/5/24
 * email: lzjtugjc@163.com
 */

public class RegisterPresenter {

    private IRegisterView mIRegisterView;

    public RegisterPresenter(IRegisterView IRegisterView) {
        mIRegisterView = IRegisterView;
    }

    public void register(String number, String password) {
        String uid = MD5Utils.strToMD5(number);
        //添加数据到数据库中
        User user = new User();
        user.setUser_id(uid);
        user.setPhone_number(number);
        user.setPassword(password);
//        Bitmap bitmap = ImageUtils.drawable2Bitmap(MyApplication.getContext().getResources().getDrawable(R.drawable.avatar1));
//        user.setIamge(bitmap);
        user.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    //注册成功
                    mIRegisterView.registerSuccessful();
                } else {
                    //注册失败
                    mIRegisterView.registerFailure(e);
                }
            }
        });
    }
}
