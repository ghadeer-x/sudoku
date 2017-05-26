package com.ancheng.sudoku.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * author: ancheng
 * date: 2017/5/24
 * email: lzjtugjc@163.com
 */

public class User extends BmobObject{
    private String user_id;
    private String signature;
    private String phone_number;
    private String nickName;
    private Object iamge;
    private String email;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Object getIamge() {
        return iamge;
    }

    public void setIamge(Object image) {
        this.iamge = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
