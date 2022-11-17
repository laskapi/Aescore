package com.gmail.in2horizon.aescore.data

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

public interface ApiService {

    @Headers("Content-Type:application/json")
    @GET("login")
    fun login(): Call<ResponseBody>
    @GET("users")
    fun getUsers(): Call<ResponseBody>


}

