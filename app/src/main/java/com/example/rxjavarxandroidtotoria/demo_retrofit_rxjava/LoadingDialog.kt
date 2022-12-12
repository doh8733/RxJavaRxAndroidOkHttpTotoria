package com.example.rxjavarxandroidtotoria.demo_retrofit_rxjava

import android.app.AlertDialog
import android.app.Application
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import com.example.rxjavarxandroidtotoria.R

class LoadingDialog(var context: Context) {
    private lateinit var  builder : Dialog

     fun startLoadingDialog() {
        builder = Dialog(context)
        builder.setContentView(R.layout.custom_progress)
        builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        builder.setCancelable(true)
        builder.show()
    }
    fun dismissDialog(){
        builder.dismiss()
    }
}