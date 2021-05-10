package com.szymon.thehangman

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import kotlin.ClassCastException

class NewGameDialogFragment : DialogFragment() {

private lateinit var listener: NewGameDialogListener

    interface NewGameDialogListener {
        fun onDialogStartClick(dialog: DialogFragment)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // verifying if host activity implemented callback interface
        try {
            listener = context as NewGameDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException((context.toString() +
                    " must implement NoticeDialogListener"))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            builder.setView(inflater.inflate(R.layout.new_game_dialog, null))

                    .setPositiveButton(R.string.start) { _, _ ->
                            // start new game
                            listener.onDialogStartClick(this)
                        }

                    .setNegativeButton(R.string.cancel) { _, _ ->
                            dialog?.cancel()
                        }

            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }

}