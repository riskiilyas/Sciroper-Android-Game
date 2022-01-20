package com.binar.sciroper.ui.login

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
import com.binar.sciroper.databinding.ActivityLogInBinding
import com.binar.sciroper.ui.menu.MenuActivity
import com.binar.sciroper.ui.signup.SignUpActivity
import com.google.android.material.textfield.TextInputEditText

class LogInActivity : AppCompatActivity(), LogInContract.View {
    private lateinit var binding: ActivityLogInBinding
    private lateinit var logInPresenter: LogInPresenter
    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var signUpBtn: Button
    private lateinit var signInBtn: Button
    private lateinit var loadingInd: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        email = binding.tietEmail
        password = binding.tietPassword
        signUpBtn = binding.btnSignUp
        signInBtn = binding.btnSignIn
        loadingInd = binding.loadingInd

        logInPresenter = LogInPresenter(this)

        signUpBtn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        signInBtn.setOnClickListener {
            showProgress()
            Handler(Looper.getMainLooper()).postDelayed({
                logInPresenter.login(
                    email = email.text.toString(),
                    password = password.text.toString()
                )
            }, 3000)
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

    override fun onSignInMsg(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        loadingInd.visibility = View.VISIBLE
        Handler(Looper.getMainLooper()).postDelayed({
            loadingInd.visibility = View.GONE
        }, 3000)
    }

    override fun onSuccess(user: User) {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }
}