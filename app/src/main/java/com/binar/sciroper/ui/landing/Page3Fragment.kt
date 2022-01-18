package com.binar.sciroper.ui.landing

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
import com.binar.sciroper.R
import com.binar.sciroper.databinding.FragmentPage3Binding
import com.binar.sciroper.ui.playgame.PlayActivity
import com.bumptech.glide.Glide

class Page3Fragment : Fragment() {
    private var _binding: FragmentPage3Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPage3Binding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

                val intent = Intent(activity, PlayActivity::class.java)
                intent.putExtra("name", name)
                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}