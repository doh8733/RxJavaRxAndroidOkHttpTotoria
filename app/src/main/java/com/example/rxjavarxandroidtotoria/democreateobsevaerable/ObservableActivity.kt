package com.example.rxjavarxandroidtotoria.democreateobsevaerable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.rxjavarxandroidtotoria.R
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.schedulers.Schedulers.io
import java.io.Serializable
import java.lang.reflect.Array
import kotlin.math.log

class ObservableActivity : AppCompatActivity() {
    companion object {
        val TAG = javaClass.simpleName
    }

    private lateinit var mDisposable: Disposable
    private var listUser = mutableListOf<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_observable)
        listUser = getalluser()
        //lien ket obsevable vowis observer
        val observable: Observable<Serializable> = createObservableJust() as Observable<Serializable>
        val observer: Observer<User> = getObserver()
        //just
        val observerJust: Observer<Serializable> = createObserverJust()

      //  observable.subscribeOn(io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer)
        observable.subscribeOn(io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observerJust)
        //ngat ket noi
    }

    private fun getObserver(): Observer<User> {
        return object : Observer<User> {
            override fun onSubscribe(d: Disposable) {
                Log.e("DIS", "onSubscribe: ")
                mDisposable = d
                //------------->mỗi khi gọi hàm hoạt đọng bát kỳ thì các hoạt động sẽ phản hồi trong đây

            }

            override fun onNext(t: User) {
                Log.e("next", "onNext:" + t.toString())
                Log.e("Thread Name", "createObservable: ${Thread.currentThread().name}")
                //------------->mỗi khi gọi hàm hoạt đọng bát kỳ thì các hoạt động sẽ phản hồi trong đây

            }

            override fun onError(e: Throwable) {
                Log.e("ERR", "onError: ")
                //------------->mỗi khi gọi hàm hoạt đọng bát kỳ thì các hoạt động sẽ phản hồi trong đây

            }

            override fun onComplete() {
                Log.e("COM", "onComplete: ")
                Log.e("Thread Name", "createObservable: ${Thread.currentThread().name}")

                //------------->mỗi khi gọi hàm hoạt đọng bát kỳ thì các hoạt động sẽ phản hồi trong đây

            }

        }
        //ket noi Observable.just()
    }

    ///--------ket noi obsevaer voiw Observable.just()
    private fun createObserverJust():Observer<Serializable>{
        return object : Observer<Serializable>{
            override fun onSubscribe(d: Disposable) {
                mDisposable = d
                Log.e(TAG, "onSubscribe: ", )
            }

            override fun onNext(t: Serializable) {
                Log.e(TAG, "onNext: ${t.toString()}", )
                val users  = listOf(t)
                //data dang mang
                for (i in users){
                    Log.e("ONNEXT", "laallalal ${i.toString()}", )
                }
                //data dang string
                if(t is String){
                    val str :String = t
                    Log.e("LOG STR", "onNext: $str", )
                }
                //new data dang list
                if (t is List<*>){
                    val list : List<User> = t as List<User>
                    for (i in list){
                        Log.e("LOG LIST", "onNext: $i", )
                    }

                }

            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "onError: ", )
            }

            override fun onComplete() {
                Log.e(TAG, "onComplete: ", )
            }

        }
    }

    //create Observable.create()
    //có thể tự thiết kế họa đọng của user bằng cách gọi đến các hàm:
    // -----> lưu ý hãy thiết kế hoạt động một cÁCH HỢP lý
    private fun createObservable(): Observable<User> {
        return Observable.create(ObservableOnSubscribe { emitter ->
            Log.e("Thread Name", "createObservable: ${Thread.currentThread().name}")
            // kiem tra cong viejc bi loi
            if (listUser.isEmpty()) {
                if (!emitter.isDisposed) {
                    emitter.onError(Exception())
                    //hàm onError()
                }
            }
            for (i in listUser) {
                if (!emitter.isDisposed) {
                    //kiểm tra kết nối giữa observable và obsever
                    emitter.onNext(i)
                    Log.e("Thread Name", "createObservable: ${Thread.currentThread().name}")
                    //hàm onNẽxt sẽ chạy mỗi khi nhận được dữ liêuj mói trong list
                }
            }
            // kiem tra thưc hiẹn xong cong viec
            if (!emitter.isDisposed) {
                emitter.onComplete()
// hàm onComplete() sẽ chạy duy nhất một lần khi hoàn thành tác vụ

            }
        })
    }
    //create Observable.fromArray()
    //sẽ chuyển đổi list hoặc mảng object thành Obsevable và sẽ được sử lý tron onNetx()
//    private fun onCreateObservableArray() : Observable<User{
//        val user = User[]{listUser}
//        return Observable.fromArray(listUser.toString())
//    }


    //----------------------//
    //------>create Observable.just()
    //Chuyển đổi object của một tập hợp object thành Observable và phát ra
    // nó với just() và nếu truyền vào một
    // Aray thì nó sẽ chuyển đổi  thành Observable và phát ra chính Aray đó
    //---------------------//
    private fun createObservableJust(): Observable<Any>{
        var list = mutableListOf<User>()
        list = getalluser()
        var user1 = User(1, "Huy")
        var user2 = User(2, "Huy2")
        var user4 = User(4, "Huy4")
        val srt :String = "Huy"
        var u = arrayOf(user1, user2)
        return Observable.just(u,srt,list)
    }

    private fun getalluser(): MutableList<User> {
        val list = mutableListOf<User>()
        list.add(User(1, "2"))
        list.add(User(3, "2"))
        list.add(User(4, "2"))
        list.add(User(5, "2"))
        return list
    }
    //congviecj can rx thực hiẹn theo cách bất đồng bộ


    override fun onDestroy() {
        super.onDestroy()
        mDisposable.isDisposed

    }
}