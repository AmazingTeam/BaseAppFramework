package com.bluezhang.baseappframwork.utils;

/**
 * Created by blueZhang on 2017/2/28.
 *
 * @Author: BlueZhang
 * @date: 2017/2/28
 */

public class BLEncrypt {
    //    static {
//        System.loadLibrary("asiaEncrypt");
//    }
//
//    public native String getEncryptFirstKey();
//
//    public native String getEncryptSecondKey();
//
//    public native String getEncryptLastKey();
//
//    public native String getEncryptMode();
//
//    public native String getSignFirstKey();
//
//    public native String getSignSecondKey();
//
//    public native String getSignLastKey();
//
//    public native String getEncryptFirstIv();
//
//    public native String getEncryptSecondIv();
//
//    public native String getEncryptLastIv();

    public String getEncryptFirstKey() {
        return "YWJjZGV";
    }

    public String getEncryptSecondKey() {
        return "mZ2hpamtsb";
    }

    public String getEncryptLastKey() {
        return "W5vcHFyc3R1dnd4";
    }

    public String getEncryptMode() {
        return "AES/CBC/PKCS5Padding";
    }

    public String getSignFirstKey() {
        return "21cd907ac49498945b9fdae";
    }

    public String getSignSecondKey() {
        return "8ef538acddcc04d40cb49024307";
    }

    public String getSignLastKey() {
        return "ac01ee19854c101e6ec04c85872361";
    }

    public String getEncryptFirstIv() {
        return "YWJjZ";
    }

    public String getEncryptSecondIv() {
        return "GVmZ2";
    }

    public String getEncryptLastIv() {
        return "hpamts";
    }

}
