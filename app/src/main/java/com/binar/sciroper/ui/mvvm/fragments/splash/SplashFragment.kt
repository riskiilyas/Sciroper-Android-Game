package com.binar.sciroper.ui.mvvm.fragments.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import com.binar.sciroper.R
import com.binar.sciroper.data.local.AppSharedPreference

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val delaySplashScreen = 2000L

        if(AppSharedPreference.isLogin!!){
            Handler(Looper.getMainLooper()).postDelayed({
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToMenuFragment())
            }, 2000)
        }else{
            Handler(Looper.getMainLooper()).postDelayed({
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToNewViewPagerFragment())
            }, 2000)
        }

//        Handler(Looper.getMainLooper()).postDelayed({
//            if (AppSharedPreference.isLogin == true) {
//                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToViewPagerFragment2())
//            } else {
//                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToViewPagerFragment2())
//            }
////            finish()
//        }, delaySplashScreen)
//
//        if (AppSharedPreference.isDarkMode!!) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//        } else {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//        }

        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

}