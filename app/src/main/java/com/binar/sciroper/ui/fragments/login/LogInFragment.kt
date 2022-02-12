package com.binar.sciroper.ui.fragments.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binar.sciroper.databinding.FragmentLogInBinding
import com.binar.sciroper.util.App
import com.google.android.material.textfield.TextInputEditText


class LogInFragment : Fragment() {

    private var _binding: FragmentLogInBinding? = null
    private val binding get() = _binding!!
    private val logInVm: LogInVM by viewModels() {
        LogInVMFactory(App.appDb.getUserDao())
    }

    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var signUpBtn: Button
    private lateinit var signInBtn: Button
    private lateinit var loadingInd: ProgressBar



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLogInBinding.inflate(inflater, container, false)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity?.finishAffinity()
                }
            })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        email = binding.tietEmail
        password = binding.tietPassword
        signUpBtn = binding.btnSignUp
        signInBtn = binding.btnSignIn
        loadingInd = binding.loadingInd

        binding.apply {
            vm = logInVm
            lifecycleOwner = viewLifecycleOwner
            logIn = this@LogInFragment
        }

        logInVm.navToMenu.observe(viewLifecycleOwner) {
            if (it) {
                Log.i("banana", "navToMenu triggered")
                val action = LogInFragmentDirections.actionLogInFragmentToMenuFragment()
                findNavController().navigate(action)
                logInVm.setEndNav()
            }
        }

        logInVm.errorToast.observe(viewLifecycleOwner) {
            if (it) {
                Log.i("Banana", "errorToast triggered")
                Toast.makeText(
                    requireContext(),
                    "email or password is incorrect",
                    Toast.LENGTH_SHORT
                ).show()
                logInVm.setEndToast()
            }
        }

        addTextChangedListenerOnView(email, password, textWatcher = textWatcher)
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val emailText = email.text?.trim().toString()
            val passwordText = password.text?.trim().toString()
            signInBtn.isEnabled =
                (emailText.isNotBlank() && passwordText.isNotBlank())
        }

        override fun afterTextChanged(s: Editable?) {
        }
    }

    private fun addTextChangedListenerOnView(
        vararg views: TextInputEditText,
        textWatcher: TextWatcher
    ) {
        for (view in views) {
            view.addTextChangedListener(textWatcher)
        }
    }

    fun goToSignUp() {
        val action = LogInFragmentDirections.actionLogInFragmentToSignUpFragment()
        findNavController().navigate(action)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}