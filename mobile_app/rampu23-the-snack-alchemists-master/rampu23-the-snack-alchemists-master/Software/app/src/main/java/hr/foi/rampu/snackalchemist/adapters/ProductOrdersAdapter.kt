package hr.foi.rampu.snackalchemist.adapters

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

class ProductOrdersAdapter(private val productList:ArrayList<Product>):RecyclerView.Adapter<ProductOrdersAdapter.ProdactOrdersViewHolder>() {
    inner class ProdactOrdersViewHolder(view: View):RecyclerView.ViewHolder(view){
        private val productNaziv: TextView
        private val productOpis: TextView
        private val productKolcina: TextView
        private val productCijena: TextView
        private val productSlika: ImageView
        init {

            productNaziv = view.findViewById(R.id.naziv_orders)
            productOpis = view.findViewById(R.id.opis_orders)
            productKolcina =view.findViewById(R.id.količina_orders)
            productCijena =view.findViewById(R.id.cijena_orders)
            productSlika=view.findViewById(R.id.iv_slika_orders)
        }

        fun bind(product: Product) {
            productNaziv.text = product.naziv
            productOpis.text = product.opis
            productKolcina.text = product.kolicina
            productCijena.text= product.cijena.toString()+" $"
            var baza= Firebase.storage.reference.child("images/${product.slika}")
            baza.getBytes(1024*1024).addOnSuccessListener { bytes ->
                // Konvertiraj bytes u Bitmap
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

                // Postavi Bitmap u ImageView
                productSlika.setImageBitmap(bitmap)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdactOrdersViewHolder {
        val CatalogView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.product_layout_orders, parent, false)
        return ProdactOrdersViewHolder(CatalogView)
    }
    override fun getItemCount() = productList.size
    override fun onBindViewHolder(holder: ProdactOrdersViewHolder, position: Int) {
        holder.bind(productList[position])
    }

}