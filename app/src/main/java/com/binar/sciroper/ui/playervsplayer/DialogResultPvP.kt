package com.binar.sciroper.ui.playervsplayer

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.binar.sciroper.databinding.FragmentDialogResultBinding

class DialogResultPvP : DialogFragment() {

    private var dialogView: DialogViewPvP? = null
    private lateinit var binding: FragmentDialogResultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(false)
        binding = FragmentDialogResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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