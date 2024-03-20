package hr.foi.rampu.snackalchemist.dataClases

import android.media.Image
import android.net.Uri

data class Product(
    var productID : String?,
    val naziv : String?,
    val opis: String?,
    val kolicina: String?,
    val cijena: Int?,
    val slika: String?
){
    constructor() : this(null, null, null, null, null,null)
}
