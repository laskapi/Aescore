package com.gmail.in2horizon.aescore.views

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.gmail.in2horizon.aescore.data.User
import com.gmail.in2horizon.aescore.model.LoginViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
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

    val user: User by loginViewModel.user.collectAsState()
    val test:String by loginViewModel.test.collectAsState()
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(navController, AescoreScreen.Login.name,
        enterTransition =
    {slideInHorizontally(initialOffsetX = {1000})},
        exitTransition =
    { slideOutHorizontally{-1000}})
    {

        composable(AescoreScreen.Login.name,) {
            LoginScreen(loginViewModel)
        }
        composable(AescoreScreen.Super.name) {
            SuperScreen(loginViewModel)
        }


    }
    Log.d("test","auth= "+user.authority)
    Log.d("test","test= "+test)
    when (user.authority) {
        "SUPER" -> navController.navigate(AescoreScreen.Super.name){
            popUpTo(AescoreScreen.Login.name) { inclusive = true }
            launchSingleTop=true
        }
        else -> navController.navigate(AescoreScreen.Login.name){
            launchSingleTop=true
        }
    }


}


/*@Composable
fun Animate(compose: @Composable () -> Unit) {
    var editable by remember { mutableStateOf(true) }
    val density = LocalDensity.current
    AnimatedVisibility(visible = editable) {
        compose
    }
}*/


/*
when (user.authority) {
    "SUPER" ->
    SuperScreen(loginViewModel = loginViewModel)

    else ->
    LoginScreen(
        loginViewModel = loginViewModel
    )*/
/*,
        onLoginSuccess =
        {}*//*


}
*/
