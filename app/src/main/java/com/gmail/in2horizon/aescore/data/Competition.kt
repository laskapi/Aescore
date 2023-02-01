package com.gmail.in2horizon.aescore.data

import com.google.gson.annotations.SerializedName

data class Competition(
    @SerializedName("id")val id:Long=-1L,
    @SerializedName("name")val name:String="",
    @SerializedName("admin") val admin:User?=null,
    @SerializedName("users") val users:MutableList<User> = mutableListOf()) {

}