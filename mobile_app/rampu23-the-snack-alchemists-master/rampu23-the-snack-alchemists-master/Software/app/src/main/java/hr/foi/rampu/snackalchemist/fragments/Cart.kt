package hr.foi.rampu.snackalchemist.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import hr.foi.rampu.snackalchemist.LocationActivityMain
import hr.foi.rampu.snackalchemist.R
import hr.foi.rampu.snackalchemist.adapters.CartAdapter
import hr.foi.rampu.snackalchemist.adapters.CatalogAdapter
import hr.foi.rampu.snackalchemist.dataClases.Product
import hr.foi.rampu.snackalchemist.repositories.UserRepository
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

class Cart : Fragment() {
    private lateinit var btnNaruci : Button
    private lateinit var Cijena : TextView
    private lateinit var  recyclerViewCart : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dohvatiPodatkeProizvode(view)
        btnNaruci=view.findViewById(R.id.btnNaruci)
        btnNaruci.setOnClickListener {
            showLocation()
            dodajNarudzbu()
        }
    }

    private fun dohvatiPodatkeProizvode(view: View) {
        val listaProduct = ArrayList<Product>()
        Cijena = view.findViewById(R.id.tvUkupnaCijena)
        var ukupnaCijena = 0
        listaProduct.clear()
        val kor = UserRepository.prijavljeniKorisnik.KorisnikID
        val baza: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("SnackBaza/Cart/$kor")
        baza.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    listaProduct.clear()
                    if (snapshot.exists()) {
                        for (product in snapshot.children) {
                            val dohvaceniProizvod = product.getValue(Product::class.java)
                            listaProduct.add(dohvaceniProizvod!!)
                            ukupnaCijena = ukupnaCijena + dohvaceniProizvod.cijena!!.toInt()
                        }
                        Cijena.text = ukupnaCijena.toString() + " $"
                        recyclerViewCart = view.findViewById(R.id.rv_cart)
                        recyclerViewCart.adapter = CartAdapter(listaProduct)
                        recyclerViewCart.layoutManager = LinearLayoutManager(view.context)

                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Nema dostupnih podataka",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "Pogreška: ${e.message}", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    requireContext(),
                    "Greška pri dohvatu podataka: ${error.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun dodajNarudzbu(){
        val listaProduct = ArrayList<Product>()
        var ukupnaCijena = 0
        listaProduct.clear()
        val kor = UserRepository.prijavljeniKorisnik.KorisnikID
        val baza: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("SnackBaza/Cart/$kor")
        baza.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    listaProduct.clear()
                    if (snapshot.exists()) {
                        val idNarudzbe = baza.push().key!!
                        val path = UserRepository.prijavljeniKorisnik.KorisnikID
                        val bazanarudzbe: DatabaseReference = FirebaseDatabase.getInstance().getReference("SnackBaza/Narudzba/$path/$idNarudzbe")
                        for (product in snapshot.children) {

                            val dohvaceniProizvod = product.getValue(Product::class.java)
                            ukupnaCijena=ukupnaCijena+ dohvaceniProizvod!!.cijena!!.toInt()
                            listaProduct.add(dohvaceniProizvod)
                        }
                        bazanarudzbe.child("id").setValue(idNarudzbe)
                        bazanarudzbe.child("proizvodi").setValue(listaProduct)
                        bazanarudzbe.child("Datum").setValue(SimpleDateFormat("dd.MM.yyyy").format(Date()))
                        bazanarudzbe.child("Cijena").setValue(ukupnaCijena)
                        baza.removeValue()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Nema dostupnih podataka",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "Pogreška: ${e.message}", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    requireContext(),
                    "Greška pri dohvatu podataka: ${error.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun showLocation(){
        val intent = Intent(requireContext(), LocationActivityMain::class.java)
        startActivity(intent)
    }

}