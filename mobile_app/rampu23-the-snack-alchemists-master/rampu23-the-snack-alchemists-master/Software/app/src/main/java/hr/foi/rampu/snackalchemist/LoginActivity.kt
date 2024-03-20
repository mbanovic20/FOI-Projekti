package hr.foi.rampu.snackalchemist

import android.content.Intent
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import hr.foi.rampu.snackalchemist.fragments.Login


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginFragment = Login()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, loginFragment)
            .commit()
    }

    fun prikaziRegistraciju(view : View){
        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)
    }

    fun prikaziMain(view : View, email : String){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("email", email)
        startActivity(intent)
    }

    fun prikaziMainAdmin(email : String){
        val intent = Intent(this, MainActivityAdmin::class.java)
        intent.putExtra("email", email)
        startActivity(intent)
    }
}