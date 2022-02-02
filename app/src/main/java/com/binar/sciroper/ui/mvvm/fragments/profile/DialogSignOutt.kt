package com.binar.sciroper.ui.mvvm.fragments.profile

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.databinding.FragmentDialogSignOutBinding

class DialogSignOutt : DialogFragment() {
    private var _binding: FragmentDialogSignOutBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentDialogSignOutBinding.inflate(LayoutInflater.from(context))

        binding.btnYes.setOnClickListener {
            AppSharedPreference.isLogin = false
            val action = ProfileFragmentDirections.actionProfileFragmentToLogInFragment()
            findNavController().navigate(action)
        }

        binding.btnNo.setOnClickListener {
            dismiss()
        }


        return AlertDialog.Builder(requireActivity()).setView(binding.root).create()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        const val DIALOG_SIGNOUTT = "dialog_update"
    }
}