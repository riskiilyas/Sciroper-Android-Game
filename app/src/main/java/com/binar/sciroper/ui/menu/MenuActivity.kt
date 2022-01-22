package com.binar.sciroper.ui.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.binar.sciroper.R
import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity(), MenuContract.View {

    private lateinit var binding: ActivityMenuBinding
    private lateinit var menuPresenter: MenuPresenter
    private lateinit var username: TextView
    private lateinit var userImage: ImageView
    private lateinit var userLevel: TextView
    private lateinit var exitBtn: Button
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId = AppSharedPreference.id!!

        username = binding.tvUsername
        userLevel = binding.tvUserLevel
        userImage = binding.userImg
        exitBtn = binding.btnExitGame

        menuPresenter = MenuPresenter(this)

        username.text = userId.toString()
        menuPresenter.getUser(userId).observe(this) {
            bind(it)
        }

        exitBtn.setOnClickListener {
            createDialog()
        }
    }

    private fun bind(user: User) {
        binding.apply {
            username.text = user.username
            userLevel.text = getString(R.string.user_level, user.level)
            userImage.setImageResource(user.avatarId)
        }
    }

    private fun createDialog() {
        val dialogFragment = DialogExit()
        dialogFragment.isCancelable = false
        dialogFragment.show(supportFragmentManager, "custom_dialog")
    }

    override fun onBackPressed() {
        val homeIntent = Intent(Intent.ACTION_MAIN)
        homeIntent.addCategory(Intent.CATEGORY_HOME)
        homeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(homeIntent)
    }
}