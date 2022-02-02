package com.binar.sciroper.ui.profile


import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.NavHost
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.binar.sciroper.R
import com.binar.sciroper.data.local.AppSharedPreference.isLogin
import com.binar.sciroper.databinding.FragmentDialogSignOutBinding
import com.binar.sciroper.ui.login.LogInActivity
import com.binar.sciroper.ui.mvvm.fragments.login.LogInFragment


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
            Intent(activity,LogInActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.btnNo.setOnClickListener {
            dismiss()
        }
    }
    companion object{
        const val DIALOG_SIGNOUT = "dialog_update"
    }
}