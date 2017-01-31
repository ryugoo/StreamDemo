package com.r384ta.android.streamdemo.network;

import com.r384ta.android.streamdemo.model.HttpBinGet;

import io.reactivex.Flowable;
import retrofit2.Response;
import retrofit2.http.GET;

public interface HttpBinService {
    @GET("/get")
    Flowable<Response<HttpBinGet>> get();
}
