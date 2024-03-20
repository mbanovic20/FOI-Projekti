package hr.foi.rampu.snackalchemist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import hr.foi.rampu.snackalchemist.R
import hr.foi.rampu.snackalchemist.RegistrationActivity
import hr.foi.rampu.snackalchemist.dataClases.Country
import hr.foi.rampu.snackalchemist.repositories.CountryRepository

class RegistrationSecondStep : Fragment() {

    private lateinit var brojMobitelaEditText: EditText
    private lateinit var spinnerDrzave: Spinner
    private var pozivniBrojSelected: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_registration_second_step, container, false)

        val bundle = arguments
        val ime = bundle?.getString("ime", "")
        val prezime = bundle?.getString("prezime", "")
        val email = bundle?.getString("email", "")

        val listaDrzava = CountryRepository.dajSveDrzave()
        spinnerDrzave = view.findViewById(R.id.spinnerDrzave)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listaDrzava.map { it.naziv })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDrzave.adapter = adapter

        brojMobitelaEditText = view.findViewById(R.id.txtBrojMobitela)

        spinnerDrzave.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                azurirajBrojMobitela(listaDrzava)
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }

        val btnDalje2B = view.findViewById<Button>(R.id.btnDalje2B)
        btnDalje2B.setOnClickListener {
            val drzavaNaziv = spinnerDrzave.selectedItem.toString()
            val brojMobitela = brojMobitelaEditText.text.toString()

            val formattedBrojMobitela = resources.getString(R.string.format_broj_mobitela_kon, pozivniBrojSelected, brojMobitela)
            brojMobitelaEditText.setText(formattedBrojMobitela)

            val brojMobitelaRegex = Regex("^[0-9+ ]+$")

            when {
                brojMobitela.isBlank() -> {
                    Toast.makeText(requireContext(), "Molimo unesite sve potrebne podatke!", Toast.LENGTH_SHORT).show()
                }
                !brojMobitela.matches(brojMobitelaRegex) -> {
                    Toast.makeText(requireContext(), "Broj mobitela mora sadržavati samo brojeve!", Toast.LENGTH_SHORT).show()
                }
                brojMobitela.length !in 14..15 -> {
                    Toast.makeText(requireContext(), "Broj mobitela mora imati točno 9 ili 10 dodanih znamenki na prefiks!", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val nextFragmentBundle = Bundle().apply {
                        putString("ime", ime)
                        putString("prezime", prezime)
                        putString("email", email)
                        putString("drzava", drzavaNaziv)
                        putString("brojMobitela", formattedBrojMobitela)
                    }

                    val thirdStepFragment = RegistrationThirdStep()
                    thirdStepFragment.arguments = nextFragmentBundle

                    (activity as RegistrationActivity).replaceFragment(thirdStepFragment)
                }
            }
        }

        return view
    }

    private fun azurirajBrojMobitela(listaDrzava : MutableList<Country>) {
        brojMobitelaEditText.text.clear()
        val drzava = spinnerDrzave.selectedItem.toString()
        val pozivniBrojSelected = CountryRepository.provjeriDrzavaPozivni(drzava, listaDrzava)
        val brojMobitela = brojMobitelaEditText.text.toString()

        val formattedBrojMobitela = resources.getString(R.string.format_broj_mobitela, pozivniBrojSelected, brojMobitela)
        brojMobitelaEditText.setText(formattedBrojMobitela)
    }
}
