package com.gmail.in2horizon.aescore.data

import android.util.Log
import com.gmail.in2horizon.aescore.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.awaitResponse
import java.net.HttpURLConnection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompRepository @Inject constructor(val apiService: ApiService) {

    suspend fun loadCompetitions():List<Competition> {
        return withContext(Dispatchers.IO) {
           val response= apiService.getCompetitions().awaitResponse()


            if (response.code()!= HttpURLConnection.HTTP_OK){
                throw(Exception(response.errorBody().toString()))
            }
            response.body()?: emptyList()
        }
    }

    suspend fun getCompetition(id: Long?): Competition {
        return withContext(Dispatchers.IO){
            id?.let {
                val response = apiService.getCompetition(it).awaitResponse()
                if (response.code() != HttpURLConnection.HTTP_OK) {
                    throw(Exception(response.errorBody().toString()))
                }

                return@withContext response.body()?:Competition()
            }
            Competition()
        }
    }
    suspend fun deleteCompetition(id:Long){
        withContext(Dispatchers.IO) {
            val response = apiService.deleteCompetition(id).awaitResponse()

            if (response.code() != HttpURLConnection.HTTP_OK) {
                throw(Exception(response.errorBody().toString()))
            }
        }
    }

    suspend fun addCompetition(comp: Competition): Response<ResponseBody> {
        return withContext(Dispatchers.IO) {
            val response = apiService.addCompetition(comp).awaitResponse()
            if (response.code()!=HttpURLConnection.HTTP_OK){
                throw(Exception(response.errorBody().toString()))
            }
            response
        }
    }

    suspend fun updateCompetition(comp: Competition): Response<ResponseBody> {

        return withContext(Dispatchers.IO) {
            val response= apiService.updateCompetition(comp).awaitResponse()
            if (response.code()!=HttpURLConnection.HTTP_OK){
                throw(Exception(response.errorBody().toString()))
            }
            response
        }
    }}
