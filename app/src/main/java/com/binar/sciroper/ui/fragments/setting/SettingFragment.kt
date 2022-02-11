package com.binar.sciroper.ui.fragments.setting

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binar.sciroper.R
import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.databinding.FragmentSettingBinding
import com.binar.sciroper.util.App


class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private val settingVm: SettingVm by viewModels {
        SettingVmFactory(App.appDb.getUserDao(), AppSharedPreference)
    }

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.statusBarColor = R.color.btnLight
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            vm = settingVm
            lifecycleOwner = viewLifecycleOwner
            settingFragment = this@SettingFragment
        }

        binding.switchNotif.isChecked = AppSharedPreference.isNotif!!
        settingVm.isChecked.observe(viewLifecycleOwner) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                settingVm.setTheme(it)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                settingVm.setTheme(it)
            }
        }

        settingVm.isCheckedMusic.observe(viewLifecycleOwner) {
            if (it) {
                playMusic(requireContext())
                settingVm.setMusic(it)
            } else {
                pausePlay()
                settingVm.setMusic(it)
            }
        }

        settingVm.isCheckedNotif.observe(viewLifecycleOwner) {
            if (it) {

                Log.d("banana", "if: $it")
                settingVm.setNotif(it)
                Log.d("banana", "setnotif: $it")
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
        val action = SettingFragmentDirections.actionSettingFragmentToProfileFragment()
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}