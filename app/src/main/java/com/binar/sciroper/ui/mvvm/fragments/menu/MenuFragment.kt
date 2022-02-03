package com.binar.sciroper.ui.mvvm.fragments.menu

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binar.sciroper.databinding.FragmentMenuBinding
import com.binar.sciroper.ui.how_to_play.HowToPlay
import com.binar.sciroper.ui.leaderboard.LeaderBoardActivity
import com.binar.sciroper.ui.menu.DialogExit
import com.binar.sciroper.ui.menugameplay.MenuGamePlayActivity
import com.binar.sciroper.util.App

class MenuFragment : Fragment() {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private val menuVm: MenuVm by viewModels {
        MenuVmFactory(App.appDb.getUserDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            vm = menuVm
            lifecycleOwner = viewLifecycleOwner
            menuFragment = this@MenuFragment
            btnSetting.setOnClickListener { navToSetting() }
            btnTutorial.setOnClickListener { moveTo(HowToPlay::class.java) }
            btnPlay.setOnClickListener { moveTo(MenuGamePlayActivity::class.java) }
            btnLeaderBoard.setOnClickListener { moveTo(LeaderBoardActivity::class.java) }
        }

        menuVm.user.observe(viewLifecycleOwner) {
            binding.userImg.setImageResource(it.avatarId)
        }
    }

    fun navToSetting() {
        val action = MenuFragmentDirections.actionMenuFragmentToSettingFragment()
        findNavController().navigate(action)
    }

    fun navToPlay() {
        val action = MenuFragmentDirections.actionMenuFragmentToMenuGamePlayFragment()
        findNavController().navigate(action)
    }

    fun navToHtp() {
        val action = MenuFragmentDirections.actionMenuFragmentToHTPFragment()
        findNavController().navigate(action)
    }

    fun createDialog() {
        val dialogFragment = DialogExit()
        dialogFragment.isCancelable = false
        dialogFragment.show(childFragmentManager, "custom_dialog")
    }

    private fun <T> moveTo(target: Class<T>) {
        val intent = Intent(activity, target)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}