package com.binar.sciroper.ui.fragments.vs_player

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.binar.sciroper.databinding.DialogUpLvBinding


class DialogLvUp(private val lv: Int) : DialogFragment() {
    private lateinit var binding: DialogUpLvBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = DialogUpLvBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvLv.text = lv.toString()
        binding.btnOk.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        const val DIALOG_LVUP = "dialog_update"
    }
}