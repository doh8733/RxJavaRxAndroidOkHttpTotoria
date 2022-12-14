package com.example.rxjavarxandroidtotoria.demo_retrofit_rxjava.demo_login_retrofit_rxandroid_mvvm

import android.widget.Toast
import com.example.rxjavarxandroidtotoria.demo_retrofit_rxjava.RetrofitClient
import com.example.rxjavarxandroidtotoria.demo_retrofit_rxjava.User
import io.reactivex.rxjava3.core.Observable

class ApiService {

    fun register(request: User): Observable<List<User>> {
        return RetrofitClient.api.getAllUser()
            .doOnSubscribe{it}
            .doOnTerminate {false}
            .doOnError {handleRegistrationError()}
            .doOnNext {


            }
    }
    private fun processResponse(request: User){
        request
    }

    private fun handleRegistrationError() {
        TODO("Not yet implemented")
    }

}