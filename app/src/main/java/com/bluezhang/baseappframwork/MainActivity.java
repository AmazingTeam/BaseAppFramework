package com.bluezhang.baseappframwork;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bluezhang.baseappframwork.API.BLNetworkService;
import com.bluezhang.baseappframwork.API.BaseAPIRequest;
import com.bluezhang.baseappframwork.API.BaseAPIResponse;
import com.bluezhang.baseappframwork.utils.LogUtil;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BLApplication blApplication = BLApplication.get(this);
        BLNetworkService networkService = blApplication.getNetworkService();
        BaseAPIRequest<String> apiRequest = new BaseAPIRequest<>();
        apiRequest.setCode("1234");
        apiRequest.setRequestObject("sdfsdfs");
        networkService.sendTracking(apiRequest)
                .subscribeOn(blApplication.defaultSubscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseAPIResponse<String>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.d("=====onCompleted====");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("=====onError====");
                    }

                    @Override
                    public void onNext(BaseAPIResponse<String> stringBaseAPIResponse) {
                        LogUtil.d("=====onNext====");
                        String data = stringBaseAPIResponse.getData();
                        LogUtil.d("=====onNext===="+data);
                    }
                });
    }
}
