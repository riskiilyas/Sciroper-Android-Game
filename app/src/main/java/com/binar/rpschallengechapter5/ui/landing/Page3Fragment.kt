package com.binar.rpschallengechapter5.ui.landing

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import com.binar.rpschallengechapter5.R
import com.binar.rpschallengechapter5.databinding.FragmentPage3Binding
import com.binar.rpschallengechapter5.ui.menu.MenuActivity
import com.bumptech.glide.Glide

class Page3Fragment : Fragment() {

    private lateinit var binding: FragmentPage3Binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPage3Binding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(view)
            .load(getString(R.string.url_landing_page3))
            .into(binding.ivThirdPage)

        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.btnNext.isVisible = s.toString().trim().isNotEmpty()
            }
        })
        binding.btnNext.setOnClickListener {
            if (binding.etName.text.isNotEmpty()) {

                val name = binding.etName.text.toString()

                val intent = Intent(activity, MenuActivity::class.java)
                intent.putExtra("name", name)
                startActivity(intent)
            }
        }
    }
}