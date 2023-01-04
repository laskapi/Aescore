package com.gmail.in2horizon.aescore.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.in2horizon.aescore.data.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    val userRepository: UserRepository
) : ViewModel() {
/*
init {
    viewModelScope.launch {
        _loggedInUser.collect{state->loggedInUser}
    }
}
*/

    // lateinit var authorities: LinkedHashSet<Authority>

    val loggedInUser= userRepository.loggedInUser

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()


    fun login(credentials: UserCredentials): Job {
        return viewModelScope.launch {
            clearErrorMessage()
            try {
                userRepository.login(credentials)
            } catch (e: Exception) {
                setErrorMessage(e.message)
            }
        }
    }


/*
    suspend fun confirmAuthentication(password: String): Boolean {
        val credentials = UserCredentials(loggedInUser.value.username, password)
        val user = authenticate(credentials)
        return user.isSameAs(loggedInUser.value)
    }*/

 /*   suspend private fun authenticate(credentials: UserCredentials): Unit*//*User*//* {
        try { *//*_loggedInUser.value =*//*
            userRepository.login(credentials.getCredentials()).getOrThrow()
        } catch (e: Exception) {

            setErrorMessage(e.message)
        }
    }*/


    private fun setErrorMessage(message: String?) {
        _errorMessage.value = message ?: ""
    }

    fun clearErrorMessage() {
        if (_errorMessage.value.isNotEmpty()) {
            _errorMessage.value = ""
        }
    }


}