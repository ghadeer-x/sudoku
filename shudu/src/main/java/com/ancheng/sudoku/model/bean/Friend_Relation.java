package com.ancheng.sudoku.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * author: ancheng
 * date: 2017/6/2
 * email: lzjtugjc@163.com
 */

public class Friend_Relation extends BmobObject {
    private String userId;
    private String friendId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }
}
