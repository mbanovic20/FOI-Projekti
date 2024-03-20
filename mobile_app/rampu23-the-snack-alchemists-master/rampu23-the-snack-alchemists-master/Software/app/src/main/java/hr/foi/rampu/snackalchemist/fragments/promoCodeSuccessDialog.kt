package hr.foi.rampu.snackalchemist.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import hr.foi.rampu.snackalchemist.R

class promoCodeSuccessDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            val builder = AlertDialog.Builder(it)
            builder.setMessage("Code activated")
                .setPositiveButton("OK") { dialog, id ->
                    // START THE GAME!
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}