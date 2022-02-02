package com.binar.sciroper.ui.mvvm.fragments.landing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.binar.sciroper.databinding.FragmentNewViewPagerBinding

class NewViewPagerFragment : Fragment() {

    private var _binding: FragmentNewViewPagerBinding? = null
    private val binding get() = _binding!!

    private lateinit var fragmentList: List<Fragment>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewViewPagerBinding.inflate(inflater, container, false)

        fragmentList = arrayListOf(
            LandingPage1Fragment(), LandingPage2Fragment()
        )

        val adapter =
            ViewPagerAdapter(
                fragmentList as ArrayList<Fragment>,
                childFragmentManager,
                lifecycle
            )

        binding.viewpager.adapter = adapter

        val wormDotsIndicator = binding.wormDotsIndicator
        wormDotsIndicator.setViewPager2(binding.viewpager)

        return binding.root
    }
}