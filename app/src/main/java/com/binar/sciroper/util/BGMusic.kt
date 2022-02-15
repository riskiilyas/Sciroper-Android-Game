package com.binar.sciroper.util

import android.content.Context
import android.media.MediaPlayer
import com.binar.sciroper.R


object BGMusic {
    var mediaPlayer: MediaPlayer? = null
    fun playMusic(context: Context) {
        mediaPlayer = MediaPlayer.create(
            context,
            R.raw.bensound_ukulele
        )
        mediaPlayer?.start()
        mediaPlayer?.isLooping = true
    }

    fun isPlay() = mediaPlayer?.isPlaying ?: false

    fun pausePlay() {
        mediaPlayer?.pause()
    }
}
