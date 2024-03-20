package hr.foi.rampu.snackalchemist.repositories

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import hr.foi.rampu.snackalchemist.dataClases.DataBaseService
import hr.foi.rampu.snackalchemist.dataClases.User

object UserRepository {
    lateinit var prijavljeniKorisnik: User

    fun postaviPrijavljenog(user: User){
        prijavljeniKorisnik = user
    }
    fun provjeraAutentifikacije(email: String, lozinka: String, lista: ArrayList<User>) : Boolean {
        for (k in lista) {
            if (email == k.email && lozinka == k.lozinka) {
                return true
            }
        }
        return false
    }

    fun dohvatiKorisnika(email: String?, lista: ArrayList<User>): User? {
        for (k in lista) {
            if (email == k.email) {
                return k
            }
        }
        return null
    }

    fun provjeraEmailaUNIQUE(email: String, lista: ArrayList<User>) : Boolean{
        for (k in lista){
            if(email == k.email){
                return false
            }
        }
        return true
    }
}