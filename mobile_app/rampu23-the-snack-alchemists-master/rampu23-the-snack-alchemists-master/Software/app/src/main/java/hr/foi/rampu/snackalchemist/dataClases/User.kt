package hr.foi.rampu.snackalchemist.dataClases

data class User(
    var KorisnikID: String?,
    val ime: String?,
    val prezime: String?,
    val email: String?,
    val drzava: String?,
    val brojMobitela: String?,
    val lozinka: String?,
    val kod: String?,
    val admin: Boolean?
) {
    constructor() : this(null, null, null, null, null, null, null, null, null)
}


