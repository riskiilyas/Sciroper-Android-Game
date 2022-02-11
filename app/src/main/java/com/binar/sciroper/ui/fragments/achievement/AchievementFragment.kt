package com.binar.sciroper.ui.fragments.achievement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.binar.sciroper.databinding.FragmentAchievementBinding
import com.binar.sciroper.util.App

class AchievementFragment : Fragment() {
    private var _binding: FragmentAchievementBinding? = null
    private val binding get() = _binding!!
    private val achievementVm: AchievementVm by viewModels {
        AchievementVmFactory(App.appDb.getUserDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAchievementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            vm = achievementVm
            lifecycleOwner = viewLifecycleOwner
            achievementFragment = this@AchievementFragment
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}