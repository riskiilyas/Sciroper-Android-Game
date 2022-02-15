package com.binar.sciroper.ui.fragments.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.databinding.FragmentLogInBinding
import com.binar.sciroper.util.App
import com.binar.sciroper.util.BGMusic
import com.binar.sciroper.util.UiState
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.runBlocking


class LogInFragment : Fragment() {

    private var _binding: FragmentLogInBinding? = null
    private val binding get() = _binding!!
    private val logInVm: LogInVM by viewModels {
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
        if (logInVm.isLoggedIn() == true) findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToMenuFragment())

        if (AppSharedPreference.isMusicPlay){
            BGMusic.pausePlay()
        }

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

        logInVm.uiState().observe(viewLifecycleOwner) { uiState ->
            if (uiState != null) {
                render(uiState)
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

    fun navToSignUp() {
        val action = LogInFragmentDirections.actionLogInFragmentToSignUpFragment()
        findNavController().navigate(action)
    }

    fun makeToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    private fun render(uiState: UiState) {
        when (uiState) {
            is UiState.Loading -> {
                onLoad()
            }
            is UiState.Success -> {
                onSuccess(uiState)
            }
            is UiState.Error -> {
                onError(uiState)
            }
        }
    }

    private fun onLoad() {
        binding.loadingInd.isVisible = true
        binding.btnSignIn.isEnabled = false
    }

    private fun onSuccess(uiState: UiState.Success) = with(binding) {
        binding.loadingInd.isGone = true
        email.text?.clear()
        password.text?.clear()
        findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToMenuFragment())
        if (AppSharedPreference.isMusicPlay){
            BGMusic.playMusic(requireContext())
        }
    }

    private fun onError(uiState: UiState.Error) = with(binding) {
        binding.loadingInd.isGone = true
        btnSignIn.isEnabled = true
        makeToast(uiState.message)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}