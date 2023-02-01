package com.gmail.in2horizon.aescore

import okhttp3.Credentials

class UserCredentials(username: String, password: String) {

    private val credentials = Credentials.basic(username, password)

    fun getCredentials(): String {
        return credentials
    }


}