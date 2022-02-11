package com.binar.sciroper.ui.fragments.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.binar.sciroper.databinding.FragmentRegisterConfirmationBinding

class RegisterConfirmationFragment : Fragment() {

    private var _binding: FragmentRegisterConfirmationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterConfirmationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener {
            val action =
                RegisterConfirmationFragmentDirections.actionRegisterConfirmationFragmentToMenuFragment()
            findNavController().navigate(action)
        }
    }
}