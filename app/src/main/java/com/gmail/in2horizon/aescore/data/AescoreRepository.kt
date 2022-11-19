package com.gmail.in2horizon.aescore.data

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject

class AescoreRepository @Inject constructor() {
    private val retrofit: Retrofit = RetrofitClient.getRetrofitInstance()
    private val apiService: ApiService = retrofit.create(ApiService::class.java)

  fun login(credentials: String): Call<UserModel>{
        RetrofitClient.setCredentials(credentials)
        return apiService.login()

    }

    fun getUsers(): Call<ResponseBody> {
        return apiService.getUsers()
    }
}
