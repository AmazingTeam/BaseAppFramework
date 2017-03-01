package com.bluezhang.baseappframwork.API;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by blueZhang on 2017/2/28.
 *
 * @Author: BlueZhang
 * @date: 2017/2/28
 */

public interface BLNetworkService {
  /*  @POST("api/GetServiceApiResult")
    Observable<ATAPIResponse<String>> sendTracking(@Body ATAPIRequest<ATTrackingRequest> request);*/
  @POST("api/GetServiceApiResult")
  Observable<BaseAPIResponse<String>> sendTracking(@Body BaseAPIRequest<String> request);
}
