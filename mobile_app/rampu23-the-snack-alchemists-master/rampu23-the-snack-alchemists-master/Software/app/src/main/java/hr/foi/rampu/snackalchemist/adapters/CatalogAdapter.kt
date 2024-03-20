package hr.foi.rampu.snackalchemist.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.storage
import hr.foi.rampu.snackalchemist.R
import hr.foi.rampu.snackalchemist.dataClases.Product
import hr.foi.rampu.snackalchemist.repositories.UserRepository
import kotlinx.coroutines.withContext

class CatalogAdapter(private val context: Context,private val productList:ArrayList<Product>):RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder>() {
    inner class CatalogViewHolder(view: View):RecyclerView.ViewHolder(view){
        private val productNaziv: TextView
        private val productOpis: TextView
        private val productKolcina: TextView
        private val productCijena: TextView
        private val productSlika: ImageView
        private val productBtnNaruci: Button
        init {

            productNaziv = view.findViewById(R.id.naziv)
            productOpis = view.findViewById(R.id.opis)
            productKolcina =view.findViewById(R.id.količina)
            productCijena =view.findViewById(R.id.cijena)
            productSlika=view.findViewById(R.id.iv_slika)
            productBtnNaruci=view.findViewById(R.id.btnProductLayout)
            productBtnNaruci.setOnClickListener {
                val product=Product(productList[adapterPosition].productID,productList[adapterPosition].naziv,productList[adapterPosition].opis,productList[adapterPosition].kolicina,productList[adapterPosition].cijena,productList[adapterPosition].slika)
                dodajProduct(product)
            }


        }
        fun bind(product: Product) {
            productNaziv.text = product.naziv
            productOpis.text = product.opis
            productKolcina.text = product.kolicina
            productCijena.text= product.cijena.toString()+" $"
            var baza= Firebase.storage.reference.child("images/${product.slika}")
            baza.getBytes(1024*1024).addOnSuccessListener { bytes ->
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

                productSlika.setImageBitmap(bitmap)
            }


        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        val CatalogView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.product_layout, parent, false)
        return CatalogViewHolder(CatalogView)
    }
    override fun getItemCount() = productList.size
    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        holder.bind(productList[position])
    }
    fun addProduct(newProduct : Product) {
        var newIndexInList = productList.size
        productList.add(newIndexInList, newProduct)
        notifyItemInserted(newIndexInList)
    }
    fun selecetProduct( position: Int):Product{
        val product=Product(productList[position].productID,productList[position].naziv,productList[position].opis,productList[position].kolicina,productList[position].cijena,productList[position].slika)
        return product
    }
    fun dodajProduct(product: Product) {
        val path = UserRepository.prijavljeniKorisnik.KorisnikID
        val baza: DatabaseReference = FirebaseDatabase.getInstance().getReference("SnackBaza/Cart/$path")
        val id=product.productID.toString()
        baza.child(id).setValue(product).addOnCompleteListener{
            Log.d("Tag","dodano na bazu")
        }
    }

}