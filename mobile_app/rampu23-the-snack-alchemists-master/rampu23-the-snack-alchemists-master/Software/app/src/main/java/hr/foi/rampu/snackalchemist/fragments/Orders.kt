package hr.foi.rampu.snackalchemist.fragments

import android.os.Bundle
import android.util.Log
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
import hr.foi.rampu.snackalchemist.adapters.OrdersAdapter
import hr.foi.rampu.snackalchemist.dataClases.Order
import hr.foi.rampu.snackalchemist.dataClases.Product
import hr.foi.rampu.snackalchemist.repositories.UserRepository

class Orders : Fragment() {
    private lateinit var  recyclerViewOrders : RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_orders, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dohvatiPodatkeProizvode(view)
    }

    private fun dohvatiPodatkeProizvode(view: View) {
        val path=UserRepository.prijavljeniKorisnik.KorisnikID
        val baza: DatabaseReference = FirebaseDatabase.getInstance().getReference("SnackBaza/Narudzba/$path")
        baza.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    if (snapshot.exists()) {
                        val listaOrders = ArrayList<Order>()
                        for (order in snapshot.children) {
                            val dohvaceniOrders = order.getValue(Order::class.java)
                            val listaProduct = ArrayList<Product>()

                            val bazanarudzba: DatabaseReference = FirebaseDatabase.getInstance().getReference("SnackBaza/Narudzba/$path/${dohvaceniOrders!!.id}/proizvodi")
                            bazanarudzba.addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    try {
                                        if (snapshot.exists()) {
                                            for (product in snapshot.children) {
                                                val dohvaceniProduct = product.getValue(Product::class.java)
                                                listaProduct.add(dohvaceniProduct!!)
                                            }
                                            dohvaceniOrders.proizvodi = listaProduct
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

                            listaOrders.add(dohvaceniOrders)
                        }

                        recyclerViewOrders = view.findViewById(R.id.rv_orders)
                        recyclerViewOrders.adapter = OrdersAdapter(listaOrders)
                        recyclerViewOrders.layoutManager = LinearLayoutManager(view.context)

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