package com.ancheng.sudoku.model.bean;

/**
 * author: ancheng
 * date: 2017/6/5
 * email: lzjtugjc@163.com
 */

public class RankInfo {
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
}
