package com.binar.sciroper.ui.landing

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.binar.sciroper.databinding.FragmentPage2Binding
import com.binar.sciroper.ui.login.LogInActivity
import com.binar.sciroper.util.goto


class Page2Fragment : Fragment() {
    private var _binding: FragmentPage2Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPage2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener {
            (activity as LandingPageActivity).goto(LogInActivity::class.java)
        }

    }
}
