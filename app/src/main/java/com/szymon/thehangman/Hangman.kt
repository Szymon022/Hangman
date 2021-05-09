package com.szymon.thehangman

import android.app.Activity
import java.util.*

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

    private fun completeAnswerWith(userGuess: Char) {
        for (i in 0 until wordLength!!-1) {
            if (userGuess == word[i]) {
                answer?.set(i, userGuess)
            }
        }
    }

    private fun isGuessCorrect(userGuess: Char) = word.contains(userGuess.toUpperCase())

    private fun isWordValid(word: String) = word.length <= MAX_CHARACTERS

    private fun isWon() = !answer!!.contains('_')

    private fun isLost() = livesLeft <= 0

    private fun start() {
        // restart game data
        if (gameStatus != GameStatus.GAME_STARTED) {
            livesLeft = LIVES
            gameStatus = GameStatus.GAME_STARTED
            println("New game has been started!")
        }
//        gameUI.update(this)
    }

    fun newGame(word: String) {
        if(isWordValid(word)) {
            this.word = word.toUpperCase()
            initAnswerArray(this.word)
            start()
        } else {
            println("Error! $word is not valid!")
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