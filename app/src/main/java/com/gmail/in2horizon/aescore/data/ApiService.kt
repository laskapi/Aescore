package com.gmail.in2horizon.aescore.data

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Headers

public interface ApiService {

@Headers("Content-Type:application/json")
@GET("login")
fun signin(): retrofit2.Call<ResponseBody>



}

