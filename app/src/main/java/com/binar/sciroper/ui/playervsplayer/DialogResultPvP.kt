package com.binar.sciroper.ui.playervsplayer

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.binar.sciroper.databinding.FragmentDialogResultBinding

class DialogResultPvP : DialogFragment() {

    private var dialogView: DialogViewPvP? = null
    private lateinit var binding: FragmentDialogResultBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentDialogResultBinding.inflate(LayoutInflater.from(context))
        val result = arguments?.getString(RESULT)
        val resultLottie = arguments?.getInt(RESULT_LOTTIE)
        binding.tvResult.text = result
        if (resultLottie != null) {
            binding.lootieResult.setAnimation(resultLottie)
        }

        binding.btnMainLagi.setOnClickListener {
            dismiss()
            dialogView?.reset(android.R.color.transparent)

        }

        binding.btnMenu.setOnClickListener {
            activity?.finish()
        }
        return AlertDialog.Builder(requireActivity()).setView(binding.root).create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dialogView = context as DialogViewPvP
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object{
        const val RESULT = "result"
        const val RESULT_LOTTIE = "result_lottie"
        const val TAG = "dialog_result"
    }
}