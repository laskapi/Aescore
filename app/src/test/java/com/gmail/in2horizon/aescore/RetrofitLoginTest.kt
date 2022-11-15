package com.gmail.in2horizon.aescore

import com.gmail.in2horizon.aescore.data.ApiService
import com.gmail.in2horizon.aescore.data.RetrofitConstructor
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Test
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit

class RetrofitLoginTest {
     val retrofit: Retrofit=RetrofitConstructor.getRetrofitInstance()

    @Test
    fun loginIsHttp200(){
     val apiService= retrofit.create(ApiService::class.java)
       val call:Call<ResponseBody> =apiService.signin()
        val response: Response<ResponseBody> =call.execute()
        Assert.assertEquals(response.code(),200)
    }
}