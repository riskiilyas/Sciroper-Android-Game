package com.binar.sciroper.ui.fragments.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binar.sciroper.R
import com.binar.sciroper.databinding.FragmentSignUpBinding
import com.binar.sciroper.util.App
import com.binar.sciroper.util.AvatarHelper
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
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
        rePassword = binding.tietRepassword
        signUpBtn = binding.btnSignUp
        loadingInd = binding.loadingInd
        avatarList = listOf(binding.avatar1, binding.avatar2, binding.avatar3, binding.avatar4)

        val database = Firebase.database("https://sciroper-fd4fe-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val ref = database.getReference("User")


        binding.apply {
            vm = signUpVm
            lifecycleOwner = viewLifecycleOwner
        }

//        auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
//            .addOnCompleteListener(requireActivity()) { task ->
//                if (task.isSuccessful) {
//                    Log.d("banana auth", "createUserWithEmail: Success")
//                    val user = auth.currentUser
//                    makeToast("$user")
//                } else {
//                    Log.w("banana auth", "createUserWithEmail:failure", task.exception)
//                    makeToast("Authentication failed.")
//                }
//            }

        signUpVm.navToMenu.observe(viewLifecycleOwner) {
            if (it) {
                Log.i("banana", "navToMenu Triggered")
                val action =
                    SignUpFragmentDirections.actionSignUpFragmentToRegisterConfirmationFragment()
                findNavController().navigate(action)
                signUpVm.setEndNav()
            }
        }

        signUpVm.errorToast.observe(viewLifecycleOwner) {
            if (it) {
                makeToast("Username or email was registered")
                signUpVm.setEndToast()
            }
        }

        signUpVm.errorEmailFormatToast.observe(viewLifecycleOwner) {
            if (it) {
                makeToast("Incorrect email format")
                signUpVm.setEndErrorEmailFormatToast()
            }
        }

        signUpVm.nonMatchingPassword.observe(viewLifecycleOwner) {
            if (it) {
                makeToast("Password does not match")
                signUpVm.setNonMatchingPasswordToast()
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
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}