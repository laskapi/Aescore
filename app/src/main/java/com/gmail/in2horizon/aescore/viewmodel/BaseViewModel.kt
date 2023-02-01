package com.gmail.in2horizon.aescore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.in2horizon.aescore.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import javax.inject.Inject


abstract class BaseViewModel constructor(val userRepository: UserRepository):ViewModel
() {

    fun confirmAuthAsync(password: String): Deferred<Boolean> {
        return viewModelScope.async {
            userRepository.confirmAuthentication(password)
        }
    }

}