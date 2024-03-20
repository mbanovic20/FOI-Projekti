package hr.foi.rampu.snackalchemist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import hr.foi.rampu.snackalchemist.fragments.promoCodeErrorDialog
import hr.foi.rampu.snackalchemist.fragments.promoCodeSuccessDialog

class PromoCodeActivity : AppCompatActivity() {
    private val validCodes = listOf("CODE2023", "CODE10", "CODE20", "CODE30")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promocodes)

        val imageButtonOnClick = findViewById<ImageButton>(R.id.backAPC)
        imageButtonOnClick.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.submitPromoCode)
            .setOnClickListener {
                val inputCode = findViewById<TextInputLayout>(R.id.textInputPromoCode).editText?.text.toString()
                var exist = false
                for(validCode in validCodes) {
                    if(inputCode == validCode) {
                        exist = true
                    }
                }
                if(exist) {
                    promoCodeSuccessDialog().show(supportFragmentManager, "Success_dialog")
                } else {
                    promoCodeErrorDialog().show(supportFragmentManager, "Error_dialog")
                }

            }

    }

}
