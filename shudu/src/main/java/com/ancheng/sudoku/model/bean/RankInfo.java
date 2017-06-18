package com.ancheng.sudoku.model.bean;

import android.support.annotation.NonNull;

/**
 * author: ancheng
 * date: 2017/6/5
 * email: lzjtugjc@163.com
 */

public class RankInfo implements Comparable<RankInfo>{
    private User user;
    private int score;

    public RankInfo() {
    }

    public RankInfo(User user, int score) {
        this.user = user;
        this.score = score;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int compareTo(@NonNull RankInfo o) {
        return this.getScore() - o.getScore();
    }
}
