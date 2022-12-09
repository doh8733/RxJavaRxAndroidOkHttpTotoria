package com.example.rxjavarxandroidtotoria.demo_retrofit_rxjava

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.rxjavarxandroidtotoria.R


class UserAdapter : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    var mListUser = mutableListOf<User>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvId: TextView by lazy { view.findViewById<TextView>(R.id.tvId) }
        private val tvName: TextView by lazy { view.findViewById<TextView>(R.id.tvName) }

        fun onBindViews(user: User) {
            tvId.text = user.id.toString()
            tvName.text = user.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindViews(mListUser[position])
    }

    override fun getItemCount(): Int {
        return mListUser.size
    }
}