package com.gmail.in2horizon.aescore.model

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.in2horizon.aescore.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.Credentials
import retrofit2.awaitResponse
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val userRepository: UserRepository) : ViewModel() {

    private val _login = MutableStateFlow(false)
    val login = _login.asStateFlow()

    fun signin(username: String, password: String): Job {


        return viewModelScope.launch {

            //TODO data validation

            val credentials = Credentials.basic(username, password)
            val response = userRepository.login(credentials).awaitResponse()
            if (response.isSuccessful && response.code() == 200) {
                _login.value = true
                val result = userRepository.getUsers().awaitResponse();
                Log.d(TAG, result.toString())
            }
        }
    }

}