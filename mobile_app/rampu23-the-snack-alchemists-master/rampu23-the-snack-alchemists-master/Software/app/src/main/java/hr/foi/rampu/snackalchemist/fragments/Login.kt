package hr.foi.rampu.snackalchemist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import hr.foi.rampu.snackalchemist.LoginActivity
import hr.foi.rampu.snackalchemist.R
import hr.foi.rampu.snackalchemist.dataClases.User
import hr.foi.rampu.snackalchemist.repositories.UserRepository

class Login : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val btnPrijaviSe = view.findViewById<Button>(R.id.btnPrijaviSe)

        btnPrijaviSe.setOnClickListener {
            val email = view.findViewById<EditText>(R.id.txtEmailLogin).text.toString()
            val lozinka = view.findViewById<EditText>(R.id.txtLozinkaLogin).text.toString()

            if (email.isBlank() || lozinka.isBlank()) {
                Toast.makeText(requireContext(), "Molimo unesite sve potrebne podatke!", Toast.LENGTH_SHORT).show()
            } else {
                dohvatiPodatkeKorisnika(email, lozinka)
            }
        }

        return view
    }

    private fun dohvatiPodatkeKorisnika(email: String, lozinka: String) {
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
                            listaKorisnika.add(dohvaceniKorisnik!!)
                        }
                        Toast.makeText(requireContext(), listaKorisnika[3].toString(), Toast.LENGTH_LONG).show()

                        val provjera = UserRepository.provjeraAutentifikacije(email, lozinka, listaKorisnika)
                        val dohvatiKorisnika = UserRepository.dohvatiKorisnika(email, listaKorisnika)

                        if (provjera) {
                            Toast.makeText(requireContext(), "Uspješna autentifikacija!", Toast.LENGTH_LONG).show()
                            if (dohvatiKorisnika?.admin == true) {
                                UserRepository.postaviPrijavljenog(dohvatiKorisnika)
                                (requireActivity() as LoginActivity).prikaziMainAdmin(email)
                            } else {
                                UserRepository.postaviPrijavljenog(dohvatiKorisnika!!)
                                (requireActivity() as LoginActivity).prikaziMain(requireView(), email)
                            }
                        } else {
                            Toast.makeText(requireContext(), "Neispravni podaci", Toast.LENGTH_LONG).show()
                        }
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
