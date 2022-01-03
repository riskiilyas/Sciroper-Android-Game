package com.binar.rpschallengechapter5.ui.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.binar.rpschallengechapter5.R
import com.binar.rpschallengechapter5.controller.CallBackFragment

class DialogResult : DialogFragment() {

    private var callBackFragment: CallBackFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.fragment_dialog_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnMainLagi = view.findViewById<Button>(R.id.btn_main_lagi)
        val btnMenu = view.findViewById<Button>(R.id.btn_menu)
        val tvresult = view.findViewById<TextView>(R.id.tv_result)

        if (arguments != null) {
            val hasil = arguments?.getString("hasil")
            tvresult.text = hasil

        }

        btnMainLagi.setOnClickListener {
            dismiss()
            callBackFragment?.reset(android.R.color.transparent)

        }

        btnMenu.setOnClickListener {
            activity?.finish()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            callBackFragment = context as CallBackFragment
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}