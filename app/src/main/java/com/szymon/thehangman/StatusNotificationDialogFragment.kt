package com.szymon.thehangman

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

class StatusNotificationDialogFragment(val gameStatus: Hangman.GameStatus): DialogFragment() {

    private lateinit var listener: StatusDialogListener

    interface StatusDialogListener {
        fun onDialogNewGameClick(dialog: DialogFragment)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // check if callback interface was implemented in main activity
        try {
            listener = context as StatusDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException((context.toString() +
                    " must implement StatusDialogListener"))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.game_status_notification_layout, null)

            // setting proper dialog title
            val tvStatusTitle = view.findViewById<TextView>(R.id.tv_status_header)
            tvStatusTitle.text = when(gameStatus) {
                Hangman.GameStatus.GAME_WON     -> "You won!"
                Hangman.GameStatus.GAME_LOST    -> "You lost!"
                else                            -> "Error"
            }

            // building alert dialog with view
            builder.setView(view)

                    // listeners can be simplified
                    .setPositiveButton(R.string.yes) { _, _ ->
                        listener.onDialogNewGameClick(this)
                    }

                    .setNegativeButton(R.string.no) { _, _ ->
                        dialog?.cancel()
                    }

            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }
}