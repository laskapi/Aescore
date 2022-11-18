package com.gmail.in2horizon.aescore.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gmail.in2horizon.aescore.model.LoginViewModel


enum class AescoreScreen() {
    Login,
    Super,
    Admin,
    User
}

@Composable
fun MainCompose() {

    val navController: NavHostController = rememberNavController()
    val loginViewModel: LoginViewModel = viewModel()
    val user by loginViewModel.user.collectAsState()

    NavHost(navController, AescoreScreen.Login.name) {

        composable(AescoreScreen.Login.name) {
            LoginScreen(login = {
                loginViewModel.login(it)
            },
                onLoginSuccess = {
                    when (user.authority) {
                        "Super" -> navController.navigate(AescoreScreen.Super.name)
                        "Admin" -> navController.navigate(AescoreScreen.Admin.name)
                    }
                },
            )

        }

        composable(AescoreScreen.Super.name) {
            SuperScreen()
        }

    }


}



