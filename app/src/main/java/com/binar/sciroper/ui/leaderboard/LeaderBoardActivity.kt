package com.binar.sciroper.ui.leaderboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.binar.sciroper.data.db.AppDB
import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.databinding.ActivityLeaderBoardBinding

class LeaderBoardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLeaderBoardBinding
    private lateinit var presenterMain : PresenterBoard
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val database = Room.databaseBuilder(
            this, AppDB::class.java, "Users"
        )
            .allowMainThreadQueries()
            .build()

//        val allDataPlayer = database.getUserDao().getUsers()


//        binding.rcPlayer.apply {
//            layoutManager = LinearLayoutManager(this@LeaderBoardActivity)
//            adapter = AdapterPlayer(presenterMain)
//        }
//
        val recyclerView = binding.rcPlayer
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = AdapterPlayer(this, presenterMain.getData() as MutableList<User>)
        binding.rcPlayer.apply {
            recyclerView.adapter
        }

    }
}


