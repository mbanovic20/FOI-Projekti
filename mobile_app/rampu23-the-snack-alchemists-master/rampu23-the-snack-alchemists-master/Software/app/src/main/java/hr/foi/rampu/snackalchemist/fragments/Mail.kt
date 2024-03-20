package hr.foi.rampu.snackalchemist.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import hr.foi.rampu.snackalchemist.R

class Mail : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mail, container, false)

        val spinnerEmailTo = view.findViewById<Spinner>(R.id.spinnerEmailTo)
        val txtEmailSubject = view.findViewById<EditText>(R.id.txtEmailSubject)
        val txtEmailBody = view.findViewById<EditText>(R.id.txtEmailBody)
        val btnSendEmail = view.findViewById<Button>(R.id.btnSendEmail)

        val emailAddressesList = mutableListOf<String>()

        val baza: DatabaseReference = FirebaseDatabase.getInstance().getReference("SnackBaza/Korisnik/")
        baza.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    if (snapshot.exists()) {
                        for (korisnik in snapshot.children) {
                            val email = korisnik.child("email").getValue(String::class.java)
                            email?.let {
                                emailAddressesList.add(it)
                            }
                        }

                        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, emailAddressesList)
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinnerEmailTo.adapter = adapter
                    } else {
                        Toast.makeText(requireContext(), "Nema dostupnih podataka", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "Pogreška: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Greška pri dohvatu podataka: ${error.message}", Toast.LENGTH_LONG).show()
            }
        })

        btnSendEmail.setOnClickListener {
            val emailTo = spinnerEmailTo.selectedItem.toString()
            val emailSubject = txtEmailSubject.text.toString()
            val emailBody = txtEmailBody.text.toString()

            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.data = Uri.parse("mailto:")
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailTo))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject)
            emailIntent.putExtra(Intent.EXTRA_TEXT, emailBody)

            try {
                startActivity(Intent.createChooser(emailIntent, "Pošalji e-mail"))
            } catch (ex: Exception) {
                val errorToast = Toast.makeText(requireContext(), "Došlo je do greške prilikom slanja e-maila", Toast.LENGTH_SHORT)
                errorToast.show()
            }
        }

        return view
    }
}
