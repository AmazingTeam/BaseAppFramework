package com.bluezhang.baseappframwork.enumeration;

/**
 * Created by blueZhang on 2017/2/27.
 *
 * @Author: BlueZhang
 * @date: 2017/2/27
 */

public enum FrontEndType {
    IOS(1),
    ANDROID(2),
    H5(3);

    private int value;

    private FrontEndType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
