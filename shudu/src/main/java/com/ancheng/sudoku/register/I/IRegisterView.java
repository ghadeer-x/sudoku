package com.ancheng.sudoku.register.I;

/**
 * author: ancheng
 * date: 2017/5/24
 * email: lzjtugjc@163.com
 */

public interface IRegisterView {
    void registerSuccessful();
    void registerFailure(Exception e);
}
