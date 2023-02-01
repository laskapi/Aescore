package com.gmail.in2horizon.aescore.retrofit

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiServiceModule {


    @Provides
    fun provideApiService(): ApiService {
        return RetrofitClient.getRetrofitInstance().create(ApiService::class.java)

    }
}