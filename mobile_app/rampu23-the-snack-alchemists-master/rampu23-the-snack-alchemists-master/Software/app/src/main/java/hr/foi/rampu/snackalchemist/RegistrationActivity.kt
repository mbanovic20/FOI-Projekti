package hr.foi.rampu.snackalchemist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import hr.foi.rampu.snackalchemist.fragments.RegistrationFirstStep

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val firstStepFragment = RegistrationFirstStep()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, firstStepFragment)
            .commit()
    }

    fun prikaziPrijavu(view : View){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
