package com.bluezhang.baseappframwork.utils;

import android.text.TextUtils;
import android.util.Log;

import com.bluezhang.baseappframwork.BLApplication;

/**
 * Created by blueZhang on 2017/2/27.
 *
 * @Author: BlueZhang
 * @date: 2017/2/27
 */

public class LogUtil {

    private LogUtil() {
    }

    private static String generateTag() {
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        String customTagPrefix = "at_log";
        tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":" + tag;
        return tag;
    }

    public static void d(String message, Object... args) {
        if (!BLApplication.isDebug()) return;
        d(String.format(message, args));
    }

    public static void d(String content) {
        if (!BLApplication.isDebug()) return;
        String tag = generateTag();
        Log.d(tag, content);
    }

    public static void d(String content, Throwable tr) {
        if (!BLApplication.isDebug()) return;
        String tag = generateTag();
        Log.d(tag, content, tr);
    }

    public static void e(String content) {
        if (!BLApplication.isDebug()) return;
        String tag = generateTag();
        Log.e(tag, content);
    }

    public static void e(String content, Throwable tr) {
        if (!BLApplication.isDebug()) return;
        String tag = generateTag();
        Log.e(tag, content, tr);
    }

    public static void i(String content) {
        if (!BLApplication.isDebug()) return;
        String tag = generateTag();
        Log.i(tag, content);
    }

    public static void i(String content, Throwable tr) {
        if (!BLApplication.isDebug()) return;
        String tag = generateTag();
        Log.i(tag, content, tr);
    }

    public static void v(String content) {
        if (!BLApplication.isDebug()) return;
        String tag = generateTag();
        Log.v(tag, content);
    }

    public static void v(String content, Throwable tr) {
        if (!BLApplication.isDebug()) return;
        String tag = generateTag();
        Log.v(tag, content, tr);
    }

    public static void w(String content) {
        if (!BLApplication.isDebug()) return;
        String tag = generateTag();
        Log.w(tag, content);
    }

    public static void w(String content, Throwable tr) {
        if (!BLApplication.isDebug()) return;
        String tag = generateTag();
        Log.w(tag, content, tr);
    }

    public static void w(Throwable tr) {
        if (!BLApplication.isDebug()) return;
        String tag = generateTag();
        Log.w(tag, tr);
    }


    public static void wtf(String content) {
        if (!BLApplication.isDebug()) return;
        String tag = generateTag();

        Log.wtf(tag, content);
    }

    public static void wtf(String content, Throwable tr) {
        if (!BLApplication.isDebug()) return;
        String tag = generateTag();

        Log.wtf(tag, content, tr);
    }

    public static void wtf(Throwable tr) {
        if (!BLApplication.isDebug()) return;
        String tag = generateTag();

        Log.wtf(tag, tr);
    }

    public static void log(String msg) {
        if (!BLApplication.isDebug()) return;
        Log.i("asiatravel", msg);
    }
}
