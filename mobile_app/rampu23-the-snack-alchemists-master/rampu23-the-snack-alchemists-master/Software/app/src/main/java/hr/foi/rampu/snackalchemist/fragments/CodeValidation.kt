package hr.foi.rampu.snackalchemist.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import hr.foi.rampu.snackalchemist.MainActivity
import hr.foi.rampu.snackalchemist.R
import hr.foi.rampu.snackalchemist.dataClases.DataBaseService
import hr.foi.rampu.snackalchemist.dataClases.User

class CodeValidation : Fragment() {
    private lateinit var etCode1: EditText
    private lateinit var etCode2: EditText
    private lateinit var etCode3: EditText
    private lateinit var etCode4: EditText
    private lateinit var etCode5: EditText
    private lateinit var etCode6: EditText
    private var preneseniKod: String? = null

    private var ime: String? = null
    private var prezime: String? = null
    private var email: String? = null
    private var drzava: String? = null
    private var brojMobitela: String? = null
    private var lozinka: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_code_validation, container, false)

        val bundle = arguments
        preneseniKod = bundle?.getString("kod", "")
        ime = bundle?.getString("ime", "") ?: ""
        prezime = bundle?.getString("prezime", "") ?: ""
        email = bundle?.getString("email", "") ?: ""
        drzava = bundle?.getString("drzava", "") ?: ""
        brojMobitela = bundle?.getString("brojMobitela", "") ?: ""
        lozinka = bundle?.getString("lozinka","") ?: ""

        val toastMessage = "Preneseni podaci:\nKod: $preneseniKod"
        Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_LONG).show()

        val etCode1 = view.findViewById<EditText>(R.id.etCode1)
        val etCode2 = view.findViewById<EditText>(R.id.etCode2)
        val etCode3 = view.findViewById<EditText>(R.id.etCode3)
        val etCode4 = view.findViewById<EditText>(R.id.etCode4)
        val etCode5 = view.findViewById<EditText>(R.id.etCode5)
        val etCode6 = view.findViewById<EditText>(R.id.etCode6)

        setupTextWatcher(etCode1, etCode2)
        setupTextWatcher(etCode2, etCode3)
        setupTextWatcher(etCode3, etCode4)
        setupTextWatcher(etCode4, etCode5)
        setupTextWatcher(etCode5, etCode6)

        etCode6.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (etCode1.text.length == 1 && etCode2.text.length == 1 &&
                    etCode3.text.length == 1 && etCode4.text.length == 1 &&
                    etCode5.text.length == 1 && s != null && s.length == 1) {

                    val uneseniKod = etCode1.text.toString() + etCode2.text.toString() +
                            etCode3.text.toString() + etCode4.text.toString() +
                            etCode5.text.toString() + etCode6.text.toString()

                    Toast.makeText(requireContext(), "Nakon unosa - $uneseniKod", Toast.LENGTH_SHORT).show()
                    provjeriKod(uneseniKod)
                }
            }
        })

        return view
    }

    private fun setupTextWatcher(currentEditText: EditText, nextEditText: EditText?) {
        currentEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s != null && s.length == 1) {
                    nextEditText?.requestFocus()
                }
            }
        })
    }

    private fun provjeriKod(uneseniKod: String) {
        if (uneseniKod == preneseniKod) {
            Toast.makeText(requireContext(), "Kod je točan!", Toast.LENGTH_SHORT).show()
            val newUser = User(null, ime, prezime, email, drzava, brojMobitela, lozinka, uneseniKod, admin = false)
            val servis = DataBaseService()

            servis.dodajKorisnika("Korisnik", newUser)

            val intent = Intent(requireActivity(), MainActivity::class.java)
            intent.putExtra("email", email)
            startActivity(intent)
        } else {
            Toast.makeText(requireContext(), "Kod nije točan, pokušajte ponovno.", Toast.LENGTH_SHORT).show()
        }
    }
}