package com.bluezhang.baseappframwork.API;

import com.alibaba.fastjson.JSON;
import com.bluezhang.baseappframwork.utils.BLNetLogUtil;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by blueZhang on 2017/2/28.
 *
 * @Author: BlueZhang
 * @date: 2017/2/28
 */

public final class JsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private Type type;

    JsonResponseBodyConverter(Type type) {
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        BLNetLogUtil.d("response = " + response);
        return JSON.parseObject(response, type);
    }
}