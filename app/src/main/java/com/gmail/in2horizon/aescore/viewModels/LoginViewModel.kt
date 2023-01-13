package com.gmail.in2horizon.aescore.viewModels

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
    private fun setErrorMessage(message: String?) {
        _errorMessage.value = message ?: ""
    }

    fun clearErrorMessage() {
        if (_errorMessage.value.isNotEmpty()) {
            _errorMessage.value = ""
        }
    }


}