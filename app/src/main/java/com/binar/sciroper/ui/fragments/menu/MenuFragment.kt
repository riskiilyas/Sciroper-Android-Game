package com.binar.sciroper.ui.fragments.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.databinding.FragmentMenuBinding
import com.binar.sciroper.util.App
import com.binar.sciroper.util.BGMusic
import com.binar.sciroper.util.checkNetworkAvailable
import com.google.android.material.snackbar.Snackbar

class MenuFragment : Fragment() {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private var isOnline = false
    private val menuVm: MenuVm by viewModels {
        MenuVmFactory(App.appDb.getUserDao())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            vm = menuVm
            lifecycleOwner = viewLifecycleOwner
            menuFragment = this@MenuFragment
        }


        menuVm.user.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.tvCoinMenu.text = it.coin.toString()
                binding.tvUsername.text = it.username
                binding.tvUserLevel.text = it.level.toString()
                binding.userImg.setImageResource(it.avatarId)
            }
        }

        checkNetworkAvailable {
            isOnline = it
            if (isVisible) {
                if (it) {
                    Snackbar.make(requireView(), "You are online!", Snackbar.LENGTH_SHORT).show()
                } else {
                    Snackbar.make(requireView(), "Currently offline!", Snackbar.LENGTH_SHORT).show()
                }
            }
            if (it) menuVm.updateUser(menuVm.user)
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
        if (AppSharedPreference.isMusicPlay){
            BGMusic.pausePlay()
        }
    }

    fun navToShop() {
        val action = MenuFragmentDirections.actionMenuFragmentToShopFragment()
        findNavController().navigate(action)
    }

    fun navToAchievement() {
        val action = MenuFragmentDirections.actionMenuFragmentToAchievementFragment()
        findNavController().navigate(action)
    }

    fun navToLeaderBoard(){
        if (!isOnline) {
            Toast.makeText(requireContext(), "Connect to Internet first!", Toast.LENGTH_SHORT).show()
        } else {
            val action = MenuFragmentDirections.actionMenuFragmentToLeaderboardFragment()
            findNavController().navigate(action)
        }
    }

    fun createDialog() {
        val dialogFragment = DialogExit()
        dialogFragment.isCancelable = false
        dialogFragment.show(childFragmentManager, "custom_dialog")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}