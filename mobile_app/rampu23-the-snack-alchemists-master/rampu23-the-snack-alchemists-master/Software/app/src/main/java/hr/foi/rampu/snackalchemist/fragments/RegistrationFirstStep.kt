package hr.foi.rampu.snackalchemist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import hr.foi.rampu.snackalchemist.R
import hr.foi.rampu.snackalchemist.RegistrationActivity
import hr.foi.rampu.snackalchemist.dataClases.User
import hr.foi.rampu.snackalchemist.repositories.UserRepository

class RegistrationFirstStep : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_registration_first_step, container, false)
        val btnDalje1B = view.findViewById<Button>(R.id.btnDalje1B)

        btnDalje1B.setOnClickListener {
            val ime = view.findViewById<EditText>(R.id.txtIme).text.toString()
            val prezime = view.findViewById<EditText>(R.id.txtPrezime).text.toString()
            val email = view.findViewById<EditText>(R.id.txtEmail).text.toString()

            if (ime.isBlank() || prezime.isBlank() || email.isBlank()) {
                Toast.makeText(requireContext(), "Molimo unesite sve potrebne podatke!", Toast.LENGTH_SHORT).show()
            } else {
                dohvatiKorisnika(ime,prezime,email)
            }
        }
        return view
    }

    private fun dohvatiKorisnika(ime: String, prezime: String, email: String) {
        val listaKorisnika = ArrayList<User>()
        listaKorisnika.clear()

        val baza: DatabaseReference = FirebaseDatabase.getInstance().getReference("SnackBaza/Korisnik")
        baza.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    listaKorisnika.clear()
                    if (snapshot.exists()) {
                        for (korisnik in snapshot.children) {
                            val dohvaceniKorisnik = korisnik.getValue(User::class.java)
                            dohvaceniKorisnik?.let { listaKorisnika.add(it) }
                        }

                        val provjera = UserRepository.provjeraEmailaUNIQUE(email, listaKorisnika)

                        val slovaRegex = Regex("^[a-zA-ZčćžšđČĆŽŠĐ]+\$")
                        val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$")

                        val poruka = when {
                            !ime.matches(slovaRegex) -> "Ime sadrži neispravne znakove!"
                            !prezime.matches(slovaRegex) -> "Prezime sadrži neispravne znakove!"
                            !email.matches(emailRegex) -> "E-mail adresa nije ispravna!"
                            !provjera -> "Uneseni E-mail je već zauzet!"
                            else -> {
                                val bundle = Bundle()
                                bundle.putString("ime", ime)
                                bundle.putString("prezime", prezime)
                                bundle.putString("email", email)

                                val secondStepFragment = RegistrationSecondStep()
                                secondStepFragment.arguments = bundle

                                (activity as RegistrationActivity).replaceFragment(secondStepFragment)
                                return
                            }
                        }
                        Toast.makeText(requireContext(), poruka, Toast.LENGTH_SHORT).show()
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
    }
}
