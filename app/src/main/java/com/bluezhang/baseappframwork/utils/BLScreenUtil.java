package com.bluezhang.baseappframwork.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by blueZhang on 2017/2/28.
 *
 * @Author: BlueZhang
 * @date: 2017/2/28
 */

public class BLScreenUtil {

    //screen of width px
    public static int SCREEN_SAMLL = 240;
    public static int SCREEN_NORMAL = 320;
    public static int SCREEN_LARGE = 480;
    public static int SCREEN_XLARGE = 720;
    public static int SCREEN_XXXLARGE = 1440;
    private static int SCREEN_XXLARGE = 1080;
    // screen of size
    private static double LARGE_SCREEN_SIZE = 6.5;

    private static float density;
    private static int densityDpi;
    private static int widthPixels;
    private static int heightPixels;
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        density = dm.density;
        densityDpi = dm.densityDpi;
        widthPixels = dm.widthPixels;
        heightPixels = dm.heightPixels;
    }

    public static int dip2Px(float nDip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, nDip, getMetrics());
    }

    public static int sp2Px(float nSp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, nSp, getMetrics());
    }

    public static int px2Dip(float npx) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, npx, getMetrics());
    }

    @SuppressWarnings("deprecation")
    public static int getScreenWidth() {
        return mContext.getResources().getDisplayMetrics().widthPixels;
    }

    @SuppressWarnings("deprecation")
    public static int getScreenHeight() {
        return mContext.getResources().getDisplayMetrics().heightPixels;
//		return getWindowManager(mContext).getDefaultDisplay().getHeight();
    }

    public static DisplayMetrics getMetrics() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        density = dm.density;
        densityDpi = dm.densityDpi;
        widthPixels = dm.widthPixels;
        heightPixels = dm.heightPixels;
        return dm;
    }

    static private WindowManager getWindowManager() {
        return (WindowManager) mContext.getSystemService(mContext.WINDOW_SERVICE);
    }

    public static double getScreenSize() {
        DisplayMetrics dm = getMetrics();
        double size = Math.sqrt((Math.pow((dm.widthPixels / dm.xdpi), 2) + Math.pow((dm.heightPixels / dm.ydpi), 2)));
        return size;
    }

    public static boolean portPriority() {
        double size = getScreenSize();
        if (size > 6.9f)
            return true;

        return size >= 4.69f && Math.min(getScreenHeight(), getScreenWidth()) >= 700;
    }

    public static boolean isLargeSize() {
        return getScreenSize() >= LARGE_SCREEN_SIZE && getScreenWidth() > (SCREEN_XXLARGE - 10);
    }

    public static float getFontScale() {
        float scale = 1.0f;
        try {
            Class<?> activityManagerNative = Class.forName("android.app.ActivityManagerNative");
            try {
                Object am = activityManagerNative.getMethod("getDefault").invoke(activityManagerNative);
                Object config = am.getClass().getMethod("getConfiguration").invoke(am);
                Configuration configs = (Configuration) config;
                scale = configs.fontScale;
            } catch (IllegalArgumentException | SecurityException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                BLNetLogUtil.e(e.getMessage());
            }

        } catch (ClassNotFoundException e) {
            scale = 1.0f;
            e.printStackTrace();
        }
        return scale;
    }

    public static int px2sp(float pxValue) {
        final float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


}
