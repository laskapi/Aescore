package com.gmail.in2horizon.aescore.data

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import javax.inject.Inject

class UserRepository @Inject constructor() {
    private val retrofit: Retrofit = RetrofitClient.getRetrofitInstance()
    private val apiService: ApiService = retrofit.create(ApiService::class.java)

    suspend fun login(credentials: String): Call<ResponseBody> {
        RetrofitClient.setCredentials(credentials)
        return apiService.login()

    }

    fun getUsers(): Call<ResponseBody> {
        return apiService.getUsers()
    }
}
