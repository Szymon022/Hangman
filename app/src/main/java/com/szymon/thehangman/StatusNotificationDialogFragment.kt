package com.szymon.thehangman

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
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

            builder.setView(inflater.inflate(R.layout.game_status_notification_layout, null))

                    // listeners can be simplified
                    .setPositiveButton(R.string.new_game,
                            DialogInterface.OnClickListener { dialog, which ->
                                listener.onDialogNewGameClick(this)
                            })

                    .setNegativeButton(R.string.cancel,
                            DialogInterface.OnClickListener { dialog, which ->
                                getDialog()?.cancel()
                            })

            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }
}