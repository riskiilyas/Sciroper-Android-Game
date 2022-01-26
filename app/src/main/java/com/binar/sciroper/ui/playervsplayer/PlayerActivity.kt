package com.binar.sciroper.ui.playervsplayer


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.binar.sciroper.R
import com.binar.sciroper.databinding.ActivityPlayerBinding
import com.binar.sciroper.ui.playervsplayer.DialogResultPvP.Companion.RESULT
import com.binar.sciroper.ui.playervsplayer.DialogResultPvP.Companion.RESULT_LOTTIE
import com.binar.sciroper.ui.playervsplayer.DialogResultPvP.Companion.TAG
import com.binar.sciroper.ui.playervsplayer.PresenterPlayerImp.Companion.DEFAULT_RESULT


class PlayerActivity : AppCompatActivity(), PlayerView, DialogViewPvP {

    private val binding by lazy { ActivityPlayerBinding.inflate(layoutInflater) }
    private var presenter = PresenterPlayerImp(this, DEFAULT_RESULT, DEFAULT_RESULT)
    private lateinit var firstPlayerChoice: ImageView
    private lateinit var secondPlayerChoice: ImageView
    private var username = presenter.dataUser.username
    private var firstPlayerResult = DEFAULT_RESULT
    private var secondPlayerResult = DEFAULT_RESULT
    private val secondPlayer = "Player 2"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        presenter = PresenterPlayerImp(this, username, secondPlayer)

        val avatarId = presenter.dataUser.avatarId

        binding.pemain1.text = username
        binding.ivAvatar.setImageResource(avatarId)
        presenter.showToast(this, getString(R.string.choose_first, username))


        val btnPemainSatu = arrayOf(
            binding.ivPemain1Batu,
            binding.ivPemain1Kertas,
            binding.ivPemain1Gunting,
        )

        val btnPemainDua = arrayOf(
            binding.ivPemain2Batu,
            binding.ivPemain2Kertas,
            binding.ivPemain2Gunting,
        )


        disableClick2(false)
        btnPemainSatu.forEachIndexed { index, ImageView ->
            ImageView.setOnClickListener {
                firstPlayerResult = btnPemainSatu[index].contentDescription.toString()
                firstPlayerChoice = btnPemainSatu[index]
                presenter.showToast(
                    this,
                    getString(R.string.player_choose, username, firstPlayerResult)
                )

                disableClick1(false)
                disableClick2(true)

                btnPemainSatu.forEach {
                    it.setBackgroundResource(R.drawable.shape_background)
                }
            }
        }
        btnPemainDua.forEachIndexed { index, ImageView ->
            ImageView.setOnClickListener {
                secondPlayerResult = btnPemainDua[index].contentDescription.toString()
                secondPlayerChoice = btnPemainDua[index]
                presenter.showToast(
                    this,
                    getString(R.string.player_choose, secondPlayer, secondPlayerResult)
                )
                disableClick2(false)
                btnPemainDua.forEach {
                    it.setBackgroundResource(R.drawable.shape_background)
                }
            }
        }

        binding.btnShow.setOnClickListener {
            if (firstPlayerResult != DEFAULT_RESULT && secondPlayerResult != DEFAULT_RESULT) {
                presenter.checkSuit(firstPlayerResult, secondPlayerResult)
                reset(android.R.color.transparent)
                firstPlayerChoice.setBackgroundResource(R.drawable.shape_background)
                secondPlayerChoice.setBackgroundResource(R.drawable.shape_background)

            } else if (firstPlayerResult != DEFAULT_RESULT && secondPlayerResult == DEFAULT_RESULT) {
                presenter.showToast(this, getString(R.string.havent_choose, secondPlayer))
            } else {
                presenter.showToast(this, getString(R.string.havent_choose, username))

            }
        }

        binding.ivRefresh.setOnClickListener {
            reset(android.R.color.transparent)
        }

        binding.ivBack.setOnClickListener {
            finish()
        }

    }

    override fun disableClick1(isEnable: Boolean) {
        binding.ivPemain1Kertas.isEnabled = isEnable
        binding.ivPemain1Batu.isEnabled = isEnable
        binding.ivPemain1Gunting.isEnabled = isEnable
    }

    override fun disableClick2(isEnable: Boolean) {
        binding.ivPemain2Kertas.isEnabled = isEnable
        binding.ivPemain2Batu.isEnabled = isEnable
        binding.ivPemain2Gunting.isEnabled = isEnable
    }

    override fun result(result: String, resultLottie: Int) {
        val dialogResult = DialogResultPvP()
        val bundle = Bundle()
        bundle.putString(RESULT, result)
        bundle.putInt(RESULT_LOTTIE, resultLottie)
        dialogResult.arguments = bundle
        dialogResult.show(supportFragmentManager, TAG)
    }

    override fun reset(
        bgReset: Int
    ) {
        binding.apply {
            ivPemain1Batu.setBackgroundResource(bgReset)
            ivPemain1Kertas.setBackgroundResource(bgReset)
            ivPemain1Gunting.setBackgroundResource(bgReset)
            ivPemain2Batu.setBackgroundResource(bgReset)
            ivPemain2Kertas.setBackgroundResource(bgReset)
            ivPemain2Gunting.setBackgroundResource(bgReset)
        }
        firstPlayerResult = DEFAULT_RESULT
        secondPlayerResult = DEFAULT_RESULT
        disableClick1(true)
        disableClick2(false)

    }
}
