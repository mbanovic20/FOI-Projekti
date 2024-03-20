package hr.foi.rampu.snackalchemist.fragments

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.storage
import hr.foi.rampu.snackalchemist.R
import hr.foi.rampu.snackalchemist.adapters.CatalogAdapter
import hr.foi.rampu.snackalchemist.dataClases.DataBaseService
import hr.foi.rampu.snackalchemist.dataClases.Product
import java.io.InputStream
import com.google.firebase.storage.component1
import com.google.firebase.storage.component2


class CatalogAdmin : Fragment() {

    private lateinit var recyclerViewCatalogAdmin:RecyclerView
    private lateinit var btnAddProduct:FloatingActionButton

    private lateinit var createdProductImage: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_catalog_admin, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        dohvatiPodatkeProizvode(view)
        btnAddProduct=view.findViewById(R.id.btnAddProduct)
        btnAddProduct.setOnClickListener {
            showDialog()
        }
    }
    private fun showDialog() {
        val builder = AlertDialog.Builder(context,0)
        val dialog = builder.create()

        val addProductview = LayoutInflater.from(context).inflate(R.layout.add_new_product, null)
        val btnAddNewProduct= addProductview.findViewById<Button>(R.id.btnAddNewProduct)

        val btnDodajSliku =addProductview.findViewById<Button>(R.id.btnDodajSliku)

        dialog.setView(addProductview)
        dialog.setCanceledOnTouchOutside(false)
        btnDodajSliku.setOnClickListener {
            val intent=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent,3)

        }
        btnAddNewProduct.setOnClickListener {

            val newnaziv = addProductview.findViewById<EditText>(R.id.txtNaziv).text.toString()
            val newopis = addProductview.findViewById<EditText>(R.id.txtOpis).text.toString()
            val newkolicina = addProductview.findViewById<EditText>(R.id.txtKolicina).text.toString()
            val newcijena = addProductview.findViewById<EditText>(R.id.txtCijena).text.toString().toInt()
            val slika = createdProductImage

            val createdProduct = Product(null,newnaziv,newopis,newkolicina,newcijena,slika)

            val catalogAdapter = (recyclerViewCatalogAdmin.adapter as CatalogAdapter)
            catalogAdapter.addProduct(createdProduct)

            var service = DataBaseService()
            service.dodajProduct("Proizvod",createdProduct)

            dialog.dismiss()

        }
        dialog.show()


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            val selectedImage: Uri? = data.getData()
            if (selectedImage != null) {
                val baza = Firebase.storage.reference.child("images/${selectedImage.lastPathSegment}")
                Log.d("proslo","$selectedImage")

                val contentResolver = requireActivity().contentResolver
                val inputStream: InputStream? = contentResolver.openInputStream(selectedImage)
                createdProductImage = "${selectedImage.lastPathSegment}"

                if (inputStream != null) {
                    Log.d("proslo","uslo")
                    baza.putFile(selectedImage)
                        .addOnProgressListener { (bytesTransferred, totalByteCount) ->
                            val progress = (100.0 * bytesTransferred) / totalByteCount
                            Log.d("proslo", "Upload is $progress% done")}
                        .addOnSuccessListener { taskSnapshot ->
                            Log.d("proslo","proslo")

                        }
                        .addOnFailureListener { exception ->
                            Log.d("proslo", "nije proslo")
                        }
                        .addOnPausedListener {
                            Log.d("proslo", "Upload is paused")}

                } else {
                    Log.d("proslo","nije dohvacena slika")
                }

            } else {
                Log.d("proslo","uri null")
            }
        }
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
                        recyclerViewCatalogAdmin = view.findViewById(R.id.rv_CatalogAdmin)
                        recyclerViewCatalogAdmin.adapter=CatalogAdapter(context!!,listaProduct)
                        recyclerViewCatalogAdmin.layoutManager = LinearLayoutManager(view.context)

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