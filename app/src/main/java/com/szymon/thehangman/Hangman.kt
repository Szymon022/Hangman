package com.szymon.thehangman

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class Hangman(activity: Activity) {

    // how many chances user has to guess
    private val LIVES = 5
    private val MAX_CHARACTERS = 15
    private val MIN_CHARACTERS = 3

    private var wordLength: Int? = null
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
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
        gameUI = UI(activity, this)
    }

    private fun initAnswerArray(word: String) {
        wordLength = word.length
        answer = mutableListOf()

        // maybe it should be from 0 to wordLength?
        for (i in 0 until wordLength!!) {
            val c = if(word[i] != ' ') '_' else ' '
            answer!!.add(i, c)
        }
    }

    private fun completeAnswerWith(userGuess: Char) {
        for (i in 0 until wordLength!!) {
            if (userGuess.toUpperCase() == word[i]) {
                answer?.set(i, userGuess)
            }
        }
    }

    private fun isGuessCorrect(userGuess: Char) = word.contains(userGuess.toUpperCase())

    private fun isWon() = !answer!!.contains('_')

    private fun isLost() = livesLeft <= 0

    fun isWordValid(word: String) = (word.length in MIN_CHARACTERS..MAX_CHARACTERS)

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun start() {
        // restart game data
        if (gameStatus != GameStatus.GAME_STARTED) {
            livesLeft = LIVES
            gameStatus = GameStatus.GAME_STARTED
            println("New game has been started!")
        }
        gameUI?.update(this)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun newGame(word: String) {
        if(isWordValid(word)) {
            this.word = word.toUpperCase(Locale.ROOT)
            initAnswerArray(this.word)
            start()
        } else {
            println("Error! $word is not valid!")
        }
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
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
            gameUI?.update(this)
        }
    }


}