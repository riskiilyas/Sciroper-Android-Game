package com.binar.sciroper.ui.fragments.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.binar.sciroper.R
import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.util.BGMusic


class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (AppSharedPreference.isMusicPlay){
            BGMusic.pausePlay()
        }
        if(AppSharedPreference.isLogin!!){
            Handler(Looper.getMainLooper()).postDelayed({
                lifecycleScope.launchWhenResumed {
                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToMenuFragment())
                    if (AppSharedPreference.isMusicPlay){
                        BGMusic.playMusic(requireContext())
                    }
                }
            }, 3000)
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                lifecycleScope.launchWhenResumed {
                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToNewViewPagerFragment())
                    if (AppSharedPreference.isMusicPlay){
                        BGMusic.pausePlay()
                    }
                }
            }, 3000)
        }
        if (AppSharedPreference.isDarkMode!!) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

}