package com.gmail.in2horizon.aescore

import com.gmail.in2horizon.aescore.data.User

class LoggedInUser(u: User? = null) {

    private var user = u

    fun get(): User? {
        return user
    }
}