package com.gmail.in2horizon.aescore.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.in2horizon.aescore.data.UserCredentials
import com.gmail.in2horizon.aescore.data.AescoreRepository
import com.gmail.in2horizon.aescore.data.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.awaitResponse
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val aescoreRepository: AescoreRepository) : ViewModel() {

    private val _login = MutableStateFlow(false)
    val login = _login.asStateFlow()

    private val _user = MutableStateFlow(UserModel())
    val user=_user.asStateFlow()

    fun login(credentials: UserCredentials): Job {


       return viewModelScope.launch{

            //TODO data validation

            val response =
                aescoreRepository.login(credentials.getCredentials()).awaitResponse()

            _login.value = response.isSuccessful && response.code() == 200

            _user.value=response.body()?:UserModel()

            //                val result = userRepository.getUsers().awaitResponse();
            //               Log.d(TAG, result.toString())


        }
    }

}