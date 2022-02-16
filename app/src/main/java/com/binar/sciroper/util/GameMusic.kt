package com.binar.sciroper.util

import android.content.Context
import android.media.MediaPlayer
import com.binar.sciroper.R

fun winMusic(context: Context) {
    mediaPlayer = MediaPlayer.create(
        context,
        R.raw.win_game
    )
    mediaPlayer?.start()
}

fun drawMusic(context: Context) {
    mediaPlayer = MediaPlayer.create(
        context,
        R.raw.draw_game
    )
    mediaPlayer?.start()
}

fun soundEffect(context: Context) {
    mediaPlayer = MediaPlayer.create(
        context,
        R.raw.sound_effect
    )
    mediaPlayer?.start()
}