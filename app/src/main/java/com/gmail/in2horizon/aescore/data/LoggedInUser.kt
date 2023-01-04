package com.gmail.in2horizon.aescore.data

class LoggedInUser(u: User? = null) {

    private var user = u

    fun get(): User? {
        return user
    }
}