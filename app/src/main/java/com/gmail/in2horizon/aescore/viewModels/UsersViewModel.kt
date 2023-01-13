package com.gmail.in2horizon.aescore.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.in2horizon.aescore.data.UserRepository
import com.gmail.in2horizon.aescore.data.Authority
import com.gmail.in2horizon.aescore.data.User
import com.gmail.in2horizon.aescore.views.MMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import retrofit2.awaitResponse
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

    private val _errorMessage = MutableStateFlow(MMessage(""))
    val errorMessage = _errorMessage.asStateFlow()

    init {
        loadUsers()
    }

    private fun loadUsers(): Job {

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

    fun confirmAuthAsync(password: String): Deferred<Boolean> {
        return viewModelScope.async {
            userRepository.confirmAuthentication(password)
        }
    }


    fun deleteUser(id: Long) {

        viewModelScope.launch {
            try {
                userRepository.deleteUser(id)
                loadUsers()
            } catch (e: Exception) {
                e.message?.apply { _errorMessage.value = MMessage(this) }
            }

        }
    }

    fun loadSelectedUser(id: Long?): Job {
        return viewModelScope.launch {
            _selectedUser.value = userRepository.getUser(id)
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

    suspend fun updateUser(/*user: User*/): Deferred<Boolean> {
        /*  try {
              userRepository.updateUser(user)
          } catch(e:Exception) {
       */
        return viewModelScope.async {
            try {
                val EMPTY_ID = -1L
                if (selectedUser.value.id == EMPTY_ID) {
                    userRepository.addUser(selectedUser.value)
                } else {
                    userRepository.updateUser(selectedUser.value)
                }
                loadUsers()
                return@async true
            } catch (e: Exception) {
                Log.d("exception3", e.message.toString())
                return@async false
            }
            // }

        }
    }

/*
    suspend fun updateUser(user: User): Boolean {
        val EMPTY_ID = -1L
        if (user.id == EMPTY_ID) {
            val response = userRepository.addUser(user).awaitResponse()
            when (response.code()) {
                HttpURLConnection.HTTP_OK -> {
                    loadUsers()
                    return true;
                }
                          }
            return false

        } else {


            val response = userRepository.updateUser(user).awaitResponse()
            when (response.code()) {
                HttpURLConnection.HTTP_OK -> {
                    loadUsers()
                    return true
                }
            }
            return false
        }
    }
*/

    suspend private fun loadAuthorities(): LinkedHashSet<Authority> {
        val response = userRepository.getAuthorities().awaitResponse()
        response.body()?.let { return it }

        //     setErrorMessage(R.string.error_server)
        return LinkedHashSet()

    }


}


