package com.gmail.in2horizon.aescore.views

import android.util.Log
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
    val loginViewModel: LoginViewModel =viewModel()

    val navController: NavHostController = rememberNavController()

    NavHost(navController, AescoreScreen.Login.name) {

        composable(AescoreScreen.Login.name) {

            LoginScreen(loginViewModel,
                    onLoginSuccess = {
                 if(it.authority.equals("SUPER")){
                     navController.navigate(AescoreScreen.Super.name)
                 }
                                     /*   when (it.authority) {
                        "SUPER" -> navController.navigate(AescoreScreen.Super.name)
                        "ADMIN" -> navController.navigate(AescoreScreen.Admin.name)
                    }*/
                },
            )

        }

        composable(AescoreScreen.Super.name) {
            SuperScreen()
        }

    }


}



