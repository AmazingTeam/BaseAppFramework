package com.bluezhang.baseappframwork.API;

import com.alibaba.fastjson.annotation.JSONField;
import com.bluezhang.baseappframwork.enumeration.FrontEndType;

/**
 * Created by blueZhang on 2017/2/27.
 *
 * @Author: BlueZhang
 * @date: 2017/2/27
 */

public class BaseAPIRequest<T> {
    @JSONField(name = "Parameters")
    private T requestObject;

    @JSONField(name = "Code")
    private String code;

    @JSONField(name = "ForeEndType")
    private int frontEndType = FrontEndType.ANDROID.getValue();

    public T getRequestObject() {
        return requestObject;
    }

    public void setRequestObject(T requestObject) {
        this.requestObject = requestObject;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getFrontEndType() {
        return frontEndType;
    }

    public void setFrontEndType(int frontEndType) {
        this.frontEndType = frontEndType;
    }
}
