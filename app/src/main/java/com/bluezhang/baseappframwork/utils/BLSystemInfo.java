package com.bluezhang.baseappframwork.utils;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.bluezhang.baseappframwork.BLApplication;
import com.bluezhang.baseappframwork.constance.BLConstantData;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * Created by blueZhang on 2017/2/27.
 *
 * @Author: BlueZhang
 * @date: 2017/2/27
 */

public class BLSystemInfo {
    private static final String SAVE_IMEI_FILE = "asia_code.log";
    private static final String SAVE_IMEI_DIR = "asia_code_dir";
    private String phoneModel;
    private String versionSystem;
    private String macAddress;
    private String imei;
    private String androidId;
    private String deviceId;

    private BLSystemInfo() {
        String code = (String) BLSharePreferenceUtil.getInstance().getParam(BLConstantData.UNIQUE_CODE, "");
        LogUtil.d("ATApplication.havaPhoneState = " + BLApplication.havaPhoneState);
        if (BLApplication.havaPhoneState) {
            TelephonyManager tm = (TelephonyManager) BLApplication.getAppContext()
                    .getSystemService(
                            Context.TELEPHONY_SERVICE);
            imei = tm.getDeviceId();
        }
        phoneModel = android.os.Build.MODEL;
        versionSystem = android.os.Build.VERSION.RELEASE;
        androidId = "" + android.provider.Settings.Secure.getString(BLApplication.getAppContext().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        WifiManager wifi = (WifiManager) BLApplication.getAppContext().getSystemService(
                Context.WIFI_SERVICE);
        if (wifi.isWifiEnabled()) {
            macAddress = getLocalMacAddressFromIp();
        }
        if (!TextUtils.isEmpty(code)) {
            deviceId = code;
        } else {
            deviceId = readDeviceIdSd();
            if (TextUtils.isEmpty(deviceId)) {
                deviceId = MD5Util.encryptAsia(BLStringUtil.concatString(androidId, imei, macAddress));
                writeDeviceIdSd(deviceId);
            }
            BLSharePreferenceUtil.getInstance().setParam(BLConstantData.UNIQUE_CODE, deviceId);
        }
        LogUtil.d("deviceId md5 = " + deviceId);
    }

    public static String getLocalMacAddressFromIp() {
        String mac_s = "";
        try {
            byte[] mac;
            NetworkInterface ne = NetworkInterface.getByInetAddress(InetAddress.getByName(NetworkUtil.getLocalIpAddress()));
            mac = ne.getHardwareAddress();
            LogUtil.d("mac = " + mac);
            mac_s = byte2hex(mac);
        } catch (Exception e) {
            LogUtil.e(e.getLocalizedMessage(), e);
        }

        return mac_s;
    }

    public static String byte2hex(byte[] b) {
        StringBuffer hs = new StringBuffer(b.length);
        String stmp = "";
        int len = b.length;
        for (int n = 0; n < len; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            if (stmp.length() == 1)
                hs = hs.append("0").append(stmp);
            else {
                hs = hs.append(stmp);
            }
        }
        return String.valueOf(hs);
    }

    public static BLSystemInfo getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public String getVersionSystem() {
        return versionSystem;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public String getImei() {
        return imei;
    }

    public String getAndroidId() {
        return androidId;
    }

    public String getDeviceId() {
        return deviceId;
    }


    private void writeDeviceIdSd(String code) {
        File fileDir = new File(BLUtil.getSdPath() + File.separator + SAVE_IMEI_DIR);
        try {
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            File file = new File(fileDir, SAVE_IMEI_FILE);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file, false);
            BufferedOutputStream outputStream = new BufferedOutputStream(out);
            outputStream.write(code.getBytes("utf-8"));
            outputStream.close();
        } catch (Exception e) {
            LogUtil.e(e.getLocalizedMessage(), e);
        }
    }

    private String readDeviceIdSd() {
        StringBuffer sb = new StringBuffer();
        String tempstr = null;
        try {
            File file = new File(BLUtil.getSdPath() + File.separator + SAVE_IMEI_DIR, SAVE_IMEI_FILE);
            if (!file.exists()) {
                return "";
            }
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            while ((tempstr = br.readLine()) != null) {
                sb.append(tempstr);
            }
            br.close();
        } catch (IOException ex) {
            LogUtil.e(ex.getLocalizedMessage(), ex);
        }
        return sb.toString();
    }
    private static class SingletonHolder {
        final static BLSystemInfo INSTANCE = new BLSystemInfo();
    }

}
