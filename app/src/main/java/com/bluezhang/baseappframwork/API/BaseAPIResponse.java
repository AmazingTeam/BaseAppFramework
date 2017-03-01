package com.bluezhang.baseappframwork.API;

import com.bluezhang.baseappframwork.constance.BLConstantData;

/**
 * Created by blueZhang on 2017/3/1.
 *
 * @Author: BlueZhang
 * @date: 2017/3/1
 */

public class BaseAPIResponse <T> {

    protected boolean success;

    protected int code;

    protected String message;

    protected T data;

    public boolean isSuccess() {
        return getCode() != 0 ? getCode() == BLConstantData.RESPONSE_CODE : success;

    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
