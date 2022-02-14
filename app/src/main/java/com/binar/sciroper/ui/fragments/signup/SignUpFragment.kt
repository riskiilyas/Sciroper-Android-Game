package com.binar.sciroper.ui.fragments.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binar.sciroper.R
import com.binar.sciroper.data.db.user.AuthDetails
import com.binar.sciroper.databinding.FragmentSignUpBinding
import com.binar.sciroper.util.App
import com.binar.sciroper.util.AvatarHelper
import com.binar.sciroper.util.UiState
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val signUpVm: SignUpVm by viewModels {
        SignUpVmFactory(App.appDb.getUserDao())
    }

    private lateinit var userName: TextInputEditText
    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var passwordLayout: TextInputLayout
    private lateinit var rePassword: TextInputEditText
    private lateinit var signUpBtn: Button
    private lateinit var loadingInd: ProgressBar
    private lateinit var avatarList: List<AppCompatImageView>
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        userName = binding.tietUsername
        email = binding.tietEmail
        password = binding.tietPassword
        passwordLayout = binding.tilPassword
        rePassword = binding.tietRepassword
        signUpBtn = binding.btnSignUp
        loadingInd = binding.loadingInd
        avatarList = listOf(binding.avatar1, binding.avatar2, binding.avatar3, binding.avatar4)


        binding.apply {
            vm = signUpVm
            lifecycleOwner = viewLifecycleOwner
        }

        signUpVm.uiState().observe(viewLifecycleOwner) { uiState ->
            if (uiState != null) {
                render(uiState)
            }
        }

        binding.btnSignUp.setOnClickListener {
            val registerDetails = AuthDetails(
                username = userName.text?.trim().toString(),
                email = email.text?.trim().toString(),
                password = password.text?.trim().toString()
            )
            signUpVm.signUp(registerDetails)
        }

        signUpVm.usernameLength.observe(viewLifecycleOwner) {
            if (it) {
                binding.tilUsername.error = "username must have more than 5 letters"
            } else
                binding.tilUsername.error = null
        }

        signUpVm.onInvalidEmail.observe(viewLifecycleOwner) {
            if (it) {
                binding.tilEmail.error = "invalid email format"
            } else
                binding.tilEmail.error = null
        }

        signUpVm.onPasswordError.observe(viewLifecycleOwner) {
            if (it) {
                binding.tilPassword.error = "password does not match"
                binding.tilRepassword.error = "password does not match"
            } else {
                binding.tilPassword.error = null
                binding.tilRepassword.error = null
            }
        }

        onSelectedAvatar(avatarList)

        addTextChangedListenerOnView(
            userName,
            email,
            password,
            rePassword,
            textWatcher = textWatcher
        )

    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val userNameText = userName.text?.trim().toString()
            val emailText = email.text?.trim().toString()
            val passwordText = password.text?.trim().toString()
            val rePasswordText = rePassword.text?.trim().toString()
            signUpBtn.isEnabled =
                (userNameText.isNotBlank() && emailText.isNotBlank() && passwordText.isNotBlank() && rePasswordText.isNotBlank())

            password.error = null
            rePassword.error = null
        }

        override fun afterTextChanged(s: Editable?) {
        }
    }

    // function to add text watcher on multiple text input edit text view
    private fun addTextChangedListenerOnView(
        vararg views: TextInputEditText,
        textWatcher: TextWatcher
    ) {
        for (view in views) {
            view.addTextChangedListener(textWatcher)
        }
    }

    private fun onSelectedAvatar(avatarList: List<ImageView>) {
        avatarList.forEachIndexed { index: Int, imageView: ImageView ->
            imageView.setOnClickListener {
                signUpVm.setAvatarId(AvatarHelper.provideList()[index])
                avatarList.forEach {
                    it.setBackgroundResource(android.R.color.transparent)
                }
                avatarList[index].setBackgroundResource(R.color.navigationColour)
            }
        }
    }

    private fun makeToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
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

    private fun onLoad() = with(binding) {
        binding.loadingInd.isVisible = true
        signUpBtn.isEnabled = false
    }

    private fun onSuccess(uiState: UiState.Success) = with(binding) {
        binding.loadingInd.isGone = true
        binding.apply {
            email.text?.clear()
            userName.text?.clear()
            password.text?.clear()
            rePassword.text?.clear()

            val action =
                SignUpFragmentDirections.actionSignUpFragmentToRegisterConfirmationFragment()
            findNavController().navigate(action)
        }
    }

    private fun onError(uiState: UiState.Error) {
        binding.loadingInd.isGone = true
        signUpBtn.isEnabled = true
        makeToast(uiState.message)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}