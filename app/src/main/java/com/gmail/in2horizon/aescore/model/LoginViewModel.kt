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
import kotlinx.coroutines.launch
import retrofit2.awaitResponse
import java.net.HttpURLConnection
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

@HiltViewModel
class LoginViewModel @Inject constructor(
    val aescoreRepository: AescoreRepository
) : ViewModel() {


    private val TAG = "loginViewModel"

    companion object {
        const val NO_ERROR = -1
    }

    private val _loggedInUuser = MutableStateFlow(User())
    val loggedInUser = _loggedInUuser.asStateFlow()

    private val _users = MutableStateFlow(emptyList<User>())
    val users = _users.asStateFlow()

    private val _errorMessage = MutableStateFlow(NO_ERROR)
    val errorMessage = _errorMessage.asStateFlow()

    lateinit var authorities: LinkedHashSet<Authority>

    fun login(credentials: UserCredentials): Job {
        return viewModelScope.launch {
            _loggedInUuser.value = authenticate(credentials)
            authorities= loadAuthorities()
            Log.d(TAG,authorities.toString())
        }
    }


    suspend fun confirmAuthentication(credentials: UserCredentials): Boolean {
        val user = authenticate(credentials)
        return user.isSameAs(loggedInUser.value)
    }

    suspend private fun loadAuthorities(): LinkedHashSet<Authority> {
        val response=aescoreRepository.getAuthorities().awaitResponse()
        response.body()?.let { return it }

        setErrorMessage(R.string.error_server)
        return LinkedHashSet()

    }

    suspend private fun authenticate(credentials: UserCredentials): User {
        val response = aescoreRepository.login(credentials.getCredentials()).awaitResponse()
        response.body()?.let {
            setErrorMessage(NO_ERROR)
            return it
        }
        setErrorMessage(R.string.no_such_user)
        return User()

    }

    fun loadUsers(): Job {

        return viewModelScope.launch {
            val response = aescoreRepository.getUsers().awaitResponse()
            _users.value = response.body().orEmpty()
            if (response.code() != HttpsURLConnection.HTTP_OK) {
                setErrorMessage(R.string.error_loading_users)
            }
        }
    }

    fun deleteUser(user: User) {

        viewModelScope.launch {
            val response = aescoreRepository.deleteUser(user.id).awaitResponse()
            when (response.code()) {
                HttpURLConnection.HTTP_OK -> loadUsers()
                HttpURLConnection.HTTP_PARTIAL ->
                    setErrorMessage(R.string.couldnt_delete_user_set)

            }
        }

    }


    fun updateUser(user: User) {
        viewModelScope.launch {
            val response = aescoreRepository.updateUser(user).awaitResponse()
            when (response.code()) {
                HttpURLConnection.HTTP_OK -> loadUsers()
                HttpURLConnection.HTTP_NOT_FOUND -> setErrorMessage(R.string.couldnt_find_user)

            }
        }
    }

    private fun setErrorMessage(messageId: Int) {
        _errorMessage.value = messageId
    }

    fun clearErrorMessage() {
        _errorMessage.value = NO_ERROR
    }
}


