package com.example.rxjavarxandroidtotoria.demo_retrofit_rxjava

import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface TodoApi {
    @GET("posts")
    fun getAllUser():Observable<List<User>>
}