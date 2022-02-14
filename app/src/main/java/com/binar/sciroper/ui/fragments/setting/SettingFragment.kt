package com.binar.sciroper.ui.fragments.setting

import android.annotation.SuppressLint
import android.nfc.Tag
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
import com.binar.sciroper.util.*


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

        settingVm.isChecked.observe(viewLifecycleOwner) {

//            if(settingVm.isCheckedMusic.value == true){
//                pausePlay()
//            }
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

        settingVm.isCheckedMusic.observe(viewLifecycleOwner) {
            if (it) {
                App.context.get()?.let { it1 -> playMusic(it1) }
                settingVm.setMusic(it)
            } else {
                resetMusic()
                settingVm.setMusic(it)
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
        if (settingVm.isCheckedMusic.value!!) {
            pausePlay()
        }
    }
}