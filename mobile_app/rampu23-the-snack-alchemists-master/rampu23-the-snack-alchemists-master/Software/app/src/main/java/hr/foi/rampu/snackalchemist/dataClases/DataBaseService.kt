package hr.foi.rampu.snackalchemist.dataClases

import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import hr.foi.rampu.snackalchemist.repositories.UserRepository
import java.util.Objects
import kotlin.math.log
import java.util.Random


class DataBaseService {
    fun dodajKorisnika(path: String, korisnik: User ) {
        val baza: DatabaseReference = FirebaseDatabase.getInstance().getReference("SnackBaza/$path")

        val id = baza.push().key!!
        korisnik.KorisnikID = id
        baza.child(id).setValue(korisnik).addOnCompleteListener{
            Log.d("Tag","dodano u bazu")
        }

        UserRepository.postaviPrijavljenog(korisnik)

        stvoriCart(id)
    }

    fun stvoriCart(id: String) {
        val baza: DatabaseReference = FirebaseDatabase.getInstance().getReference("SnackBaza/Cart")

        val id_korisnika = id
        baza.child(id_korisnika).setValue("").addOnCompleteListener{
            Log.d("Tag","dodano na bazu")
        }
    }

    fun generateRandomCode(length: Int = 6): String {
        val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        val random = Random()
        return (1..length)
            .map { random.nextInt(characters.length) }
            .map(characters::get)
            .joinToString("")
    }

    fun dodajProduct(path: String, product: Product ) {
        val baza: DatabaseReference = FirebaseDatabase.getInstance().getReference("SnackBaza/$path")

        val id = baza.push().key!!
        product.productID = id
        baza.child(id).setValue(product).addOnCompleteListener{
            Log.d("Tag","dodano na bazu")
        }
    }
}