package com.binar.sciroper.ui.fragments.setting


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.databinding.FragmentSettingBinding
import com.binar.sciroper.util.BGMusic.isPlay
import com.binar.sciroper.util.BGMusic.playMusic
import com.binar.sciroper.util.BGMusic.stopMusic
import com.binar.sciroper.util.checkNetworkAvailable


class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private val settingVm: SettingVm by viewModels {
        SettingVmFactory()
    }
    private var isOnline = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkNetworkAvailable { isOnline = it }

        binding.apply {
            vm = settingVm
            lifecycleOwner = viewLifecycleOwner
            settingFragment = this@SettingFragment
        }


        settingVm.isChecked.observe(viewLifecycleOwner) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                settingVm.setTheme(it)
                Log.e("Tag", "true")
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                settingVm.setTheme(it)
                Log.e("Tag", "false")
            }
        }

        binding.switchMusic.isChecked = AppSharedPreference.isMusicPlay
        binding.switchMusic.setOnCheckedChangeListener { _, isChecked ->
            settingVm.setMusic(isChecked)
            if (isChecked) {
                playMusic()
                Toast.makeText(requireContext(), "Music ON", Toast.LENGTH_SHORT).show()
                binding.switchMusic.isChecked = true
            } else if (!isChecked && isPlay()) {
                stopMusic()
                binding.switchMusic.isChecked = false
            }
        }
        settingVm.isCheckedNotif.observe(viewLifecycleOwner) {
            if (it) {
                settingVm.setNotif(it)
            } else {
                settingVm.setNotif(it)
            }
        }
    }

    fun navToMenu() {
        val action = SettingFragmentDirections.actionSettingFragmentToMenuFragment()
        findNavController().navigate(action)
    }

    fun navToProfile() {
        if (!isOnline) {
            Toast.makeText(requireContext(), "No internet Connection!", Toast.LENGTH_SHORT).show()
            return
        }
        val action = SettingFragmentDirections.actionSettingFragmentToProfileFragment()
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}