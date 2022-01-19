package com.binar.sciroper.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.binar.sciroper.data.db.user.User
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

        signUpBtn.setOnClickListener {
            if (password.text.toString() != rePassword.text.toString()) {
                showProgress()
                onSignUpMsg("Password does not match")
            } else {
                showProgress()
                signUpPresenter.register(
                    username = userName.text.toString(),
                    email = email.text.toString(),
                    password = password.text.toString()
                )
            }
        }

        userName.addTextChangedListener(textWatcher)
        email.addTextChangedListener(textWatcher)
        password.addTextChangedListener(textWatcher)
        rePassword.addTextChangedListener(textWatcher)
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
        }

        override fun afterTextChanged(s: Editable?) {
        }
    }

    override fun onSignUpMsg(message: String) {
        Handler(Looper.getMainLooper()).postDelayed({
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }, 3000)
    }

    override fun showProgress() {
        loadingInd.visibility = View.VISIBLE
        Handler(Looper.getMainLooper()).postDelayed({
            loadingInd.visibility = View.GONE
        }, 3000)
    }

    override fun onSuccess(user: User) {
        val intent = Intent(this, RegisterConfirmationActivity::class.java)
        intent.putExtra("key_id", user.id)
        startActivity(intent)
    }

    fun getData() {
        TODO("Figure out how to send intent from sign up to registration")
        TODO("Figure out how to send fix avatar bug in login screen")
    }
}