package com.binar.sciroper.ui.fragments.landing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.binar.sciroper.databinding.FragmentLandingPage2Binding

class LandingPage2Fragment : Fragment() {

    private var _binding: FragmentLandingPage2Binding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLandingPage2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            landingPage2 = this@LandingPage2Fragment
        }
    }

    fun goToLogIn() {
        val action = NewViewPagerFragmentDirections.actionNewViewPagerFragmentToLogInFragment()
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}