package com.example.rxjavarxandroidtotoria.demo_retrofit_rxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rxjavarxandroidtotoria.R
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response

class CallApiRetrofitWithRxJavaActivity : AppCompatActivity() {
    private val btnCall: Button by lazy { findViewById<Button>(R.id.btnCall) }
    private val rcViewUser: RecyclerView by lazy { findViewById<RecyclerView>(R.id.rcViewUser) }
    private lateinit var loadingDialog: LoadingDialog

    private lateinit var userAdapter: UserAdapter
    private var mListUser = mutableListOf<User>()
    private lateinit var mDisposable: Disposable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_api_retrofit_with_rx_java)
        loadingDialog = LoadingDialog(this)
        userAdapter = UserAdapter()
        rcViewUser.layoutManager = LinearLayoutManager(this)
        val dividerItem = DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)
        rcViewUser.addItemDecoration(dividerItem)

        btnCall.setOnClickListener {
            fetchData()
        }
    }

    private fun fetchData() {
        val call = RetrofitClient.api.getAllUser().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        call.subscribe(object : Observer<List<User>> {
            override fun onSubscribe(d: Disposable) {
                mDisposable = d
                loadingDialog.startLoadingDialog()
            }

            override fun onNext(t: List<User>) {
                mListUser = t as MutableList<User>
            }

            override fun onError(e: Throwable) {
                Toast.makeText(this@CallApiRetrofitWithRxJavaActivity, "$e", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onComplete() {
                Toast.makeText(
                    this@CallApiRetrofitWithRxJavaActivity,
                    "Call api success",
                    Toast.LENGTH_SHORT
                ).show()
                userAdapter.mListUser = mListUser
                rcViewUser.adapter = userAdapter
                loadingDialog.dismissDialog()
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        mDisposable.dispose()
    }

}