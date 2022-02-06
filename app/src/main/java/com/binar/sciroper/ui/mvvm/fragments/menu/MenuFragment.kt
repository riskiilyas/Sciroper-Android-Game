package com.binar.sciroper.ui.mvvm.fragments.menu

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binar.sciroper.R
import com.binar.sciroper.databinding.FragmentMenuBinding
import com.binar.sciroper.ui.how_to_play.HowToPlay
import com.binar.sciroper.ui.leaderboard.LeaderBoardActivity
import com.binar.sciroper.ui.menu.DialogExit
import com.binar.sciroper.ui.menugameplay.MenuGamePlayActivity
import com.binar.sciroper.util.App
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.FirebaseDatabaseKtxRegistrar
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MenuFragment : Fragment() {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private val menuVm: MenuVm by viewModels {
        MenuVmFactory(App.appDb.getUserDao())
    }

    private lateinit var database: FirebaseDatabase


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
        }

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("User")

        myRef.setValue(
            menuVm.user.value
        )


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

    fun navToShop() {
        val action = MenuFragmentDirections.actionMenuFragmentToShopFragment()
        findNavController().navigate(action)
    }

    fun navToAchievement() {
        val action = MenuFragmentDirections.actionMenuFragmentToAchievementFragment()
        findNavController().navigate(action)
    }

    fun navToLeaderBoard(){
        val action = MenuFragmentDirections.actionMenuFragmentToLeaderboardFragment()
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