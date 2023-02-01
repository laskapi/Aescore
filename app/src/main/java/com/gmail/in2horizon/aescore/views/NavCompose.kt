package com.gmail.in2horizon.aescore.views

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.in2horizon.aescore.viewmodel.LoginViewModel
import com.gmail.in2horizon.aescore.superview.SuperRootScreen


enum class AescoreScreen() {
    Login,
    Super,
    Admin,
    User
}

@Composable
fun NavCompose() {

    val loginViewModel: LoginViewModel = hiltViewModel()
    val loggedIn by loginViewModel.loggedInUser.collectAsState()


    when {
        (loggedIn.get()!=null) -> SuperRootScreen()
        else -> LoginScreen(
            login = { loginViewModel.login(it) },
            errorMessage = loginViewModel.errorMessage,
         )
    }


}

