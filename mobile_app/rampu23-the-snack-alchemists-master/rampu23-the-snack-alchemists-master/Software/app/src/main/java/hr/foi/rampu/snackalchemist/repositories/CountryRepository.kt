package hr.foi.rampu.snackalchemist.repositories

import hr.foi.rampu.snackalchemist.dataClases.Country

object CountryRepository {
    private val listaDrzava: MutableList<Country> = mutableListOf(
        Country("Hrvatska", 385),
        Country("Srbija", 381),
        Country("Bosna i Hercegovina", 387),
        Country("Slovenija", 386),
        Country("Crna Gora", 382)
    )

    fun dajSveDrzave(): MutableList<Country>{
        return listaDrzava
    }

    fun provjeriDrzavaPozivni(drzava: String, listaDrzava : MutableList<Country>) : Int?{
        for(d in listaDrzava){
            if(drzava == d.naziv){
                return d.pozivniBroj
            }
        }
        return null
    }
}