package com.bluezhang.baseappframwork.API;

import android.util.Base64;

import com.alibaba.fastjson.JSON;
import com.bluezhang.baseappframwork.utils.BLNetLogUtil;
import com.bluezhang.baseappframwork.utils.BLStringUtil;
import com.bluezhang.baseappframwork.utils.Cryptos;
import com.bluezhang.baseappframwork.utils.MD5Util;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * Created by blueZhang on 2017/2/28.
 *
 * @Author: BlueZhang
 * @date: 2017/2/28
 */

public class JsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private static final String TAG = "JsonRequestBodyConverter";

    JsonRequestBodyConverter() {

    }

    @Override
    public RequestBody convert(T value) throws IOException {
        long currentTime = System.currentTimeMillis();
        String jason = null;
        try {
            jason = Cryptos.aesEncrypt(JSON.toJSONString(value));
        } catch (Exception e) {
            BLNetLogUtil.e(TAG, e);
        }
        BLNetLogUtil.d("request = " + (!BLRetrofitFactory.issIsShowLog() ? jason : JSON.toJSONString(value)));
        BLRequestBody atRequestBody = BLRequestBody.create(MEDIA_TYPE, !BLRetrofitFactory.issIsShowLog() ? jason : JSON.toJSONString(value));
        atRequestBody.setSignValue(getSingValue(JSON.toJSONString(value), currentTime));
        atRequestBody.setTokenValue(getTokenValue(currentTime));
        return atRequestBody;
    }

    private String getSingValue(String jason, long currentTime) {
        return MD5Util.encryptToMD5(BLStringUtil.concatString(String.valueOf(currentTime), jason));
    }

    private String getTokenValue(long currentTime) {
        return Base64.encodeToString(String.valueOf(currentTime).getBytes(), Base64.NO_WRAP);
    }
}
