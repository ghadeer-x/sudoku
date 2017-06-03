package com.ancheng.sudoku.model;

import com.ancheng.sudoku.model.bean.User;

import cn.bmob.v3.datatype.BmobFile;

/**
 * author: ancheng
 * date: 2017/6/1
 * email: lzjtugjc@163.com
 */

public class UserManager {

    private static UserManager sUserManager;
    private User mUser;

    public synchronized static UserManager getInstance() {
        if (sUserManager == null) {
            sUserManager = new UserManager();
        }
        return sUserManager;
    }

    public User getUser() {
        return mUser;
    }

    public String getObjectId() {
        String objectId = null;
        if (mUser != null) {
            objectId = mUser.getObjectId();
        }
        return objectId;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public String getPassword() {
        String passWord = null;
        if (mUser != null) {
            passWord = mUser.getPassword();
        }
        return passWord;
    }

    public void setPassword(String password) {
        if (mUser != null) {
            mUser.setPassword(password);
        }
    }

    public String getUser_id() {
        String user_id = null;
        if (mUser != null) {
            user_id = mUser.getUser_id();
        }
        return user_id;
    }

    public String getSignature() {
        String signature = null;
        if (mUser != null) {
            signature = mUser.getSignature();
        }
        return signature;
    }

    public void setSignature(String signature) {
        if (mUser != null) {
            mUser.setSignature(signature);
        }
    }

    public String getPhone_number() {
        String phone_number = null;
        if (mUser != null) {
            phone_number = mUser.getPhone_number();
        }
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        if (mUser != null) {
            mUser.setPhone_number(phone_number);
        }
    }

    public String getNickName() {
        String nickName = null;
        if (mUser != null) {
            nickName = mUser.getNickName();
        }
        return nickName;
    }

    public void setNickName(String nickName) {
        if (mUser != null) {
            mUser.setNickName(nickName);
        }
    }

    public String getEmail() {
        String email = null;
        if (mUser != null) {
            email = mUser.getEmail();
        }
        return email;
    }

    public void setEmail(String email) {
        if (mUser != null) {
            mUser.setEmail(email);
        }
    }

    public BmobFile getAvatar() {
        BmobFile avatar = null;
        if (mUser != null) {
            avatar = mUser.getAvatar();
        }
        return avatar;
    }

    public void setAvatar(BmobFile avatar) {
        if (mUser != null) {
            mUser.setAvatar(avatar);
        }
    }
}
