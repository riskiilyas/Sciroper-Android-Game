package com.binar.sciroper.ui.playgame

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.binar.sciroper.R
import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.databinding.ActivityCpuBinding
import com.binar.sciroper.ui.playervsplayer.DialogResultPvP
import com.binar.sciroper.ui.playervsplayer.DialogViewPvP
import com.binar.sciroper.util.App
import com.binar.sciroper.util.UserLevel
import kotlinx.coroutines.*

@DelicateCoroutinesApi
@SuppressLint("ResourceAsColor")
class CPUActivity : AppCompatActivity(), PlayView, DialogViewPvP {

    private lateinit var binding: ActivityCpuBinding
    private lateinit var player1: User
    private lateinit var presenter: PresenterPlay
    private var isPlayerTurn = true

    private lateinit var p1Choices: List<ImageView>
    private lateinit var comChoices: List<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCpuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        player1 = App.appDb.getUserDao().getUserByIdNoLiveData(AppSharedPreference.id!!)
        presenter = PresenterPlayImpl(this, player1.username)
        binding.pemain1Player.text = player1.username
        binding.ivAvatarPlayer.setImageResource(player1.avatarId)

        binding.ivBack.setOnClickListener { onBackPressed() }

        binding.ivRefresh.setOnClickListener {
            if (isPlayerTurn) {
                reset(Color.TRANSPARENT)
            }
        }

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

        p1Choices.forEach { its ->
            its.setOnClickListener {
                if (isPlayerTurn) {
                    it.setBackgroundResource(R.drawable.shape_background)
                    showToast("${player1.username} Memilih ${it.contentDescription}")
                    presenter.comTurn(it.contentDescription.toString())
                    isPlayerTurn = false
                }
            }
        }

    }

    override fun hasil(hasil: String, status: Int) {
        val userLevel = UserLevel(player1)

        when (status) {
            0 -> userLevel.draw()
            1 -> userLevel.win()
            -1 -> userLevel.lose()
        }

        GlobalScope.launch(Dispatchers.Default) {
            App.appDb.getUserDao().updateUser(player1)
        }
    }

    override fun createDialog(resultString: String, result: Int) {
        val dialogResult = DialogResultPvP()
        val bundle = Bundle()
        when (result) {
            0 -> {
                bundle.putString(DialogResultPvP.RESULT, "DRAW!")
                bundle.putInt(DialogResultPvP.RESULT_LOTTIE, R.raw.result_draw)
            }
            1 -> {
                bundle.putString(DialogResultPvP.RESULT, "$resultString\nWIN!")
                bundle.putInt(DialogResultPvP.RESULT_LOTTIE, R.raw.result_win)
            }
            -1 -> {
                bundle.putString(DialogResultPvP.RESULT, "COM\nWIN!")
                bundle.putInt(DialogResultPvP.RESULT_LOTTIE, R.raw.result_win)
            }
        }
        dialogResult.arguments = bundle
        dialogResult.show(supportFragmentManager, DialogResultPvP.TAG)
    }

    override fun reset(bgReset: Int) {
        p1Choices.forEach {
            it.setBackgroundColor(Color.TRANSPARENT)
        }
        comChoices.forEach {
            it.setBackgroundColor(Color.TRANSPARENT)
        }
        isPlayerTurn = true
    }

    override suspend fun comPlay(id: Int) {
        delay(800)
        comChoices.forEach { image ->
            GlobalScope.launch(Dispatchers.Main) {
                image.setBackgroundResource(R.drawable.shape_background)
            }
            delay(400)
            GlobalScope.launch(Dispatchers.Main) {
                image.setBackgroundColor(Color.TRANSPARENT)
            }
        }
        delay(800)
        GlobalScope.launch(Dispatchers.Main) {
            comChoices[id].setBackgroundResource(R.drawable.shape_background)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
