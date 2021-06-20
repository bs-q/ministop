package com.iservice.agency.data.remote;

import com.iservice.agency.data.model.api.ResponseWrapper;
import com.iservice.agency.data.model.api.request.CreateAccountRequest;
import com.iservice.agency.data.model.api.request.LoginRequest;
import com.iservice.agency.data.model.api.response.CreateAccountResponse;
import com.iservice.agency.data.model.api.response.LoginResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @POST("v1/account/login")
    @Headers({"IgnoreAuth: 1"})
    Observable<ResponseWrapper<LoginResponse>> login(@Body LoginRequest request);

    @GET("v1/account/profile")
    Observable<ResponseWrapper<LoginResponse>> profile();

    @POST("v1/agency/register")
    @Headers({"IgnoreAuth: 1"})
    Observable<ResponseWrapper<CreateAccountResponse>> createAccount(@Body CreateAccountRequest request);
}
