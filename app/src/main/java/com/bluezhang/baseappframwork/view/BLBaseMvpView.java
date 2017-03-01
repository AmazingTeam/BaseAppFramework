package com.bluezhang.baseappframwork.view;

import android.content.Context;

import com.bluezhang.baseappframwork.API.BaseAPIResponse;

/**
 * Created by blueZhang on 2017/3/1.
 *
 * @Author: BlueZhang
 * @date: 2017/3/1
 */

public interface BLBaseMvpView <T> {
    Context getMyContext();

    void showNetWorkProgress();

    void disMissProgress();

    void onError(Throwable e);

    void setData(BaseAPIResponse<T> result);
}
