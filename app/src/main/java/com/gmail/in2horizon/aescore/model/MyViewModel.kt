package com.gmail.in2horizon.aescore.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.in2horizon.aescore.R
import com.gmail.in2horizon.aescore.data.AescoreRepository
import com.gmail.in2horizon.aescore.data.Authority
import com.gmail.in2horizon.aescore.data.User
import com.gmail.in2horizon.aescore.data.UserCredentials
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

abstract class MyViewModel constructor(
    val aescoreRepository: AescoreRepository
) : ViewModel() {


    val NO_ERROR = -1


    val EMPTY_ID: Long = -1

    lateinit var authorities: LinkedHashSet<Authority>

    private val _loggedInUser = MutableStateFlow(User())
    val loggedInUser = _loggedInUser.asStateFlow()

    private val _errorMessage = MutableStateFlow(NO_ERROR)
    val errorMessage = _errorMessage.asStateFlow()


    fun login(credentials: UserCredentials): Job {
        return viewModelScope.launch {
            val user = authenticate(credentials)
            if (user.isNotEmpty()) {
                authorities = loadAuthorities()
                _loggedInUser.value = user
            }

            //loadUsers()

        }
    }

    suspend fun confirmAuthentication(credentials: UserCredentials): Boolean {
        val user = authenticate(credentials)
        return user.isSameAs(loggedInUser.value)
    }

    suspend protected fun authenticate(credentials: UserCredentials): User {
        val response = aescoreRepository.login(credentials.getCredentials()).awaitResponse()
        response.body()?.let {
            setErrorMessage(NO_ERROR)
            return it
        }
        setErrorMessage(R.string.no_such_user)
        return User()

    }

    suspend private fun loadAuthorities(): LinkedHashSet<Authority> {
        val response = aescoreRepository.getAuthorities().awaitResponse()
        response.body()?.let { return it }

        setErrorMessage(R.string.error_server)
        return LinkedHashSet()

    }


    fun setErrorMessage(messageId: Int) {
        _errorMessage.value = messageId
    }

    fun clearErrorMessage() {
        _errorMessage.value = NO_ERROR
    }

    abstract fun loadSelectedEntity(id: Long): Job
    abstract fun deleteEntity(id: Long)


}