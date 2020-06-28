package org.malucky.itinerary

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.fragment.app.DialogFragment

class SingleChoice : DialogFragment() {
    var position = 0 //default selected position


    interface SingleChoiceListener {
        fun onPositiveButtonClicked(list: Array<String?>?, position: Int)

        fun onNegativeButtonClicked()
    }

    var mListener: SingleChoiceListener? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mListener = context as SingleChoiceListener
        }catch (e : Exception){
            throw ClassCastException("SingleChoice must implemented")
        }
    }

    @NonNull
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        val list =
            activity!!.resources.getStringArray(R.array.choice_items)
        builder.setTitle("Select your Choice")
            .setSingleChoiceItems(list, position, object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    position = which
                }

            })
            .setPositiveButton("OK", object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    mListener!!.onPositiveButtonClicked(list, position)
                }

            })
            .setNegativeButton("Cancel", object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    mListener!!.onNegativeButtonClicked()
                }

            })

        return builder.create()
    }


}