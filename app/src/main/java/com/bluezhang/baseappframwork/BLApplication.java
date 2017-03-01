package com.bluezhang.baseappframwork;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import com.alibaba.fastjson.JSON;
import com.bluezhang.baseappframwork.API.BLHandleNetworkUtil;
import com.bluezhang.baseappframwork.API.BLNetworkService;
import com.bluezhang.baseappframwork.API.BLRetrofitFactory;
import com.bluezhang.baseappframwork.tracking.BLAppInfo;
import com.bluezhang.baseappframwork.utils.BLDateTimeUtil;
import com.bluezhang.baseappframwork.utils.BLScreenUtil;
import com.bluezhang.baseappframwork.utils.BLSystemInfo;
import com.bluezhang.baseappframwork.utils.BLUtil;
import com.bluezhang.baseappframwork.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by blueZhang on 2017/2/27.
 *
 * @Author: BlueZhang
 * @date: 2017/2/27
 */

public class BLApplication extends Application {
    public static final boolean IS_DEBUG = BuildConfig.DEBUG;
    public static String sessionId;
    public static boolean havaPhoneState = false;
    public static boolean havaStorage = false;
    private static Context atContext;
    private static List<Activity> runningTasks = new ArrayList<Activity>();
    public boolean isCheckUpdate = false;
    private BLNetworkService networkService;
    private Scheduler defaultSubscribeScheduler;

    public static final boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    public static Context getAppContext() {
        return atContext;
    }

    public static BLApplication get(Context context) {
        return (BLApplication) context.getApplicationContext();
    }

    public static void exitApp() {
        for (Activity activity : runningTasks) {
            if (activity != null) {
                activity.finish();
            }
        }
    }

    public static void addActivity(Activity activity) {
        runningTasks.add(activity);
    }

    public static void removeActivity(Activity activity) {
        runningTasks.remove(activity);
    }

    public static String getSessionId() {
        return sessionId;
    }

    public static void setSessionId(String sessionId) {
        BLApplication.sessionId = sessionId;
    }

    public boolean isCheckUpdate() {
        return isCheckUpdate;
    }

    public void setIsCheckUpdate(boolean isCheckUpdate) {
        this.isCheckUpdate = isCheckUpdate;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        atContext = this;
        BLScreenUtil.init(this);
        BLDateTimeUtil.initATDateTimeUtil(this);

    }

    public BLNetworkService getNetworkService() {
        if (null == networkService) {
            networkService = BLRetrofitFactory.getAPIService(
                    BLNetworkService.class,
                    BLHandleNetworkUtil.getInstance().getHostName(),
                    getUserInfo(),
                    IS_DEBUG
            );
        }
        return networkService;
    }

    public Scheduler defaultSubscribeScheduler() {
        if (defaultSubscribeScheduler == null) {
            defaultSubscribeScheduler = Schedulers.io();
        }
        return defaultSubscribeScheduler;
    }

    public void setDefaultSubscribeScheduler(Scheduler scheduler) {
        this.defaultSubscribeScheduler = scheduler;
    }

    private String getUserInfo() {
        BLAppInfo info = new BLAppInfo();
        info.setAppVersion(BLUtil.getAppVersionInfo(BLApplication.getAppContext()).getVersionName());
        info.setChannelID(BLUtil.getChannelID());
        info.setDeviceID(BLSystemInfo.getInstance().getDeviceId());
        info.setDeviceType(BLSystemInfo.getInstance().getPhoneModel());
        info.setOSVersion(BLSystemInfo.getInstance().getVersionSystem());
        info.setPackageID(BLUtil.getAppVersionInfo(BLApplication.getAppContext()).getPageName());
        info.setBuildNumber(BLUtil.getAppVersionInfo(BLApplication.getAppContext()).getVersionCode());
        String userInfo = "";
        try {
            userInfo = JSON.toJSONString(info);
        } catch (Exception e) {
            LogUtil.e(e.getLocalizedMessage(), e);
        }
        return userInfo;
    }
}
