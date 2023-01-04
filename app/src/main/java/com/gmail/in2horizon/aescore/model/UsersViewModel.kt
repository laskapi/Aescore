package com.gmail.in2horizon.aescore.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.in2horizon.aescore.R
import com.gmail.in2horizon.aescore.data.UserRepository
import com.gmail.in2horizon.aescore.data.Authority
import com.gmail.in2horizon.aescore.data.User
import com.gmail.in2horizon.aescore.views.MMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import retrofit2.awaitResponse
import java.net.HttpURLConnection
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

@HiltViewModel
open class UsersViewModel @Inject constructor(val userRepository: UserRepository) : ViewModel() {


    private val TAG = "usersViewModel"

    lateinit var authorities: LinkedHashSet<Authority>


    private val _users = MutableStateFlow(emptyList<User>())
    val users = _users.asStateFlow()

    private val _selectedUser = MutableStateFlow(User())
    val selectedUser = _selectedUser.asStateFlow()

    private val _errorMessage= MutableStateFlow(MMessage(""))
    val errorMessage=_errorMessage.asStateFlow()

    init {
        loadUsers()
    }


    fun confirmAuth(password: String): Deferred<Boolean> {
        return viewModelScope.async {
            userRepository.confirmAuthentication(password)
        }
    }


    fun loadUsers(): Job {

        return viewModelScope.launch {
            val response = userRepository.getUsers().awaitResponse()
            _users.value = response.body().orEmpty()
            authorities = loadAuthorities()

            Log.d(TAG, "load users::" + users.value.toString())
            if (response.code() != HttpsURLConnection.HTTP_OK) {
                //      setErrorMessage(R.string.error_loading_users)
            }
        }
    }

    fun deleteUser(id: Long) {

        viewModelScope.launch {
            try {
                val result = userRepository.deleteUser(id)
            }
            catch(e:Exception){
                e.message?.apply{_errorMessage.value=MMessage(this)}
            }
            /*    when (response.code()) {
                    HttpURLConnection.HTTP_OK ->
                        loadUsers()
                    HttpURLConnection.HTTP_PARTIAL->
                        throw (Exception(R.string.couldnt_delete_user_bounded))
                        }
            */
        }
    }

    fun loadSelectedUser(id: Long): Job {
        return viewModelScope.launch {
            val response = userRepository.getUser(id).awaitResponse()

            _selectedUser.value = response.body() ?: User()

        }
    }

    fun updateLocalSelectedUser(
        username: String? = null,
        password: String? = null,
        email: String? = null
    ) {
        username?.let { name -> _selectedUser.update { it.copy(username = name) } }
        password?.let { pass -> _selectedUser.update { it.copy(password = pass) } }
        email?.let { mail -> _selectedUser.update { it.copy(email = mail) } }


    }

    suspend fun updateUser(user: User): Boolean {
        val EMPTY_ID = -1L
        if (user.id == EMPTY_ID) {
            val response = userRepository.addUser(user).awaitResponse()
            when (response.code()) {
                HttpURLConnection.HTTP_OK -> {
                    loadUsers()
                    return true;
                }
                /*    HttpURLConnection.HTTP_PARTIAL -> setErrorMessage(R.string
                        .error_fill_all_fields)
                    HttpURLConnection.HTTP_BAD_REQUEST->setErrorMessage(R.string
                        .error_user_already_exists)
               */
            }
            return false

        } else {


            val response = userRepository.updateUser(user).awaitResponse()
            when (response.code()) {
                HttpURLConnection.HTTP_OK -> {
                    loadUsers()
                    return true
                }
                // HttpURLConnection.HTTP_NOT_FOUND -> setErrorMessage(R.string
                // .couldnt_find_user)

            }
            return false
        }
    }

    suspend private fun loadAuthorities(): LinkedHashSet<Authority> {
        val response = userRepository.getAuthorities().awaitResponse()
        response.body()?.let { return it }

        //     setErrorMessage(R.string.error_server)
        return LinkedHashSet()

    }


}


