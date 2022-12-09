package com.example.rxjavarxandroidtotoria.democreateobsevaerable


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.rxjavarxandroidtotoria.R
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class Observable2Activity : AppCompatActivity() {
    private lateinit var mDisposable: Disposable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_observable_interval)
        //Observable.interval()
        //tạo ra một Observaable phát ra chuỗi các số nguyên cách nhau một khoảng thời gian cụ thể.
        //gần giống với blocking trong coroutines
        // Có thể ứng dụng trong dự án báo thức

        //--------------------//
        //Observable.timer()

//        val observable: Observable<Long> = createObservableInterval()
//        val observer: Observer<Long> = createObserver()
//        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//            .subscribe(observer)
        //Observable.timer()
        //sẽ tạo ra môt item sau 1 khoangr thời ian delay cho trước
        val observableTimer: Observable<Long> = createObservableTimer()
        val observerTimer: Observer<Long> = createObserverTimer()
        observableTimer.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(observerTimer)
        //end
        //        //Observable.ranger()
        //Sẽ lần lượt tạo ra một dải các interger trong giải đó
        val observableRanger: Observable<Int> = createObservableRanger()
        val observerRanger: Observer<Int> = createObserverRanger()
        observableRanger.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(observerRanger)
        //end


        //        //Observable.repeat()
        //Sẽ lăcp đi lặp lại việc phát ra dữ liệu
        // và bạn có thể hạn chế số lần lặp bằng các set repeat() số lần
        val observableRepeat: Observable<Int> = createObservableRepeat()
        val observerRepeat: Observer<Int> = createObserverRepeat()
        observableRepeat.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(observerRepeat)
        //end

        //        //Observable.defer()
        //Sẽ không tạo Obserable cho đến khi ít nhất 1 bubcriber được đăng ký nó sẽ luôn tạo mới 1 observable tương ứng với mỗi subcriber
        val user = User(1,"Huy")
        val observableDefer: Observable<String> = user.getNameObservable()
        val observerDefer: Observer<String> = createObserverDefer()
        user.name = "Huy2"
        observableDefer.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(observerDefer)
        //end


    }

    //Observable.interval()
    private fun createObserver(): Observer<Long> {
        return object : Observer<Long> {
            override fun onSubscribe(d: Disposable) {
                Log.e("DIS", "onSubscribe: ")
                mDisposable = d
            }

            override fun onNext(t: Long) {
                Log.e("T", "onNext: $t")
                if (t == 3.toLong()) {
                    Toast.makeText(
                        this@Observable2Activity,
                        "Day di ong chau oi",
                        Toast.LENGTH_SHORT
                    ).show()
                    mDisposable.dispose()
                    if (mDisposable.isDisposed) {
                        Toast.makeText(
                            this@Observable2Activity,
                            "Đã hủy Observable Interval",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }


            }

            override fun onError(e: Throwable) {

            }

            override fun onComplete() {
                Log.e("Complete", "onComplete:")

            }

        }
    }

    private fun createObservableInterval(): Observable<Long> {

        return Observable.interval(3, 5, TimeUnit.SECONDS)
    }
    //----------------end----------------//
    //  //Observable.timer()

    private fun createObserverTimer(): Observer<Long> {
        return object : Observer<Long> {
            override fun onSubscribe(d: Disposable) {
                Log.e("DISTIME", "onSubscribe: ")
                mDisposable = d
            }

            override fun onNext(t: Long) {
                Log.e("T", "onNext: $t")

            }

            override fun onError(e: Throwable) {

            }

            override fun onComplete() {
                mDisposable.dispose()
                Log.e("Complete", "onComplete:")

            }

        }
    }

    private fun createObservableTimer(): Observable<Long> {

        return Observable.timer(3, TimeUnit.SECONDS)
    }
    //----------------end---------------//


    //--------------Observable.ranger()-------------//

    private fun createObserverRanger(): Observer<Int> {
        return object : Observer<Int> {
            override fun onSubscribe(d: Disposable) {
                Log.e("DISTIME", "onSubscribe: ")
                mDisposable = d
            }

            override fun onNext(t: Int) {
                Log.e("T", "onNext: $t")

            }

            override fun onError(e: Throwable) {

            }

            override fun onComplete() {
                Log.e("Complete", "onComplete:")

            }

        }
    }

    private fun createObservableRanger(): Observable<Int> {
        //co ranger() kieu in
        //rangeLong kieu Long
        return Observable.range(0, 10)
    }
    //----------------end----------------//

    //----------------Observable.repeat()-------------//
    private fun createObserverRepeat(): Observer<Int> {
        return object : Observer<Int> {
            override fun onSubscribe(d: Disposable) {
                Log.e("DISTIME", "onSubscribe: ")
                mDisposable = d
            }

            override fun onNext(t: Int) {
                Log.e("T", "onNext: $t")

            }

            override fun onError(e: Throwable) {

            }

            override fun onComplete() {
                Log.e("Complete", "onComplete:")

            }

        }
    }

    private fun createObservableRepeat(): Observable<Int> {
        //co ranger() kieu in
        //rangeLong kieu Long
        //sẽ lặp đi lặp lại việc tạo ra các phần tử từ 0 -10
        //set số lần lặp là 5 lần
        return Observable.range(0, 10).repeat(5)
    }
    //----------------end-----------------------------//


    //---------------Observable.defer()--------------//
    private fun createObserverDefer(): Observer<String> {
        return object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                Log.e("DISTIME", "onSubscribe: ")
                mDisposable = d
            }

            override fun onNext(t: String) {
                Log.e("T", "onNext: $t")

            }

            override fun onError(e: Throwable) {

            }

            override fun onComplete() {
                Log.e("Complete", "onComplete:")

            }

        }
    }

    private fun createObservableDefer(): Observable<Int> {
        //co ranger() kieu in
        //rangeLong kieu Long
        //sẽ lặp đi lặp lại việc tạo ra các phần tử từ 0 -10
        //set số lần lặp là 5 lần
        return Observable.range(0, 10).repeat(5)
    }
    //---------------end-----------------------------//

    private fun getalluser(): MutableList<User> {
        val list = mutableListOf<User>()
        list.add(User(1, "2"))
        list.add(User(3, "2"))
        list.add(User(4, "2"))
        list.add(User(5, "2"))
        return list
    }

    override fun onDestroy() {
        super.onDestroy()
        mDisposable.isDisposed

    }
}