package com.binar.sciroper.ui.fragments.vs_com

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.binar.sciroper.R
import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.databinding.FragmentVsComBinding
import com.binar.sciroper.ui.fragments.vs_player.DialogLvUp
import com.binar.sciroper.ui.fragments.vs_player.GameDialog
import com.binar.sciroper.util.App
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.properties.Delegates


class VsComFragment : Fragment() {

    private var _binding: FragmentVsComBinding? = null
    private val binding get() = _binding!!
    private val vsComVm: VsComVm by viewModels {
        VsComVmFactory(App.appDb.getUserDao())
    }
    private lateinit var user: User

    private lateinit var p1Choices: List<ImageView>
    private lateinit var comChoices: List<ImageView>
    private var currentLevel = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVsComBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user = vsComVm.user
        currentLevel = vsComVm.user.level

        p1Choices = listOf(
            binding.ivPemain1BatuPlayer,
            binding.ivPemain1KertasPlayer,
            binding.ivPemain1GuntingPlayer
        )

        comChoices = listOf(
            binding.ivPemain2BatuCOM,
            binding.ivPemain2KertasCOM,
            binding.ivPemain2GuntingCOM
        )

        p1Choices.forEach { its ->
            its.setOnClickListener {
                it.setBackgroundResource(R.drawable.shape_background)
                showToast("${user.username} Memilih ${it.contentDescription}")
                vsComVm.setPlayerChoice(it.contentDescription.toString())
                freezeState(p1Choices)
                val comChoice = (0..2).random()
                lifecycleScope.launch {
                    delay(800)
                    comChoices.forEach {
                        it.setBackgroundResource(R.drawable.shape_background)
                        delay(500)
                        it.setBackgroundColor(Color.TRANSPARENT)
                    }
                    delay(1000)
                    comChoices[comChoice].setBackgroundResource(R.drawable.shape_background)
                    vsComVm.setComChoice(listOf("Rock", "Paper", "Scissors")[comChoice])
                    vsComVm.gameResult()
                }
            }
        }

        binding.apply {
            vm = vsComVm
            lifecycleOwner = viewLifecycleOwner
            vsComFragment = this@VsComFragment
        }

        binding.apply {
            ivAvatarPlayer.setImageResource(vsComVm.user.avatarId)
        }

        vsComVm.isOver.observe(viewLifecycleOwner) { it ->
            if (it) {
                val dialogFragment = ComDialog(vsComVm)
                dialogFragment.isCancelable = false
                dialogFragment.show(childFragmentManager, "DIALOG_COM")
            }
        }

        vsComVm.isReset.observe(viewLifecycleOwner) {
            if (it) {
                unFreezeState(p1Choices)
                p1Choices.forEach { v -> v.setBackgroundColor(Color.TRANSPARENT) }
                comChoices.forEach { v -> v.setBackgroundColor(Color.TRANSPARENT) }
            }
        }

        vsComVm.userLiveData.observe(viewLifecycleOwner) {
            binding.tvCoin.text = it.coin.toString()

            if (currentLevel < it.level) {
                val dialogSignOut = DialogLvUp(it.level)
                dialogSignOut.show(childFragmentManager, DialogLvUp.DIALOG_LVUP)
                currentLevel = it.level
            }
        }


    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun freezeState(choices: List<ImageView>) {
        choices.forEach {
            it.isEnabled = false
        }
    }

    private fun unFreezeState(choices: List<ImageView>) {
        choices.forEach {
            it.isEnabled = true
        }
    }

    fun navToMenuGamePlay(){
        val action = VsComFragmentDirections.actionVsComFragmentToMenuGamePlayFragment()
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}