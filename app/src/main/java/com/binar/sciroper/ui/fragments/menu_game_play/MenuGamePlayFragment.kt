package com.binar.sciroper.ui.fragments.menu_game_play

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binar.sciroper.databinding.FragmentMenuGamePlayBinding
import com.binar.sciroper.util.App

class MenuGamePlayFragment : Fragment() {

    private var _binding: FragmentMenuGamePlayBinding? = null
    private val binding get() = _binding!!
    private val menuGamePlayVm: MenuGamePlayVm by viewModels {
        MenuGamePlayVmFactory(App.appDb.getUserDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuGamePlayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            vm = menuGamePlayVm
            lifecycleOwner = viewLifecycleOwner
            mgpFragment = this@MenuGamePlayFragment
        }
    }

    fun moveToVsPemain() {
        val action = MenuGamePlayFragmentDirections.actionMenuGamePlayFragmentToVsPlayerFragment()
        findNavController().navigate(action)
    }

    fun moveToVsCpu() {
        val action = MenuGamePlayFragmentDirections.actionMenuGamePlayFragmentToVsComFragment()
        findNavController().navigate(action)
    }

    fun moveToMenu() {
        val action = MenuGamePlayFragmentDirections.actionMenuGamePlayFragmentToMenuFragment()
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}