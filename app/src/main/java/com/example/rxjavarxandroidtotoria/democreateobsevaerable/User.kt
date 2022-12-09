package com.example.rxjavarxandroidtotoria.democreateobsevaerable

import io.reactivex.rxjava3.core.Observable
import java.io.Serializable

data class User(var id : Int =0,var name :String =""):Serializable  {

    override fun toString(): String {
        return "User(id=$id, name='$name')"
    }
    fun  getNameObservable() :Observable<String>{
        return Observable.just(name)
    }
    fun getNameDeferObservable():Observable<String>{
        return Observable.defer {
            Observable.just(name)
        }
    }
}