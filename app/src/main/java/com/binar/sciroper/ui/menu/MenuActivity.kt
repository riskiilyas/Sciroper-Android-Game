package com.binar.sciroper.ui.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.binar.sciroper.R
import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity(), MenuContract.View {

    private lateinit var binding: ActivityMenuBinding
    private lateinit var menuPresenter: MenuPresenter
    private lateinit var username: TextView
    private lateinit var userLevel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent?.extras?.getInt("key_id")!!

        username = binding.tvUsername
        userLevel = binding.tvUserLevel

        menuPresenter = MenuPresenter(this)

        username.text = userId.toString()
        menuPresenter.getUser(userId).observe(this) {
            bind(it)
        }
    }

    private fun bind(user: User) {
        binding.apply {
            username.text = user.username
            userLevel.text = getString(R.string.user_level, user.level)
        }
    }
}