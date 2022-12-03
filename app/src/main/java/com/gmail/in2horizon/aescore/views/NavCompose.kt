package com.gmail.in2horizon.aescore.views

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.*
import com.gmail.in2horizon.aescore.data.User
import com.gmail.in2horizon.aescore.model.LoginViewModel
import com.gmail.in2horizon.aescore.views.superuser.SuperScreen
import com.google.accompanist.navigation.animation.rememberAnimatedNavController


enum class AescoreScreen() {
    Login,
    Super,
    Admin,
    User
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavCompose(loginViewModel: LoginViewModel) {

    val user: User by loginViewModel.loggedInUser.collectAsState()


    when{
        user.authorities.isNotEmpty()->SuperScreen(loginViewModel)
        else -> LoginScreen(loginViewModel = loginViewModel)
    }    


    val navController = rememberAnimatedNavController()

  /*  AnimatedNavHost(navController, AescoreScreen.Login.name,
        enterTransition =
    {slideInHorizontally(initialOffsetX = {1000})},
        exitTransition =
    { slideOutHorizontally{-1000}}
    ){

        composable(AescoreScreen.Login.name) {
            LoginScreen(loginViewModel)
        }
        composable(AescoreScreen.Super.name) {
            SuperScreen(loginViewModel)
        }
    }


    when (user.authority) {
        "SUPER" -> navController.navigate(AescoreScreen.Super.name){
            popUpTo(AescoreScreen.Login.name) { inclusive = true }
            launchSingleTop=true
        }
        else -> navController.navigate(AescoreScreen.Login.name){
            launchSingleTop=true
        }
    }
*/
}

