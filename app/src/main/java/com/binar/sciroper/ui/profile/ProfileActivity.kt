package com.binar.sciroper.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.binar.sciroper.R
import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.databinding.ActivityProfileBinding
import com.binar.sciroper.util.App
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity(), ProfileView {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var presenterProfile: PresenterProfile
    private var userAvatar: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = User(1, "rahmat", "rahmat@gmail.com", "1223", 1, 0, 0, 0, 0)

        GlobalScope.launch {
            App.appDb.getUserDao().insertUser(user)
        }
        presenterProfile = PresenterProfile(this)
        val username = presenterProfile.getDataUser().username
        val email = presenterProfile.getDataUser().email
        val avatarID = presenterProfile.getDataUser().avatarId
        binding.etUsername.setText(username)
        binding.etEmail.setText(email)

        val avatar = arrayOf(
            binding.avatarId1,
            binding.avatarId2,
            binding.avatarId3,
            binding.avatarId4,
        )
        avatar[avatarID].setBackgroundResource(R.color.navigationColour)
        choiceAvatar(avatar)



        binding.btnUpdate.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val email = binding.etEmail.text.toString()
            val oldPass = binding.etOldPass.text.toString()
            val newPass = binding.etNewPass.text.toString()
            val reNewPass = binding.etRePass.text.toString()

            presenterProfile.updateDataUser(
                userAvatar,
                username,
                email,
                oldPass,
                newPass,
                reNewPass
            )
        }
        binding.btnSignOut.setOnClickListener {
            onSignOut()
        }
    }

    override fun onSuccessUpdate(user: Int) {
        val dialogUpdate = DialogUpdate()
        dialogUpdate.show(supportFragmentManager, "DialogUpdate")
        binding.etOldPass.text?.clear()
        binding.etNewPass.text?.clear()
        binding.etRePass.text?.clear()
    }

    override fun onErrorUpdate(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onSignOut() {
        val dialogSignOut = DialogSignOut()
        dialogSignOut.show(supportFragmentManager, "DialogSignOut")
    }

    override fun choiceAvatar(user: Array<ImageView>) {
        user.forEachIndexed { index: Int, imageView: ImageView ->
            imageView.setOnClickListener {
                userAvatar = index
                user.forEach {
                    it.setBackgroundResource(android.R.color.transparent)
                }
                user[index].setBackgroundResource(R.color.navigationColour)
            }
        }
    }
}