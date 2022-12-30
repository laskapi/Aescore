package com.gmail.in2horizon.aescore.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.in2horizon.aescore.R
import com.gmail.in2horizon.aescore.data.AescoreRepository
import com.gmail.in2horizon.aescore.data.Authority
import com.gmail.in2horizon.aescore.data.User
import com.gmail.in2horizon.aescore.data.UserCredentials
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.awaitResponse
import java.net.HttpURLConnection
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

@HiltViewModel
open class LoginViewModel @Inject constructor (aescoreRepository: AescoreRepository) : MyViewModel
    (aescoreRepository) {


    private val TAG = "loginViewModel"




    private val _users = MutableStateFlow(emptyList<User>())
    val users = _users.asStateFlow()

    private val _selectedUser = MutableStateFlow(User())
    val selectedUser = _selectedUser.asStateFlow()

    init{
        loadUsers()
    }

    fun loadUsers(): Job {

        return viewModelScope.launch {
            val response = aescoreRepository.getUsers().awaitResponse()
            _users.value = response.body().orEmpty()
            Log.d(TAG,"load users::"+ users.value.toString())
            if (response.code() != HttpsURLConnection.HTTP_OK) {
                setErrorMessage(R.string.error_loading_users)
            }
        }
    }

    override fun deleteEntity(id:Long) {

        viewModelScope.launch {
            val response = aescoreRepository.deleteUser(id).awaitResponse()
            when (response.code()) {
                 HttpURLConnection.HTTP_OK -> loadUsers()
                HttpURLConnection.HTTP_PARTIAL ->
                    setErrorMessage(R.string.couldnt_delete_user_set)

            }
        }

    }

    override fun loadSelectedEntity(id: Long): Job {
        return viewModelScope.launch {
            val response = aescoreRepository.getUser(id).awaitResponse()

              _selectedUser.value=response.body()?:User()

        }
    }

    fun updateLocalSelectedUser(username: String? = null, password: String? = null, email: String? = null) {
        username?.let { name -> _selectedUser.update { it.copy(username = name) } }
        password?.let { pass -> _selectedUser.update { it.copy(password = pass) } }
        email?.let { mail -> _selectedUser.update { it.copy(email = mail) } }


    }

    suspend  fun updateUser(user: User):Boolean {
            if(user.id==EMPTY_ID){
                val response = aescoreRepository.addUser(user).awaitResponse()
                when (response.code()) {
                    HttpURLConnection.HTTP_OK -> {
                         loadUsers()
                        return true;
                    }
                    HttpURLConnection.HTTP_PARTIAL -> setErrorMessage(R.string
                        .error_fill_all_fields)
                    HttpURLConnection.HTTP_BAD_REQUEST->setErrorMessage(R.string
                        .error_user_already_exists)
                }
                return false

            }
            else {


                val response = aescoreRepository.updateUser(user).awaitResponse()
                when (response.code()) {
                    HttpURLConnection.HTTP_OK -> {
                        loadUsers()
                        return true
                    }
                    HttpURLConnection.HTTP_NOT_FOUND -> setErrorMessage(R.string.couldnt_find_user)

                }
                return false
            }
    }



}


