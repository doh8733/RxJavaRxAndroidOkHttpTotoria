package com.example.rxjavarxandroidtotoria.demo_retrofit_rxjava

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

class RequestToken(token: String) {
    //----------Call api co token -------------//
    val interceptor = Interceptor { chain ->
        val request: Request = chain.request()
        val builder: Request.Builder = request.newBuilder()
        builder.addHeader("Authorization", "Bearer $token") //value sẽ là tham số token
        chain.proceed(builder.build())
    }

    val okHttpClientToken =
        OkHttpClient.Builder().addInterceptor(interceptor)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
}