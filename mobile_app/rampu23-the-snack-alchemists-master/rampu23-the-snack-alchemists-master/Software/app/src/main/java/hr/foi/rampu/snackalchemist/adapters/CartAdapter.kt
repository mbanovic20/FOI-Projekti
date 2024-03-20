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

class CartAdapter(private val productList:ArrayList<Product>):RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    inner class CartViewHolder(view: View):RecyclerView.ViewHolder(view){
        private val productNaziv: TextView
        private val productOpis: TextView
        private val productKolcina: TextView
        private val productCijena: TextView
        private val productSlika: ImageView
        private val productBtnDelte: Button
        init {

            productNaziv = view.findViewById(R.id.nazivCart)
            productOpis = view.findViewById(R.id.opisCart)
            productKolcina =view.findViewById(R.id.količinaCart)
            productCijena =view.findViewById(R.id.cijenaCart)
            productSlika=view.findViewById(R.id.iv_slikaCart)
            productBtnDelte=view.findViewById(R.id.btnProductDelete)
            productBtnDelte.setOnClickListener {
                val product=Product(productList[adapterPosition].productID,productList[adapterPosition].naziv,productList[adapterPosition].opis,productList[adapterPosition].kolicina,productList[adapterPosition].cijena,productList[adapterPosition].slika)
                izbrisiProduct(product)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val CartView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.prodcut_layout_cart, parent, false)
        return CartViewHolder(CartView)
    }
    override fun getItemCount() = productList.size
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(productList[position])
    }
    fun addProduct(newProduct : Product) {
        var newIndexInList = productList.size
        productList.add(newIndexInList, newProduct)
        notifyItemInserted(newIndexInList)
    }
    fun izbrisiProduct(product: Product) {
        val kor = UserRepository.prijavljeniKorisnik.KorisnikID
        val baza: DatabaseReference = FirebaseDatabase.getInstance().getReference("SnackBaza/Cart/$kor")
        val id=product.productID.toString()
        baza.child(id).removeValue().addOnCompleteListener{
            Log.d("Tag","dodano na bazu")
        }
    }

}