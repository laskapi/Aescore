package com.gmail.in2horizon.aescore.data

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class BasicAuthInterceptor constructor() :
Interceptor {
   private var credentials: String= ""

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authRequest: Request = request.newBuilder()
            .header("Authorization", credentials).build()
        return chain.proceed(authRequest)
    }

    fun setCredentials(credentials: String) {
        this.credentials=credentials
    }
}