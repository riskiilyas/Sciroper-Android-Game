package com.binar.sciroper.binding_adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.binar.sciroper.R

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