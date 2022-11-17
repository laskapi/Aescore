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
object RetrofitClient {
    private const val BASE_URL: String = "http://192.168.0.213:8080/api/"

    private val httpLoggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        HttpLoggingInterceptor.Level.BODY.also { this.level = it }
    }
    private val basicAuthInterceptor = BasicAuthInterceptor()


    fun setCredentials(credentials:String){
        basicAuthInterceptor.setCredentials(credentials)
    }

    @Provides
    fun getRetrofitInstance(): Retrofit {
        val client: OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(httpLoggingInterceptor)
            this.addInterceptor(basicAuthInterceptor)
          }.build()

        return Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}