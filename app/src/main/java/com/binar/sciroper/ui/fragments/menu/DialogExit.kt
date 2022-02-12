package com.binar.sciroper.ui.fragments.menu

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.binar.sciroper.databinding.DialogExitBinding

class DialogExit : DialogFragment() {
    private var _binding: DialogExitBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        _binding = DialogExitBinding.inflate(LayoutInflater.from(context))

        binding.positiveBtn.setOnClickListener {
            activity?.finishAffinity()
        }

        binding.negativeBtn.setOnClickListener {
            dismiss()
        }

        return AlertDialog.Builder(requireActivity()).setView(binding.root).create()
    }
}