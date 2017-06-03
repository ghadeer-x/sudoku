package com.ancheng.sudoku.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * author: ancheng
 * date: 2017/6/2
 * email: lzjtugjc@163.com
 */

public class Game_Score extends BmobObject{
    private String userId;
    private Number time;
    private String level;
    private Number score;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Number getTime() {
        return time;
    }

    public void setTime(Number time) {
        this.time = time;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Number getScore() {
        return score;
    }

    public void setScore(Number score) {
        this.score = score;
    }
}
