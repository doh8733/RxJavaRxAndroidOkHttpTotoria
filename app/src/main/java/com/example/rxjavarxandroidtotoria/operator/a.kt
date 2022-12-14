package com.example.rxjavarxandroidtotoria.operator

import android.util.Log
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subscribers.DisposableSubscriber
import java.util.ArrayList

class a {
    private fun createObservable(data: Int): Observable<Int> {
        return Observable.just(data)
    }

    private fun createListObservable(): List<Observable<*>> {
        val result: MutableList<Observable<*>> = ArrayList()
        result.add(createObservable(1))
        result.add(createObservable(2))
        result.add(createObservable(3))
        return result
    }


}