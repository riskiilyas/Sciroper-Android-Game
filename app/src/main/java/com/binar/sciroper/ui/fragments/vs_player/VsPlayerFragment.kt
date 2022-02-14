package com.binar.sciroper.ui.fragments.vs_player

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.binar.sciroper.R
import com.binar.sciroper.databinding.FragmentVsPlayerBinding
import com.binar.sciroper.ui.fragments.vs_player.DialogLvUp.Companion.DIALOG_LVUP
import com.binar.sciroper.ui.fragments.vs_player.GameDialog.Companion.DIALOG_GAME
import com.binar.sciroper.util.App

class VsPlayerFragment : Fragment() {

    private var _binding: FragmentVsPlayerBinding? = null
    private val binding get() = _binding!!
    private val vsPlayerVm: VsPlayerVm by activityViewModels {
        VsPlayerVmFactory(App.appDb.getUserDao())
    }
    private lateinit var btnPemainSatu: List<ImageView>
    private lateinit var btnPemainDua: List<ImageView>
    private lateinit var allBtn: MutableList<ImageView>
    private var currentLevel = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVsPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentLevel = vsPlayerVm.user.level
        btnPemainSatu = listOf(
            binding.ivPemain1Batu,
            binding.ivPemain1Kertas,
            binding.ivPemain1Gunting,
        )

        btnPemainDua = listOf(
            binding.ivPemain2Batu,
            binding.ivPemain2Kertas,
            binding.ivPemain2Gunting,
        )

        allBtn = mutableListOf()

        allBtn.addAll(btnPemainSatu)
        allBtn.addAll(btnPemainDua)

        binding.apply {
            vm = vsPlayerVm
            vsPlayerFragment = this@VsPlayerFragment
            ivAvatar.setImageResource(vsPlayerVm.user.avatarId)
            pemain1.text = vsPlayerVm.user.username
            ivRefresh.setOnClickListener { restartGame() }
        }

        playGame()


        vsPlayerVm.userLiveData.observe(viewLifecycleOwner) {
            binding.tvCoin.text = it.coin.toString()

            if (currentLevel < it.level) {
                val dialogSignOut = DialogLvUp(it.level)
                dialogSignOut.show(childFragmentManager, DIALOG_LVUP)
                currentLevel = it.level
            }
        }

    }

    private fun createToast(player: String, choice: String) {
        Toast.makeText(
            requireContext(),
            "$player Memilih $choice",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun playerMove() {
        btnPemainSatu.forEach {
            it.setOnClickListener { choice ->
                vsPlayerVm.setPlayerChoice(choice.contentDescription.toString())
                vsPlayerVm.setPlayerSelectedId(choice.id)
                choice.setBackgroundResource(R.drawable.shape_background)
                freezeState(btnPemainSatu)
                vsPlayerVm.gameResult()
                createDialog()
                createToast(vsPlayerVm.user.username, vsPlayerVm.playerChoice)
            }
        }
    }

    private fun opponentMove() {
        btnPemainDua.forEach {
            it.setOnClickListener { choice ->
                vsPlayerVm.setOpponentChoice(choice.contentDescription.toString())
                vsPlayerVm.setOpponentSelectedId(choice.id)
                choice.setBackgroundResource(R.drawable.shape_background)
                freezeState(btnPemainDua)
                vsPlayerVm.gameResult()
                createDialog()
                createToast(vsPlayerVm.opponent, vsPlayerVm.opponentChoice)
            }
        }
    }

    private fun playGame() {
        playerMove()
        opponentMove()
    }

    private fun freezeState(choices: List<ImageView>) {
        vsPlayerVm.setStatus(false)
        choices.forEach {
            it.isEnabled = vsPlayerVm.status
        }
    }

    private fun createDialog() {
        if (vsPlayerVm.result != "") {
            val dialogFragment = GameDialog(vsPlayerVm)
            dialogFragment.isCancelable = false
            dialogFragment.show(childFragmentManager, DIALOG_GAME)
        }
    }

    private fun restartGame() {
        vsPlayerVm.reset()
        allBtn.forEach {
            it.isEnabled = vsPlayerVm.status
            it.setBackgroundResource(0)
        }
    }

    fun navToMenuGamePlay(){
        val action = VsPlayerFragmentDirections.actionVsPlayerFragmentToMenuGamePlayFragment()
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}