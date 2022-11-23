package com.gmail.in2horizon.aescore.data

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject

class AescoreRepository @Inject constructor() {
    private val retrofit: Retrofit = RetrofitClient.getRetrofitInstance()
    private val apiService: ApiService = retrofit.create(ApiService::class.java)

<<<<<<< HEAD
  fun login(credentials: String): Call<User>{


=======
  fun login(credentials: String): Call<UserModel>{
>>>>>>> f1e5104e35007d69342a40fef3ece6f3a58f48d7
        RetrofitClient.setCredentials(credentials)
        return apiService.login()

    }

    fun getUsers(): Call<List<User>> {
        return apiService.getUsers()
    }
}
