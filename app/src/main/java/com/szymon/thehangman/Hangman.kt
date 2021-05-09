package com.szymon.thehangman

import android.app.Activity

class Hangman(val activity: Activity) {

    // how many chances user has to guess
    private val LIVES = 5
    private val MAX_CHARACTERS = 15

    private var wordLength: Int? = null
    private var gameUI: UI? = null

    var livesLeft: Int = LIVES
    var word: String = ""
    var answer: MutableList<Char>? = null
    var gameStatus: GameStatus? = GameStatus.GAME_STOPPED

    enum class GameStatus {
        GAME_STARTED,
        GAME_STOPPED,
        GAME_WON,
        GAME_LOST
    }

    init {
        initAnswerArray("")
    }

    private fun initAnswerArray(word: String) {
        wordLength= word.length
        answer = mutableListOf()

        for (i in 1 until wordLength!!) {
            if (word[i-1] != ' ') {
                answer!!.add('_')
            }
        }
    }

    fun enterGuess(userGuess : Char) {
        if (livesLeft > 0) {
            if (isGuessCorrect(userGuess)) {
                println("Guessed correctly!")
                completeAnswerWith(userGuess)

                if (isWon()) {
                    println("Congratulations! You won!")
                    gameStatus = GameStatus.GAME_WON
                }
            } else {
                println("Wrong guess!")
                livesLeft--

                if (isLost()) {
                    println("Ops! You lost! :(")
                    gameStatus = GameStatus.GAME_LOST
                }
            }
//            gameUI.update(this)
        }
    }


}