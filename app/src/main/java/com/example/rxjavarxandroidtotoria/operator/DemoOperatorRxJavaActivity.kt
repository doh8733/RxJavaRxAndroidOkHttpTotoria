package com.example.rxjavarxandroidtotoria.operator

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.rxjavarxandroidtotoria.R
import com.example.rxjavarxandroidtotoria.demo_retrofit_rxjava.RetrofitClient
import com.example.rxjavarxandroidtotoria.demo_retrofit_rxjava.User
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.schedulers.TestScheduler
import io.reactivex.rxjava3.subscribers.DisposableSubscriber
import java.util.concurrent.TimeUnit
import kotlin.math.log


class DemoOperatorRxJavaActivity : AppCompatActivity() {
    val list = mutableListOf<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_operator_rx_java)
        //------------map()---------------///
        //map được dung đẻ convert các kiêu dữ liệu vií dụ như demo 2 chúng ta conver từ Lít<User> sang kiểu dữ liệu String
        createOperatorMap()
        createOperator2()
        //----------flatmap()-----------//
        //Flatmap() có thể được dùng để thay thế map nhưn nó khác map ở chõ map sẽ trả về Object còn flatmap trả về Observable
        createOperatorFlatmap()
        //-----------zip-----------//
        //zip cho phep gop cac obsever lai voi nhau va tra ve cung luc vi du t se thu hien dem so 1 ,2,3 nhung zip se thuc hien dem 123 cung mot luc
        operatorZip()
        zipList()
        //merge se tra ket qua theo tung phan tu sau khi da xac thuc xong obsever
        operatorMerge()
        mergeList()
        //--------------concat------------//
        //concat() sẽ hỗ trợ việc gộp cách thức lấy dữ liệu từ server trả về và và từ local database lại và trả về chung một kết quả từ đó chỉ cần hiển thị ra kêts quả
        //concat() ngoài ra còn con cat còn gộp các observable lại với nhau rồi thực hiện tuần tự chúng.
        demoConcat()
        demoConcatMap()

    }

    private fun createOperatorMap() {
        var observable: Observable<List<User>> = RetrofitClient.api.getAllUser()
        observable.map {
            10 * it.size
        }.subscribe(System.out::println)
    }

    private fun createOperator2() {
        RetrofitClient.api.getAllUser().map(Function<List<User>, String>() { input ->
            input.toString()
            //tren Function input la List<User> output se la string
        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { output ->

                println(output)
            }
    }



    private fun createOperatorFlatmap() {
        RetrofitClient.api.getAllUser().flatMap(Function<List<User>, Observable<List<User>>>() {
            try {
                Observable.defer {
                    return@defer Observable.just(it)
                }
            } catch (e: Exception) {
                return@Function Observable.error(e)
            }
        })
//            .flatMap {
//                Observable.fromArray(it)
//            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<User>> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: List<User>) {
                    //loc nhung user cos id lon hon 50
                    Log.e("LOG", "onNext: $t", )

                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                    Log.e("LIST", "onComplete: ${list.toString()}")
                }


            })
    }

    private fun createObservable(data: Int): Observable<Int> {
        return Observable.just(data)
    }

    private fun createListObservable(): List<Observable<*>> {
        val result: MutableList<Observable<*>> = mutableListOf()
        result.add(createObservable(1))
        result.add(createObservable(2))
        result.add(createObservable(3))
        return result
    }

    //--------zip---------//
    private fun operatorZip() {
        Observable.zip(
            createObservable(1), createObservable(2), createObservable(3)
        ) { t1, t2, t3 -> DataZip(t1, t2, t3) }.subscribe(object : Observer<DataZip> {
            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: DataZip) {
                Log.e("zip", "onNext: ${t.numberOne}  ${t.numberTwo}  ${t.numberThree}")
            }

            override fun onError(e: Throwable) {
            }

            override fun onComplete() {
            }

        })
    }

    private fun zipList() {
        Observable.zip(createListObservable()) { agrs ->
            DataZip(agrs[0] as Int, agrs[1] as Int, agrs[2] as Int)
        }.subscribe(object : Observer<DataZip> {
            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: DataZip) {
                Log.e(
                    "LIST ZIP",
                    "onComplete: " + t.numberOne + " " + t.numberTwo + " " + t.numberThree,
                )
            }

            override fun onError(e: Throwable) {
            }

            override fun onComplete() {
            }

        })
    }

    private fun operatorMerge() {
        Observable.merge(createObservable(1), createObservable(2), createObservable(3))
            .subscribe(object : Observer<Int> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: Int) {
                    Log.e("MERGE", "onNext: $t")
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }

            })
    }

    private fun mergeList() {
        Observable.merge(createListObservable())
            .subscribe(object : Observer<Any> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: Any) {
                    Log.e("List Merge", "onNext: $t")
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }

            })
    }

    ///--------------concat()----------------//
    private fun getDataServer(): Observable<List<Int>> {
        val list: MutableList<Int> = mutableListOf()
        list.add(3)
        list.add(4)
        list.add(5)
        list.add(6)
        return Observable.just(list)
    }

    private fun getDataLocal(): Observable<List<Int>> {
        val data: MutableList<Int> = ArrayList()
        data.add(3)
        data.add(4)
        data.add(5)
        return Observable.just(data)
    }

    private fun demoConcat() {
        val list = mutableListOf<Int>()
        Observable.concat(Observable.just(getDataServer(), getDataLocal()))
            .subscribe(object : Observer<List<Int>> {
                override fun onSubscribe(d: Disposable) {
                    Log.e("CONCAT", "onSubscribe: OK___________")
                }

                override fun onNext(t: List<Int>) {
                    list.addAll(t)
                }

                override fun onError(e: Throwable) {
                    Log.e("CONCAT", "onSubscribe: OK___________")

                }

                override fun onComplete() {
                    Log.e("CONCAT", "onSubscribe: OK___________$list")

                }

            })
    }

    private fun demoConcatMap() {
        val item = mutableListOf<String>("1a", "2s", "3d", "4f", "5f", "6s", "6a", "7", "8")
        val scheduler = TestScheduler()
        Observable.fromArray(item).concatMap {
            return@concatMap Observable.just(item + "X").delay(5, TimeUnit.SECONDS)
                .doOnNext {
                    Log.e("CONCATMAP", "demoConcatMap: $item")
                }
        }
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                Log.e("CONCATMAP NEXT", "demoConcatMap: $it")
            }.doOnSubscribe {
                Log.e("TAG", "demoConcatMap: ")
            }.doOnComplete {
                Log.e("TAG", "demoConcatMap: ")
            }
            .subscribe()
    }

    private fun demoConcatMap2() {
        val item = mutableListOf<Int>(1, 2, 3, 4, 5, 6, 6, 7, 8)
        val scheduler = TestScheduler()
        Observable.fromArray(item).concatMap {
            Observable.just(it + "X").delay(5, TimeUnit.SECONDS)
                .doOnNext { Log.e("CONCATMAP", "demoConcatMap: $it") }
        }.doOnNext {
            Log.e("CONCATMAP NEXT", "demoConcatMap: $it")
        }.subscribe()
    }


}