package com.binar.sciroper.ui.playgame

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.binar.sciroper.R
import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.databinding.ActivityCpuBinding
import com.binar.sciroper.ui.playervsplayer.DialogResultPvP
import com.binar.sciroper.util.App
import com.binar.sciroper.util.UserLevel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("ResourceAsColor")
class CPUActivity : AppCompatActivity(), PlayView, DialogView {

    private lateinit var binding: ActivityCpuBinding
    private lateinit var player1: LiveData<User>
    private lateinit var presenter: PresenterPlay
    private var isPlayerTurn = true

    private lateinit var p1Choices : List<ImageView>
    private lateinit var comChoices : List<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCpuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        player1 = App.appDb.getUserDao().getUserById(AppSharedPreference.id!!)
        presenter = PresenterPlayImpl(this, player1.value!!.username)
        binding.pemain1Player.text = player1.value!!.username
        binding.ivAvatarPlayer.setImageResource(player1.value!!.avatarId)

        p1Choices = listOf(
            binding.ivPemain1BatuPlayer,
            binding.ivPemain1KertasPlayer,
            binding.ivPemain1GuntingPlayer
        )

        comChoices = listOf(
            binding.ivPemain2BatuCOM,
            binding.ivPemain2KertasCOM,
            binding.ivPemain2GuntingCOM
        )

        p1Choices.forEachIndexed { index, it ->
            if (isPlayerTurn){
                it.setBackgroundColor(R.color.navigationColour)
                showToast("${player1.value!!.username} Memilih ${it.contentDescription}")
            }
        }

    }

    override fun hasil(hasil: String, status: Int) {
        val dialogResult = DialogResultPvP()
        val bundle = Bundle()
        bundle.putString("hasil", hasil)
        dialogResult.arguments = bundle
        dialogResult.show(supportFragmentManager, "DialogResult")
        val userLevel = UserLevel(player1.value!!)

        when (status) {
            0 -> userLevel.draw()
            1 -> userLevel.win()
            -1 -> userLevel.lose()
        }

        GlobalScope.launch {
            App.appDb.getUserDao().updateUser(player1.value!!)
        }
    }

    override fun reset() {
        p1Choices.forEach {
            it.setBackgroundColor(Color.TRANSPARENT)
        }
        comChoices.forEach {
            it.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    override suspend fun comPlay(id: Int) {
        delay(800)
        comChoices.forEachIndexed { index, image ->
            image.setBackgroundColor(R.color.navigationColour)
            delay(800)
            image.setBackgroundColor(Color.TRANSPARENT)
        }
        comChoices[id].setBackgroundColor(R.color.navigationColour)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
