package com.gmail.in2horizon.aescore.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RetrofitConstructor {
    private const val BASE_URL: String = "http://192.168.0.213:8080/api/"
    private val basicAuthInterceptor:BasicAuthInterceptor= BasicAuthInterceptor("super","super")
    private val httpLoggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        HttpLoggingInterceptor.Level.BODY.also { this.level = it }
    }

    val client: OkHttpClient = OkHttpClient.Builder().apply {
        this.addInterceptor(basicAuthInterceptor)
        this.addInterceptor(httpLoggingInterceptor)
    }.build()

    @Provides
    fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}