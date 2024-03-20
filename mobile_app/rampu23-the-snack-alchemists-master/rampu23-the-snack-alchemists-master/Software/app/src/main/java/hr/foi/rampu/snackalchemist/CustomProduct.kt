package hr.foi.rampu.snackalchemist

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
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
import hr.foi.rampu.snackalchemist.dataClases.DataBaseService
import hr.foi.rampu.snackalchemist.dataClases.Product
import hr.foi.rampu.snackalchemist.repositories.UserRepository

class CustomProduct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_product)
        val btnDodaj = findViewById<Button>(R.id.buttonDodajProizvod)

        val spinerS1 = findViewById<Spinner>(R.id.spinnerSastojak1)
        val spinerS2 = findViewById<Spinner>(R.id.spinnerSastojak2)
        val spinerS3 = findViewById<Spinner>(R.id.spinnerSastojak3)

        val spinerK1 =findViewById<Spinner>(R.id.spinnerKolicina1)
        val spinerK2 =findViewById<Spinner>(R.id.spinnerKolicina2)
        val spinerK3 =findViewById<Spinner>(R.id.spinnerKolicina3)
        val kolicinaList = mutableListOf<String>()
        kolicinaList.add("100 g")
        kolicinaList.add("200 g")
        kolicinaList.add("300 g")

        val sastojakList = mutableListOf<String>()

        val baza: DatabaseReference = FirebaseDatabase.getInstance().getReference("SnackBaza/Sastojci/")
        baza.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    if (snapshot.exists()) {
                        for (sastojak in snapshot.children) {
                            val email = sastojak.child("naziv").getValue(String::class.java)
                            email?.let {
                                sastojakList.add(it)
                            }
                        }

                        val adapter = ArrayAdapter(
                            applicationContext,
                            android.R.layout.simple_spinner_item,
                            sastojakList
                        )

                        val adapter2 = ArrayAdapter(
                            applicationContext,
                            android.R.layout.simple_spinner_item,
                            kolicinaList
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinerS1.adapter = adapter
                        spinerS2.adapter = adapter
                        spinerS3.adapter = adapter

                        spinerK1.adapter =adapter2
                        spinerK2.adapter =adapter2
                        spinerK3.adapter =adapter2

                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Nema dostupnih podataka",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(applicationContext, "Pogreška: ${e.message}", Toast.LENGTH_LONG)
                        .show()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Greška pri dohvatu podataka: ${error.message}", Toast.LENGTH_LONG).show()
            }
        })

        btnDodaj.setOnClickListener {
            var newopis = spinerS1.selectedItem.toString() +" "+"\n"+spinerS2.selectedItem.toString() +" "+"\n"+spinerS3.selectedItem.toString()
            val kor = UserRepository.prijavljeniKorisnik.KorisnikID
            var kolicina = spinerK1.selectedItem.toString().split(" ")[0].toInt()+spinerK2.selectedItem.toString().split(" ")[0].toInt()+spinerK3.selectedItem.toString().split(" ")[0].toInt()

            val createdProduct = Product(null,"Vlastiti Mix",newopis,kolicina.toString(),0,"custom.png")
            var service = DataBaseService()
            service.dodajProduct("Cart/$kor",createdProduct)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}