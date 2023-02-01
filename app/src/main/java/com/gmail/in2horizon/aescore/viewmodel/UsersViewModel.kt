package com.gmail.in2horizon.aescore.viewmodel

import androidx.lifecycle.viewModelScope
import com.gmail.in2horizon.aescore.MMessage
import com.gmail.in2horizon.aescore.data.Authority
import com.gmail.in2horizon.aescore.data.User
import com.gmail.in2horizon.aescore.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(userRepository: UserRepository) :
    BaseViewModel(userRepository) {


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
        viewModelScope.launch {
            authorities = userRepository.getAuthorities()/*loadAuthoritiesAsync().await()*/
        }
    }

    private fun loadUsers() {
        viewModelScope.launch {
            _users.value = userRepository.getUsers()
        }
    }
    /*   private fun loadUsers(): Job {

           return viewModelScope.launch {
               val response = userRepository.getUsers().awaitResponse()
               _users.value = response.body().orEmpty()
               authorities = loadAuthorities()

               Log.d(TAG, "load users::" + users.value.toString())
               if (response.code() != HttpsURLConnection.HTTP_OK) {
                   //      setErrorMessage(R.string.error_loading_users)
               }
           }
       }*/


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

    suspend fun updateUserAsync(): Deferred<Boolean> {

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
                return@async false
            }

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

  /*  private fun loadAuthoritiesAsync(): Deferred<LinkedHashSet<Authority>> {

        return viewModelScope.async {
            return@async userRepository.getAuthorities()

        }
    }*/


}


