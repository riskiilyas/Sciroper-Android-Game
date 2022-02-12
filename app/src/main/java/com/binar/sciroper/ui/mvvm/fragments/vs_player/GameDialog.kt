package com.binar.sciroper.ui.mvvm.fragments.vs_player

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.binar.sciroper.R
import com.binar.sciroper.databinding.GameDialogBinding
import com.binar.sciroper.util.*

class GameDialog : DialogFragment() {

    private var _binding: GameDialogBinding? = null
    private val binding get() = _binding!!
    private val vsPlayerVm: VsPlayerVm by activityViewModels {
        VsPlayerVmFactory(App.appDb.getUserDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = GameDialogBinding.inflate(LayoutInflater.from(context))

        binding.btnMainLagi.setOnClickListener {
            vsPlayerVm.reset()
            dismiss()
            findNavController().navigate(VsPlayerFragmentDirections.actionVsPlayerFragmentToVsPlayerFragment())
        }

        binding.btnMenu.setOnClickListener {
            vsPlayerVm.reset()
            findNavController().navigate(VsPlayerFragmentDirections.actionVsPlayerFragmentToMenuGamePlayFragment())
        }

        setAnimation()

        return AlertDialog.Builder(requireActivity()).setView(binding.root).create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            vm = vsPlayerVm
        }
    }

    private fun setAnimation() {
        when (vsPlayerVm.result) {
            "draw" -> {
                App.context.get()?.let { drawMusic(it) }
                binding.lootieResult.setAnimation(R.raw.result_draw)
                binding.tvResult.text = "Draw"
            }
            else -> {
                App.context.get()?.let { winMusic(it) }
                binding.lootieResult.setAnimation(R.raw.result_win)
                binding.tvResult.text = "${vsPlayerVm.winner} ${vsPlayerVm.result}"

            }
        }
        Log.i("banana", "${vsPlayerVm.winner} ${vsPlayerVm.result}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}