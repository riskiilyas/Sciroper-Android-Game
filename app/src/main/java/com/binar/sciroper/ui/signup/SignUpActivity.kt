package com.binar.sciroper.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.*
import com.binar.sciroper.R

import com.binar.sciroper.databinding.ActivitySignUpBinding
import com.binar.sciroper.ui.register_confirmation.RegisterConfirmationActivity
import com.google.android.material.textfield.TextInputEditText

class SignUpActivity : AppCompatActivity(), SignUpContract.View {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var signUpPresenter: SignUpPresenter
    private lateinit var userName: TextInputEditText
    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var rePassword: TextInputEditText
    private lateinit var signUpBtn: Button
    private lateinit var loadingInd: ProgressBar
    private lateinit var radioGroup: RadioGroup
    private var avatarId: Int = R.drawable.avatar21


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        signUpPresenter = SignUpPresenter(this)

        userName = binding.tietUsername
        email = binding.tietEmail
        password = binding.tietPassword
        rePassword = binding.tietRepassword
        signUpBtn = binding.btnSignUp
        loadingInd = binding.loadingInd
        radioGroup = binding.radioGroup

        signUpBtn.setOnClickListener {
            // check if user has input email in the correct pattern/format
            if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()){
                showProgress()
                onSignUpMsg("Invalid email format")
            }
            // check if password value equals rePassword value
            else if (password.text.toString() != rePassword.text.toString()) {
                showProgress()
                onSignUpMsg("Password does not match")
            }
            // simulate loading during data fetch, register user and move to register succeed screen
            else {
                showProgress()
                Handler(Looper.getMainLooper()).postDelayed({
                    signUpPresenter.register(
                        username = userName.text.toString(),
                        email = email.text.toString(),
                        password = password.text.toString(),
                        avatarId = avatarId
                    )
                }, 3000)
            }
        }

        // to check for user avatar and also set the indicator if an avatar is chosen
        // do note that default avatar is set to id of R.drawable.avatar21
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.avatar1.id -> {
                    avatarId = R.drawable.avatar21
                    binding.avatar1.background = getDrawable(R.drawable.avatar11)
                    binding.avatar2.background = getDrawable(R.drawable.avatar22)
                    binding.avatar3.background = getDrawable(R.drawable.avatar23)
                    binding.avatar4.background = getDrawable(R.drawable.avatar24)
                }
                binding.avatar2.id -> {
                    avatarId = R.drawable.avatar22
                    binding.avatar1.background = getDrawable(R.drawable.avatar21)
                    binding.avatar2.background = getDrawable(R.drawable.avatar12)
                    binding.avatar3.background = getDrawable(R.drawable.avatar23)
                    binding.avatar4.background = getDrawable(R.drawable.avatar24)
                }
                binding.avatar3.id -> {
                    avatarId = R.drawable.avatar23
                    binding.avatar1.background = getDrawable(R.drawable.avatar21)
                    binding.avatar2.background = getDrawable(R.drawable.avatar22)
                    binding.avatar3.background = getDrawable(R.drawable.avatar13)
                    binding.avatar4.background = getDrawable(R.drawable.avatar24)
                }
                binding.avatar4.id -> {
                    avatarId = R.drawable.avatar24
                    binding.avatar1.background = getDrawable(R.drawable.avatar21)
                    binding.avatar2.background = getDrawable(R.drawable.avatar22)
                    binding.avatar3.background = getDrawable(R.drawable.avatar23)
                    binding.avatar4.background = getDrawable(R.drawable.avatar14)
                }
            }
        }

        addTextChangedListenerOnView(
            userName,
            email,
            password,
            rePassword,
            textWatcher = textWatcher
        )

    }

    // text watcher applied to sign up button to enable or disable sign up button depending;
    // if user has input all the required field
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

    // display message when sign up button is clicked
    override fun onSignUpMsg(message: String) {
        Handler(Looper.getMainLooper()).postDelayed({
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }, 3000)
    }

    // to show progress bar
    override fun showProgress() {
        loadingInd.visibility = View.VISIBLE
        Handler(Looper.getMainLooper()).postDelayed({
            loadingInd.visibility = View.GONE
        }, 3000)
    }

    // if sign up is success, onSuccess is used to navigate to register confirmation activity
    override fun onSuccess(username: String) {
        signUpPresenter.getUser(email.text.toString(), password.text.toString())
        val intent = Intent(this, RegisterConfirmationActivity::class.java)
        startActivity(intent)
    }
}