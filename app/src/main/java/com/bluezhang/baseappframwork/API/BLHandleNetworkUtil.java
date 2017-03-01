package com.bluezhang.baseappframwork.API;

import android.text.TextUtils;

import com.bluezhang.baseappframwork.BLApplication;

/**
 * Created by blueZhang on 2017/2/28.
 *
 * @Author: BlueZhang
 * @date: 2017/2/28
 */

public class BLHandleNetworkUtil {
    public static final String DEVELOP = "http://10.7.2.100:8888";
    public static final String QA_TEST = "http://10.7.2.117:8888";
    public static final String OUTER_NET = "http://36.110.94.19:28888";
    private static String currentHostAddress;

    private BLHandleNetworkUtil() {
    }

    public static BLHandleNetworkUtil getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static void changeHostAddress(String currentHostAdd) {
        currentHostAddress = currentHostAdd;
    }

    public String getHostName() {
        if (!TextUtils.isEmpty(OUTER_NET)) {
            return OUTER_NET;
        }
        return QA_TEST;
    }

    private static class SingletonHolder {
        private static final BLHandleNetworkUtil INSTANCE = new BLHandleNetworkUtil();
    }
}
