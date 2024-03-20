package hr.foi.rampu.snackalchemist.dataClases

data class Order(
    val id:String?,
    val Cijena: Int?,
    val Datum:String?,

    var proizvodi:ArrayList<Product>?
){
    constructor() : this(null, null, null,null)
}