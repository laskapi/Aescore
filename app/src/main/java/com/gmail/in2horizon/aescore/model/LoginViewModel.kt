package com.gmail.in2horizon.aescore.model

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
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
class LoginViewModel @Inject constructor(/*private val savedStateHandle: SavedStateHandle,*/ val
aescoreRepository:
AescoreRepository) :
    ViewModel() {


    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()



    private val _user = MutableStateFlow(User())
    val user = _user.asStateFlow()

    //val user:StateFlow<User> = this.savedStateHandle.getStateFlow<User>("user",User())

    private val _users = MutableStateFlow(emptyList<User>())
    val users = _users.asStateFlow()

    fun login(credentials: UserCredentials): Job {



        return viewModelScope.launch {

            //TODO data validation
          try {
              val response =
                  aescoreRepository.login(credentials.getCredentials()).awaitResponse()

            _isLoggedIn.value = response.isSuccessful && response.code() == 200
//            savedStateHandle.set("user",response.body()?:User())
            _user.value=response.body()?:User()
          }catch(e: Exception){
              Log.e("Exception","exception :: "+e.message)
          }
        }
    }

    fun loadUsers(): Job {

        return viewModelScope.launch {
            val response = aescoreRepository.getUsers().awaitResponse()
            _users.value=response.body()?: emptyList()
        }
    }
}


