package com.bluezhang.baseappframwork.presenter;

/**
 * Created by blueZhang on 2017/3/1.
 *
 * @Author: BlueZhang
 * @date: 2017/3/1
 */

public interface BLBasePresenter<V> {
    void attachView(V view);

    void detachView();
}