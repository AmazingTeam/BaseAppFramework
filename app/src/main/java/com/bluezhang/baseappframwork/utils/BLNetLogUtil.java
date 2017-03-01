package com.bluezhang.baseappframwork.utils;

import android.text.TextUtils;

import com.bluezhang.baseappframwork.API.BLRetrofitFactory;
import com.orhanobut.logger.Logger;

/**
 * Created by blueZhang on 2017/2/28.
 *
 * @Author: BlueZhang
 * @date: 2017/2/28
 */

public class BLNetLogUtil {

    private BLNetLogUtil() {
    }

    private static String generateTag() {
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        String customTagPrefix = "at_net_log";
        tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":" + tag;
        return tag;
    }

    public static void d(String message, Object... args) {
        if (!BLRetrofitFactory.issIsShowLog()) return;
        d(String.format(message, args));
    }

    public static void d(String content) {
        if (!BLRetrofitFactory.issIsShowLog()) return;
        Logger.d(content);
    }

    public static void d(String content, Throwable tr) {
        if (!BLRetrofitFactory.issIsShowLog()) return;
        Logger.d(content);
    }

    public static void e(String content) {
        if (!BLRetrofitFactory.issIsShowLog()) return;
        Logger.e(content);
    }

    public static void e(String content, Throwable tr) {
        if (!BLRetrofitFactory.issIsShowLog()) return;
        Logger.e(content);
    }

    public static void i(String content) {
        if (!BLRetrofitFactory.issIsShowLog()) return;
        Logger.i(content);
    }

    public static void i(String content, Throwable tr) {
        if (!BLRetrofitFactory.issIsShowLog()) return;
        Logger.i(content);
    }

    public static void v(String content) {
        if (!BLRetrofitFactory.issIsShowLog()) return;
        String tag = generateTag();
        Logger.v(content);
    }

    public static void v(String content, Throwable tr) {
        if (!BLRetrofitFactory.issIsShowLog()) return;
        Logger.v(content);
    }

    public static void w(String content) {
        if (!BLRetrofitFactory.issIsShowLog()) return;
        Logger.w(content);
    }

    public static void w(String content, Throwable tr) {
        if (!BLRetrofitFactory.issIsShowLog()) return;
        Logger.w(content);
    }

    public static void w(Throwable tr) {
        if (!BLRetrofitFactory.issIsShowLog()) return;
        String tag = generateTag();
        Logger.w(tag, tr);
    }


    public static void wtf(String content) {
        if (!BLRetrofitFactory.issIsShowLog()) return;
        Logger.wtf(content);
    }

    public static void wtf(String content, Throwable tr) {
        if (!BLRetrofitFactory.issIsShowLog()) return;
        Logger.wtf(content);
    }

    public static void wtf(Throwable tr) {
        if (!BLRetrofitFactory.issIsShowLog()) return;
        String tag = generateTag();
        Logger.wtf(tag, tr);
    }

    public static void log(String msg) {
        if (BLRetrofitFactory.issIsShowLog()) {
            Logger.i("asiatravel", msg);
        }
    }
}
