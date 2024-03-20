package hr.foi.rampu.snackalchemist.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import hr.foi.rampu.snackalchemist.CustomProduct
import hr.foi.rampu.snackalchemist.R

class Homepage : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_homepage, container, false)

        // Access views within the layout using rootView
        val multilineText = rootView.findViewById<TextView>(R.id.multilineText)
        val myButton = rootView.findViewById<Button>(R.id.myButton)

        myButton.setOnClickListener {
            val intent = Intent(activity, CustomProduct::class.java)
            startActivity(intent)
        }

        // Set any additional logic or listeners here

        return rootView
    }

}