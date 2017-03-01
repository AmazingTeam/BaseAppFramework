package com.bluezhang.baseappframwork.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.os.StatFs;
import android.text.TextUtils;

import com.bluezhang.baseappframwork.BLApplication;
import com.bluezhang.baseappframwork.constance.BLConstantData;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.UUID;

/**
 * Created by blueZhang on 2017/2/27.
 *
 * @Author: BlueZhang
 * @date: 2017/2/27
 */

public class BLUtil {
    private static final int TIME_DELAY = 600;
    private static final String ONE_STAR = "1";
    private static final String TWO_STAR = "2";
    private static final String THREE_STAR = "3";
    private static final String FOUR_STAR = "4";
    private static final String FIVE_STAR = "5";
    private static final int CHILD_AGE = 12;
    private static final int BABY_AGE = 2;
    private static long lastClickTime;

    public static byte[] longToByte(long number) {
        long temp = number;
        byte[] b = new byte[8];
        for (int i = 0; i < b.length; i++) {
            b[i] = Long.valueOf(temp & 0xff).byteValue();
            temp = temp >> 8;
        }
        return b;
    }
    /**
     * Whether the application is already installed
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isPackageInstalled(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packages = packageManager.getInstalledPackages(0);
        for (PackageInfo packageInfo : packages) {
            if (packageInfo.packageName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }
    public static boolean hasMapApp(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Uri mUri = Uri.parse("geo:");
        Intent mIntent = new Intent(Intent.ACTION_VIEW, mUri);
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(mIntent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfos.size() > 0;
    }

    public static boolean hasAPPMarket(Context context) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_APP_MARKET);
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
        int size = infos.size();
        return size > 0;
    }

    public static VersionInfo getAppVersionInfo(Context context) {
        VersionInfo versionInfo = null;
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionInfo = new VersionInfo(packageInfo.packageName, packageInfo.versionName, packageInfo.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionInfo;
    }
    /**
     * @return
     */
    public static String getChannelID() {
        String channels = "";
        try {
            ApplicationInfo appInfo = BLApplication.getAppContext().getPackageManager().getApplicationInfo(BLApplication.getAppContext().getPackageName(), PackageManager.GET_META_DATA);
//            String chan = appInfo.metaData.getString("TD_CHANNEL_ID");
            String chan = "at0";// TODO: 2017/3/1 change the channel
            if (TextUtils.isEmpty(chan)) {
                channels = "at0";
            } else {
                channels = chan + "";
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        if (TextUtils.isEmpty(channels)) {
            channels = "at0";
        }
        return channels;

    }

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String uniqueId = uuid.toString();
        LogUtil.d("debug", "----->UUID" + uuid);
        return uniqueId;
    }

    /**
     * Must be serialized object must be serialized object
     *
     * @param src
     * @param <T>
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static <T> T deepCopy(T src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        T copyArray = (T) in.readObject();
        return copyArray;
    }
    /**
     * change activity with parcelable data
     *
     * @param context    context
     * @param clazz      target activigty
     * @param parcelable param type
     * @param dataTag    data tag
     * @param <T>        class type
     */
    public static <T> void changeActivity(Context context, Class<T> clazz, Parcelable parcelable, String dataTag) {
        Intent intent = new Intent(context, clazz);
        intent.putExtra(dataTag, parcelable);
        context.startActivity(intent);
    }
    public static boolean getHasSdCard() {
        return Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED) && readSDCard() > 10;
    }

    /**
     * get file Permission
     *
     * @param dir
     */
    public static void getChmod(File dir) {
        String[] command = {"chmod", "777", dir.getPath()};

        ProcessBuilder builder = new ProcessBuilder(command);
        try {
            builder.start();

        } catch (IOException e) {

            // TODO Auto-generated catch block

            e.printStackTrace();

        }
    }

    /**
     * SD card available space, the unit for MB
     */
    private static long readSDCard() {
        long AvailableRoom = 0;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long blockSize = sf.getBlockSize();
            // long blockCount = sf.getBlockCount();
            long availCount = sf.getAvailableBlocks();
            AvailableRoom = availCount * blockSize / 1024 / 1024;
        }
        return AvailableRoom;
    }
    public static String getSdPath() {
        File sdDir = null;
        String asiaTravelPath = "";
        if (BLApplication.havaStorage && getHasSdCard() && readSDCard() > 10) {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
            asiaTravelPath = sdDir.getAbsolutePath() + File.separator + BLConstantData.APP_CACHE_PATH_FOLDER;
        } else {
            File myPackageDir = new File(BLApplication.getAppContext().getFilesDir(), File.separator + BLConstantData.APP_CACHE_PATH_FOLDER);
            if (!myPackageDir.exists()) {
                myPackageDir.mkdirs();
            }
            getChmod(BLApplication.getAppContext().getFilesDir());
            getChmod(myPackageDir);
            asiaTravelPath = myPackageDir.getAbsolutePath();
        }
        File file = new File(asiaTravelPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        getChmod(file);
        return file.getAbsolutePath();
    }

    public static float getAlphaScale(int currentData, int allData) {
        return currentData * 1.F / allData;
    }

    /**
     * get app running state
     *
     * @param context context
     * @return context;
     */
    public static boolean appIsRunning(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = am.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo info : runningTasks) {
            if (info.topActivity.getPackageName().equals(context.getPackageName()) && info.baseActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }
    public static class VersionInfo {
        private String versionName;
        private int versionCode;
        private String pageName;

        public VersionInfo(String pageName, String versionName, int versionCode) {
            this.versionName = versionName;
            this.versionCode = versionCode;
            this.pageName = pageName;
        }

        public String getVersionName() {
            return versionName;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public String getPageName() {
            return pageName;
        }
    }
}
