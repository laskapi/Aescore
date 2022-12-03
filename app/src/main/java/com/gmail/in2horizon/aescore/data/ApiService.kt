package com.gmail.in2horizon.aescore.data

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

import kotlin.collections.HashSet

public interface ApiService {

    @Headers("Content-Type:application/json")
    @GET("login")
    fun login(): Call<User>

    @GET("users")
    fun getUsers(): Call<List<User>>

    @DELETE("users/{id}")
    fun deleteUser(@Path("id") id: Long): Call<List<String>>

    @PUT(value = "users")
    fun updateUser(@Body user: User): Call<List<User>>

    @GET(value = "authorities")
    fun getAuthorities(): Call<LinkedHashSet<Authority>>

/*
@HTTP(method="DELETE",path="users",hasBody = true)
    fun deleteUser(@Body user:User):Call<User>
*/

}

