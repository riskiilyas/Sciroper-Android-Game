package com.binar.sciroper.util

import android.content.Context
import android.media.MediaPlayer
import com.binar.sciroper.R


object BGMusic {
    var mediaPlayer: MediaPlayer? = null

    fun createMediaplayer(context: Context) {
        mediaPlayer = MediaPlayer.create(
            context,
            R.raw.bensound_ukulele
        )
    }

    fun playMusic() {
        mediaPlayer?.start()
        mediaPlayer?.isLooping = true
    }

    fun pausePlay() {
        mediaPlayer?.pause()
    }

    fun stopPlay() {
        mediaPlayer?.pause()
        mediaPlayer?.seekTo(0)
    }
}
