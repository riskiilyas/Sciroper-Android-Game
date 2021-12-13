package com.binar.rpschallengechapter5.ui.landing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.binar.rpschallengechapter5.R
import com.bumptech.glide.Glide

class Page1Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_page1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ivFirstPage = view.findViewById<ImageView>(R.id.iv_first_page)

        Glide.with(view)
            .load(getString(R.string.url_landing_page1))
            .into(ivFirstPage)
    }
}