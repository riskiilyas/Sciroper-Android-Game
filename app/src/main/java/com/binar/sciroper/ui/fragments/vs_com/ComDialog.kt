package com.binar.sciroper.ui.fragments.vs_com

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.binar.sciroper.R
import com.binar.sciroper.databinding.ComLayoutBinding
import com.binar.sciroper.databinding.GameDialogBinding
import com.binar.sciroper.ui.fragments.vs_player.VsPlayerFragmentDirections
import com.binar.sciroper.util.App

class ComDialog: DialogFragment() {
    private var _binding: ComLayoutBinding? = null
    private val binding get() = _binding!!
    private val vsComVm: VsComVm by activityViewModels {
        VsComVmFactory(App.appDb.getUserDao())
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
        _binding = ComLayoutBinding.inflate(LayoutInflater.from(context))

        binding.btnMainLagiC.setOnClickListener {
            dismiss()
            vsComVm.reset()
        }

        binding.btnMenuC.setOnClickListener {
            findNavController().navigate(VsComFragmentDirections.actionVsComFragmentToMenuGamePlayFragment())
        }

        setAnimation()

        return AlertDialog.Builder(requireActivity()).setView(binding.root).create()
    }

    @SuppressLint("SetTextI18n")
    private fun setAnimation() {
        when (vsComVm.result.value) {
            "draw" -> {
                binding.lootieResultC.setAnimation(R.raw.result_draw)
                binding.tvResultC.text = "Draw"
            }
            else -> {
                binding.lootieResultC.setAnimation(R.raw.result_win)
                binding.tvResultC.text = "${vsComVm.winner} ${vsComVm.result}"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}