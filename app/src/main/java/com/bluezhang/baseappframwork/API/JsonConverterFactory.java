package com.bluezhang.baseappframwork.API;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by blueZhang on 2017/2/28.
 *
 * @Author: BlueZhang
 * @date: 2017/2/28
 */

public class JsonConverterFactory extends Converter.Factory {
    private JsonConverterFactory() {
    }

    /**
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    public static JsonConverterFactory create() {
        return new JsonConverterFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new JsonResponseBodyConverter<>(type);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new JsonRequestBodyConverter<>();
    }
}
