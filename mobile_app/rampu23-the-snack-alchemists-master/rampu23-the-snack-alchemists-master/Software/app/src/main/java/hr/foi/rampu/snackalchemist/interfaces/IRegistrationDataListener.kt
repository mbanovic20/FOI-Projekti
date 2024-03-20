package hr.foi.rampu.snackalchemist.interfaces

import hr.foi.rampu.snackalchemist.dataClases.User

interface IRegistrationDataListener {
    fun onRegistrationDataEntered(user: User)
}