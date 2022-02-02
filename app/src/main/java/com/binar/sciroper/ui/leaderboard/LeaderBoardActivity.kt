package com.binar.sciroper.ui.leaderboard

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.binar.sciroper.databinding.ActivityLeaderBoardBinding
import com.binar.sciroper.util.UserLevel
import com.binar.sciroper.R
import com.binar.sciroper.ui.mvvm.fragments.leaderboard.LeaderBoardVm
import com.binar.sciroper.ui.mvvm.fragments.leaderboard.LeaderBoardVmFactory
import com.binar.sciroper.ui.mvvm.fragments.leaderboard.UserListAdapter
import com.binar.sciroper.util.App


class LeaderBoardActivity : AppCompatActivity(), MainView {
    private lateinit var binding: ActivityLeaderBoardBinding
    private val leaderBoardVm: LeaderBoardVm by viewModels {
        LeaderBoardVmFactory(App.appDb.getUserDao())
    }
    private lateinit var recyclerView: RecyclerView

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeaderBoardBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_ActivityLeaerboard)
        setContentView(binding.root)
        recyclerView = binding.rcPlayer
        val adapter = UserListAdapter()
        recyclerView.adapter = adapter
        binding.vm = leaderBoardVm
        binding.lifecycleOwner = this


        leaderBoardVm.allUsers.observe(this) { users ->
            users.let {
                adapter.submitList(UserLevel.sortUsersLevel(it))
            }
            leaderBoardVm.getUserListSize(users)
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }
}


