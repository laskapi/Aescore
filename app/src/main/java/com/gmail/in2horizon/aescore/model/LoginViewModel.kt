package com.gmail.in2horizon.aescore.model

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.in2horizon.aescore.data.UserCredentials
import com.gmail.in2horizon.aescore.data.AescoreRepository
import com.gmail.in2horizon.aescore.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import retrofit2.awaitResponse
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle, val
aescoreRepository:
AescoreRepository) :
    ViewModel() {




    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()


    private val _loggingAttempt = MutableStateFlow(false)
    val loggingAttempt = _loggingAttempt.asStateFlow()

/*
    private val _user = MutableStateFlow(User())
    val user = _user.asStateFlow()
*/

    val user:StateFlow<User> = this.savedStateHandle.getStateFlow<User>("user",User())
    val test: StateFlow<String> = this.savedStateHandle.getStateFlow("test","nic")


    private val _users = MutableStateFlow(emptyList<User>())
    val users = _users.asStateFlow()

    fun login(credentials: UserCredentials): Job {


        return viewModelScope.launch {

            //TODO data validation
            val response =
                aescoreRepository.login(credentials.getCredentials()).awaitResponse()
            _loggingAttempt.value = response.isSuccessful && response.code() == 200


            _isLoggedIn.value = response.isSuccessful && response.code() == 200

            _user.value=response.body()?:UserModel()

            //                val result = userRepository.getUsers().awaitResponse();
            //               Log.d(TAG, result.toString())


        }
    }

    fun loadUsers(): Job {

        return viewModelScope.launch {
            val response = aescoreRepository.getUsers().awaitResponse()
            _users.value=response.body()?: emptyList()

        /*    response.body()?.forEachIndexed { index, user ->

                Log.d("TAG", "::" + index + "::" + user.toString())

            }*/
        }
    }


}