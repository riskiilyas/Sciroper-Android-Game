package com.binar.sciroper.binding_adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.ui.fragments.leaderboard.UserListAdapter

@BindingAdapter("setImg")
fun setImage(imgView: ImageView, imgId: Int) {
    imgView.setImageResource(imgId)
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<User>?) {
    val adapter = recyclerView.adapter as UserListAdapter
    adapter.submitList(data)
}