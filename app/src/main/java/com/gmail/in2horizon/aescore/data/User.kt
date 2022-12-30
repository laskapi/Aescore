package com.gmail.in2horizon.aescore.data

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.HashSet
import kotlin.collections.LinkedHashSet

data class User(
    @SerializedName("id") var id: Long=-1L,
    @SerializedName("username") var username: String="",
    @SerializedName("password") var password: String = "",
    @SerializedName("email") var email:String="",
    @SerializedName("authorities") var authorities: HashSet<Authority> = HashSet(),
 /*       mutableEmptySet emptySet<Authority>(),
 */   @SerializedName("competitions") var competitions: Set<Competition> = emptySet<Competition>()

) : java.io
.Serializable {

    fun isNotEmpty(): Boolean {
        return (id != -1L) &&
                username.isNotEmpty()
                && password.isNotEmpty()
                && email.isNotEmpty()
            //    && authorities.isNotEmpty()
            //    && competitions.isNotEmpty()
    }

    override fun equals(other: Any?): Boolean {

        if (this === other) return true
        return false
    }



    fun isSameAs(other: User): Boolean {
    return hashCode()==other.hashCode()
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + authorities.hashCode()
        result = 31 * result + competitions.hashCode()
        return result
    }


}


