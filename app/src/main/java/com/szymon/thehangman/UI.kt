package com.szymon.thehangman

import android.app.Activity
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class UI(private val activity: Activity, private val hangmanGame: Hangman) {

    // UI contents:
    private val tvLivesLeft: TextView =     activity.findViewById(R.id.tv_lives_left)
    private val tvAnswer: TextView =        activity.findViewById(R.id.tv_answer)
    private val ivGallows: ImageView =      activity.findViewById(R.id.iv_gallows_pole)

    init {
        // initial update of UI based on given hangman game state
        update(hangmanGame)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun update(gameInstance: Hangman) {
        val livesLeft = gameInstance.livesLeft
        tvLivesLeft.text = livesLeft.toString()

        val currentAnswer = answerToString(gameInstance.answer)
        tvAnswer.text = (currentAnswer)

        val gallowsDrawable = when(gameInstance.livesLeft) {
            5 -> activity.getDrawable(R.drawable.img_lives_5)
            4 -> activity.getDrawable(R.drawable.img_lives_4)
            3 -> activity.getDrawable(R.drawable.img_lives_3)
            2 -> activity.getDrawable(R.drawable.img_lives_2)
            1 -> activity.getDrawable(R.drawable.img_lives_1)
            0 -> activity.getDrawable(R.drawable.img_lives_0)
            else -> null
        }
        ivGallows.setImageDrawable(gallowsDrawable)
    }

    private fun answerToString(answer: MutableList<Char>?): String {
        var generatedAnswer = ""
        answer?.forEach { c ->
            generatedAnswer += if (c != ' ') "$c " else "   "
        }
        return generatedAnswer
    }

}