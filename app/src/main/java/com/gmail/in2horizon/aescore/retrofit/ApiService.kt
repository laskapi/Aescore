package com.gmail.in2horizon.aescore.retrofit

import com.gmail.in2horizon.aescore.data.Authority
import com.gmail.in2horizon.aescore.data.Competition
import com.gmail.in2horizon.aescore.data.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Headers("Content-Type:application/json")
    @GET("login")
    fun login(): Call<User>

    @GET("super/users")
    fun getUsers(): Call<List<User>>

    @GET("super/users/{id}")
    fun getUser(@Path("id") id: Long): Call<User>

    @POST("super/users")
    fun addUser(@Body user: User): Call<ResponseBody>

    @PUT("super/users")
    fun updateUser(@Body user: User): Call<ResponseBody>

    @DELETE("super/users/{id}")
    fun deleteUser(@Path("id") id: Long): Call<Void>




    @GET("super/authorities")
    fun getAuthorities(): Call<LinkedHashSet<Authority>>




    @GET("super/competitions")
    fun getCompetitions(): Call<List<Competition>>

    @GET("super/competitions/{id}")
    fun getCompetition(@Path("id") id: Long): Call<Competition>

    @DELETE("super/competitions/{id}")
    fun deleteCompetition(@Path("id") id: Long): Call<Void>

    @POST("super/competitions")
    fun addCompetition(@Body comp:Competition):Call<ResponseBody>

    @PUT("super/competitions")
    fun updateCompetition(@Body comp: Competition): Call<ResponseBody>

/*
@HTTP(method="DELETE",path="users",hasBody = true)
    fun deleteUser(@Body user:User):Call<User>
*/

}

