package hr.foi.rampu.snackalchemist.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import hr.foi.rampu.snackalchemist.MainActivity
import hr.foi.rampu.snackalchemist.R
import hr.foi.rampu.snackalchemist.RegistrationActivity
import hr.foi.rampu.snackalchemist.dataClases.DataBaseService
import hr.foi.rampu.snackalchemist.dataClases.User

class RegistrationThirdStep : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_registration_third_step, container, false)

        val bundle = arguments
        val ime = bundle?.getString("ime", "")
        val prezime = bundle?.getString("prezime", "")
        val email = bundle?.getString("email", "")
        val drzava = bundle?.getString("drzava", "")
        val brojMobitela = bundle?.getString("brojMobitela", "")

        val btnRegistrirajSe = view.findViewById<Button>(R.id.btnRegistrirajSeB)
        btnRegistrirajSe.setOnClickListener {
            val lozinka = view.findViewById<EditText>(R.id.txtLozinkaB).text.toString()
            val lozinkaPonovi = view.findViewById<EditText>(R.id.txtPonoviLozinkaB).text.toString()
            val chkPrivatnost = view.findViewById<CheckBox>(R.id.checkBoxPrivatnostB)

            val lozinkaRegex = Regex("^(?=.*[A-Za-zčćžšđČĆŽŠĐ])(?=.*\\d)[A-Za-zčćžšđČĆŽŠĐ\\d]{8,}\$")

            if(chkPrivatnost.isChecked) {
                when {
                    lozinka.isBlank() || lozinkaPonovi.isBlank() -> {
                        Toast.makeText(
                            requireContext(),
                            "Molimo unesite sve potrebne podatke!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    lozinka != lozinkaPonovi -> {
                        Toast.makeText(
                            requireContext(),
                            "Lozinke se ne podudaraju!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    !lozinka.matches(lozinkaRegex) -> {
                        Toast.makeText(
                            requireContext(),
                            "Lozinka mora sadržavati minimalno 8 znakova, barem jedno slovo i barem jednu brojku",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {
                        val servis = DataBaseService()
                        val kod = servis.generateRandomCode()

                        val lastFragmentBundle = Bundle().apply {
                            putString("KorisnikID", null)
                            putString("ime", ime)
                            putString("prezime", prezime)
                            putString("email", email)
                            putString("drzava", drzava)
                            putString("brojMobitela", brojMobitela)
                            putString("lozinka", lozinka)
                            putString("kod", kod)
                            putBoolean("isAdmin", false)
                        }

                        val codeValidationFragment = CodeValidation()
                        codeValidationFragment.arguments = lastFragmentBundle

                        (activity as RegistrationActivity).replaceFragment(codeValidationFragment)
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Molimo da prihvatite pravila privatnosti", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }


}