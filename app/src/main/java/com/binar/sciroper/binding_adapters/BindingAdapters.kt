package com.binar.sciroper.binding_adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.binar.sciroper.R
import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.data.retrofit.HistoryResponse
import com.binar.sciroper.ui.fragments.achievement.HistoryAdapter
import com.binar.sciroper.ui.fragments.leaderboard.UserListAdapter

@BindingAdapter("setImg")
fun setImage(imgView: ImageView, imgId: Int) {
    imgView.setImageResource(imgId)
}

@BindingAdapter(value = ["storedImgId", "imgId"])
fun setAvatarBg(imgView: ImageView, storedImgId: Int, imgId: Int){
    if (storedImgId == imgId){
        imgView.setBackgroundResource(R.color.navigationColour)
    }else{
        imgView.setBackgroundResource(android.R.color.transparent)
    }
}

@BindingAdapter("setAvatar")
fun setAvatar(imgView: ImageView, avatarId: Int){
    imgView.setImageResource(avatarId)
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<User>?) {
    val adapter = recyclerView.adapter as UserListAdapter
    adapter.submitList(data)
}