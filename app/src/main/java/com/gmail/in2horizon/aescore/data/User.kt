package com.gmail.in2horizon.aescore.data

data class User(val username:String="", val password:String="", val authority:String=""):java.io.Serializable {

    override fun equals(other: Any?):Boolean{

        if(this===other) return true
        return false
    }

    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + authority.hashCode()
        return result
    }
}