package com.binar.sciroper.ui.fragments.how_to_play

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.databinding.FragmentHTPBinding
import com.binar.sciroper.util.BGMusic

class HTPFragment : Fragment() {

    private var _binding: FragmentHTPBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHTPBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            htpFragment = this@HTPFragment
        }
    }

    fun navToMenu() {
        val action = HTPFragmentDirections.actionHTPFragmentToMenuFragment()
        findNavController().navigate(action)
        if (AppSharedPreference.isMusicPlay) {
            BGMusic.playMusic(requireContext())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}