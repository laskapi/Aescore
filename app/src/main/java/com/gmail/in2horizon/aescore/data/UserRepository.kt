package com.gmail.in2horizon.aescore.data

import android.content.Context
import android.util.Log
import com.gmail.in2horizon.aescore.R
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import java.net.HttpURLConnection
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserRepository @Inject constructor(@ApplicationContext val context: Context) {
    private val retrofit: Retrofit = RetrofitClient.getRetrofitInstance()
    private val apiService: ApiService = retrofit.create(ApiService::class.java)

    private val _loggedInUser = MutableStateFlow(LoggedInUser())
    val loggedInUser = _loggedInUser.asStateFlow()


    suspend fun login(credentials: UserCredentials): Unit {
        var result = authenticate(credentials)
        _loggedInUser.value = LoggedInUser(result.getOrThrow())

    }

    suspend fun confirmAuthentication(password: String): Boolean {
        loggedInUser.value.get()?.let {
            var result = authenticate(
                UserCredentials(
                    it.username, password
                )
            )
            return result.isSuccess
        }
        return false
    }

    private suspend fun authenticate(credentials: UserCredentials): Result<User> {
        return withContext(Dispatchers.IO) {
            RetrofitClient.setCredentials(credentials.getCredentials())
            val response = apiService.login().awaitResponse()
            if (response.code() == 200 && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(context.getString(R.string.incorrect_name_or_password)))
            }
        }
    }


    fun getUsers(): Call<List<User>> {
        return apiService.getUsers()
    }

    suspend fun getUser(id: Long?): User {
        return withContext(Dispatchers.IO) {
            id?.let {
                val result = apiService.getUser(id).awaitResponse()
                result.body()?.let { return@withContext it }
                throw(Exception(context.getString(R.string.couldnt_find_user)))
            }
            return@withContext User()
        }
    }

    suspend fun addUser(user: User): Response<ResponseBody> {

        return withContext(Dispatchers.IO) {
            val response = apiService.addUser(user).awaitResponse()
            if (response.code()!=HttpURLConnection.HTTP_OK){
                throw(Exception(response.errorBody().toString()))
            }
            response
        }
    }

    suspend fun updateUser(user: User): Response<ResponseBody> {
        return withContext(Dispatchers.IO) {
           val response= apiService.updateUser(user).awaitResponse()
            if (response.code()!=HttpURLConnection.HTTP_OK){
                throw(Exception(response.errorBody().toString()))
            }
            response
        }
    }

    suspend fun deleteUser(id: Long): Response<Void> {
        return withContext(Dispatchers.IO) {
            val response = apiService.deleteUser(id).awaitResponse()

            when (response.code()) {
                HttpURLConnection.HTTP_PARTIAL ->
                    throw (Exception(context.getString(R.string.couldnt_delete_user_bounded)))
            }
            response
        }
    }


    fun getAuthorities(): Call<LinkedHashSet<Authority>> {
        return apiService.getAuthorities()
    }


}
