package com.binar.sciroper.util

import android.content.Context
import android.media.MediaPlayer
import com.binar.sciroper.R

var mediaPlayer: MediaPlayer? = null
var durationNow = 0
fun playMusic(context: Context) {
    mediaPlayer = MediaPlayer.create(
        context,
        R.raw.bensound_ukulele
    )

    mediaPlayer?.start()
    mediaPlayer?.seekTo(durationNow)
    mediaPlayer?.isLooping = true
}

fun pausePlay() {
    durationNow = mediaPlayer?.duration!!
    mediaPlayer?.pause()
}

fun resetMusic(){
    mediaPlayer?.pause()
    mediaPlayer?.reset()
}


