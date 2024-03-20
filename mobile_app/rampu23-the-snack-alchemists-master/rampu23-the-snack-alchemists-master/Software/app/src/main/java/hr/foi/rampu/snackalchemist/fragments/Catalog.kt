package hr.foi.rampu.snackalchemist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import hr.foi.rampu.snackalchemist.R
import hr.foi.rampu.snackalchemist.adapters.CatalogAdapter
import hr.foi.rampu.snackalchemist.dataClases.Product

class Catalog : Fragment() {

    private lateinit var  recyclerViewCatalog : RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_catalog, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dohvatiPodatkeProizvode(view)
    }
    private fun dohvatiPodatkeProizvode(view: View) {
        val listaProduct = ArrayList<Product>()
        listaProduct.clear()

        val baza: DatabaseReference = FirebaseDatabase.getInstance().getReference("SnackBaza/Proizvod")
        baza.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    listaProduct.clear()
                    if (snapshot.exists()) {
                        for (product in snapshot.children) {
                            val dohvaceniKorisnik = product.getValue(Product::class.java)
                            listaProduct.add(dohvaceniKorisnik!!)
                        }
                        recyclerViewCatalog = view.findViewById(R.id.rv_Catalog)
                        recyclerViewCatalog.adapter = CatalogAdapter(context!!,listaProduct)
                        recyclerViewCatalog.layoutManager = LinearLayoutManager(view.context)

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