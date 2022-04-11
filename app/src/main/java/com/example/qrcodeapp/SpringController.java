package com.example.qrcodeapp;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SpringController {


    @GET("barcode/app_Login")
    Call<List<User_InfoVo>> app_login(@Query("emp_id") String emp_id , @Query("emp_pw") String emp_pw);

//    // Retrofit Helper
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(301, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build();

    public static final Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl("http://192.168.0.196:8080/controller/")  //정호대리님 ip
             .baseUrl("http://192.168.0.122:8181/controller/")
//            .baseUrl("http://ierp.interpass.co.kr/controller/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build();

}
