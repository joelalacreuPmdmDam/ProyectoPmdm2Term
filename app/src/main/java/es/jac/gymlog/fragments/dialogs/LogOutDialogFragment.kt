package es.jac.gymlog.fragments.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import es.jac.gymlog.R

class LogOutDialogFragment : DialogFragment() {


    private lateinit var miListener: LogOutDialogListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is LogOutDialogListener){
            miListener = context
        }else{
            throw Exception("Your fragment or activity must implement the interface LogOutDialogListener")
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let{
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            builder
                .setView(inflater.inflate(R.layout.dialog_log_out,null))
                .setPositiveButton("OK"){ dialog,id ->
                    miListener.onDialogPositiveBtnClicked()
                }
                .setNegativeButton("No"){dialog,id ->
                    //Cancel dialog
                }
            builder.create()
        }?: throw IllegalStateException("Ativity cannot be null")
    }

    interface LogOutDialogListener{
        fun onDialogPositiveBtnClicked()
    }

}