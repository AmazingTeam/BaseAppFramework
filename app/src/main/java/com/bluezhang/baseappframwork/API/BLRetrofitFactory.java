package com.bluezhang.baseappframwork.API;

import com.bluezhang.baseappframwork.constance.BLConstantData;
import com.bluezhang.baseappframwork.utils.BLNetLogUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

import static com.bluezhang.baseappframwork.constance.BLConstantData.ENCRYPTION;
import static com.bluezhang.baseappframwork.constance.BLConstantData.FLAG;
import static com.bluezhang.baseappframwork.constance.BLConstantData.NO_ENCRYPTION;
import static com.bluezhang.baseappframwork.constance.BLConstantData.TOKEN;
import static com.bluezhang.baseappframwork.constance.BLConstantData.USER_INFO;
import static java.text.NumberFormat.Field.SIGN;

/**
 * Created by blueZhang on 2017/2/28.
 *
 * @Author: BlueZhang
 * @date: 2017/2/28
 */

public class BLRetrofitFactory {
    private static boolean sIsShowLog;

    private BLRetrofitFactory() {
    }

    public static <T> T getAPIService(Class<T> apiServiceClass, String baseURL, String userInfoJson,boolean isShowLog) {
        sIsShowLog = isShowLog;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(JsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(genericClient(userInfoJson))
                .build();
        return retrofit.create(apiServiceClass);
    }

    private static OkHttpClient genericClient(final String userInfoJson) {
        return new OkHttpClient().newBuilder()
                .connectTimeout(BLConstantData.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(BLConstantData.READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(BLConstantData.WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder builder = chain.request().newBuilder();
                        BLRequestBody atRequestBody = (BLRequestBody) chain.request().body();
                        builder.header(BLConstantData.SIGN, atRequestBody.getSignValue());
                        builder.header(TOKEN, atRequestBody.getTokenValue());
                        builder.header(FLAG, !sIsShowLog ? ENCRYPTION : NO_ENCRYPTION);
                        builder.header(USER_INFO, userInfoJson);
                        BLNetLogUtil.d("request_e_head = " + SIGN + ":" + atRequestBody.getSignValue());
                        BLNetLogUtil.d("request_e_head = " + TOKEN + ":" + atRequestBody.getTokenValue());
                        BLNetLogUtil.d("request_e_head = " + FLAG + ":" + sIsShowLog);
                        Request request = builder.build();
                        BLNetLogUtil.d("request.url = " + request.url().url());
                        return chain.proceed(request);
                    }
                })
                .build();
    }

    public static boolean issIsShowLog() {
        return sIsShowLog;
    }

}
