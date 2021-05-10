package com.szymon.thehangman

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment

class MainActivity : AppCompatActivity(),
    NewGameDialogFragment.NewGameDialogListener, StatusNotificationDialogFragment.StatusDialogListener,
    Hangman.GameStatusChangedListener {


    private lateinit var btEnterGuess: Button
    private lateinit var tvNewGameButton: TextView
    private lateinit var etPlayerGuess: EditText

    private lateinit var hangman: Hangman

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initActivity()
        showNewGameDialog()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initActivity() {
        hangman = Hangman(this)

        tvNewGameButton = findViewById(R.id.tv_new_game)
        btEnterGuess    = findViewById(R.id.bt_enter_guess)
        etPlayerGuess   = findViewById(R.id.et_user_guess)

        setOnClickListeners()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setOnClickListeners() {
        tvNewGameButton.setOnClickListener {
            showNewGameDialog()
        }

        btEnterGuess.setOnClickListener {
            val playerGuess = etPlayerGuess.text.toString()
            if(playerGuess.isNotEmpty()) {
                hangman.enterGuess(playerGuess[0])
                // clear guess entrance field each time
                etPlayerGuess.setText("")
            }
        }
    }

    private fun showNewGameDialog() {
        val newGameDialog = NewGameDialogFragment()
        newGameDialog.show(supportFragmentManager, "newGame")
    }

    private fun showNotificationDialog(gameStatus: Hangman.GameStatus) {
        val notificationDialog = StatusNotificationDialogFragment(gameStatus)
        notificationDialog.show(supportFragmentManager, "notifyStatus")
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDialogStartClick(dialog: DialogFragment) {
        val etWord : EditText? = dialog.dialog?.findViewById<EditText>(R.id.et_word)
        val word = etWord?.text.toString()

        if (!hangman.isWordValid(word)) {
            dialog.dismiss()
            showNewGameDialog()
        } else {
            hangman.newGame(word)
            dialog.dismiss()
        }
    }

    override fun onDialogNewGameClick(dialog: DialogFragment) {
        dialog.dismiss()
        showNewGameDialog()
    }

    override fun onGameStatusChanged(gameStatus: Hangman.GameStatus) {
        showNotificationDialog(gameStatus)
    }
}