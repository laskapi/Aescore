package com.gmail.in2horizon.aescore.data

import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject

class AescoreRepository @Inject constructor() {
    private val retrofit: Retrofit = RetrofitClient.getRetrofitInstance()
    private val apiService: ApiService = retrofit.create(ApiService::class.java)


  fun login(credentials: String): Call<User>{

        RetrofitClient.setCredentials(credentials)
        return apiService.login()

    }

    fun getUsers(): Call<List<User>> {
        return apiService.getUsers()
    }

    fun getUser(id: Long):Call<User> {
        return apiService.getUser(id)
    }

    fun addUser(user: User): Call<ResponseBody> {
        return apiService.addUser(user)
    }

    fun updateUser(user: User):Call<User> {
        return apiService.updateUser(user)
    }

    fun deleteUser(id: Long) :Call<Void>{
        return apiService.deleteUser(id)
    }


    fun getAuthorities(): Call<LinkedHashSet<Authority>> {
        return apiService.getAuthorities()
    }



}
