package com.bluezhang.baseappframwork.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bluezhang.baseappframwork.BLApplication;
import com.bluezhang.baseappframwork.constance.BLConstantData;

/**
 * Created by blueZhang on 2017/2/27.
 *
 * @Author: BlueZhang
 * @date: 2017/2/27
 */

public class BLSharePreferenceUtil {
    public static final String spName = "asia_travel_sp";
    public static final String ERROR_PASSWORD_CURRENT_TIME = "error_input_pass_current_time";
    public static String type;
    private static SharedPreferences sp = BLApplication.getAppContext().getSharedPreferences(spName, Context.MODE_PRIVATE);


    private BLSharePreferenceUtil() {
    }

    public static BLSharePreferenceUtil getInstance() {
        return SingletonInstance.INSTANCE;
    }

    public static boolean getIsFirstRun() {
        return sp.getBoolean(BLConstantData.KEY_IS_FIRSTRUN, true);
    }

    /**
     * apply is faster as it is asynchronous.
     * <pre>this is the google doc</pre>
     * http://developer.android.com/reference/android/content/SharedPreferences.Editor.html#apply()
     *
     * @param isFirstRun is first run
     */
    public static void setIsFirstRun(boolean isFirstRun) {
        sp.edit().putBoolean(BLConstantData.KEY_IS_FIRSTRUN, isFirstRun).apply();
    }

    public static void destroySp() {
        sp = null;
    }


    public void setParam(String key, Object object) {
        if (object != null) {
            String type = object.getClass().getSimpleName();
            SharedPreferences.Editor editor = sp.edit();
            if ("String".equals(type)) {
                editor.putString(key, (String) object);
            } else if ("Integer".equals(type)) {
                editor.putInt(key, (Integer) object);
            } else if ("Boolean".equals(type)) {
                editor.putBoolean(key, (Boolean) object);
            } else if ("Float".equals(type)) {
                editor.putFloat(key, (Float) object);
            } else if ("Long".equals(type)) {
                editor.putLong(key, (Long) object);
            }
            editor.apply();
        } else {
            throw new NullPointerException("The default object can't be null!");
        }
    }

    public Object getParam(String key, Object defaultObject) {
        if (defaultObject != null) {
            String type = defaultObject.getClass().getSimpleName();
            if ("String".equals(type)) {
                return sp.getString(key, (String) defaultObject);
            } else if ("Integer".equals(type)) {
                return sp.getInt(key, (Integer) defaultObject);
            } else if ("Boolean".equals(type)) {
                return sp.getBoolean(key, (Boolean) defaultObject);
            } else if ("Float".equals(type)) {
                return sp.getFloat(key, (Float) defaultObject);
            } else if ("Long".equals(type)) {
                return sp.getLong(key, (Long) defaultObject);
            }
        } else {
            throw new NullPointerException("The default object can't be null!");
        }
        return null;
    }


    /**
     * Use Activity name as key and Bean as value
     *
     * @param param Class
     * @param obj   Object
     * @param <T>
     */
    public <T> void setObject(Class<?> param, T obj) {
        if (param != null && obj != null) {
            String key = param.getName();
            String object = JSON.toJSONString(obj);
            setParam(key, object);
        }
    }


    public <T> void setObject(String key, T obj) {
        if (key != null && obj != null) {
            String object = JSON.toJSONString(obj);
            setParam(key, object);
        }
    }


    public <T> T getObject(String key, Class<T> type) {
        String object = (String) getParam(key, "");
        if (TextUtils.isEmpty(object)) {
            return null;
        }
        return JSON.parseObject(object, type);
    }


    /**
     * Get Object
     *
     * @param param
     * @return
     */
    public <T> T getObject(Class<?> param, Class<T> type) {
        String object = (String) getParam(param.getName(), "");
        if (TextUtils.isEmpty(object)) {
            return null;
        }
        return JSON.parseObject(object, type);
    }

    private static class SingletonInstance {
        private static final BLSharePreferenceUtil INSTANCE = new BLSharePreferenceUtil();
    }
}
