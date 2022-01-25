package com.binar.sciroper.ui.profile


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.binar.sciroper.data.local.AppSharedPreference.isLogin
import com.binar.sciroper.databinding.FragmentDialogSignOutBinding


class DialogSignOut : DialogFragment() {
    private lateinit var binding: FragmentDialogSignOutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = FragmentDialogSignOutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnYes.setOnClickListener {
            isLogin = false
            //todo bikin intent keloginActivity
//            Intent(activity,LoginActivity::class.java).also {
//                startActivity(it)
//            }
        }

        binding.btnNo.setOnClickListener {
            dismiss()
        }
    }
    companion object{
        const val DIALOG_SIGNOUT = "dialog_update"
    }
}