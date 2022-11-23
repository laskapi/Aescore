package com.gmail.in2horizon.aescore.views

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gmail.in2horizon.aescore.R
import com.gmail.in2horizon.aescore.data.UserCredentials
<<<<<<< HEAD
import com.gmail.in2horizon.aescore.data.User
import com.gmail.in2horizon.aescore.model.LoginViewModel
=======
import com.gmail.in2horizon.aescore.data.UserModel
import com.gmail.in2horizon.aescore.model.LoginViewModel
import kotlinx.coroutines.Job
>>>>>>> f1e5104e35007d69342a40fef3ece6f3a58f48d7


@OptIn(ExperimentalMaterial3Api::class)
@Composable
<<<<<<< HEAD
fun LoginScreen(
    loginViewModel: LoginViewModel
) {

    val user by loginViewModel.user.collectAsState()
=======
fun LoginScreen(loginViewModel: LoginViewModel, onLoginSuccess: (UserModel) -> Unit) {


    //  val isLoggedIn by loginViewModel.isLoggedIn.collectAsState()
    val user by loginViewModel.user.collectAsState()

    Log.d("compose", user.toString())
    LaunchedEffect(key1 = user, block = {
        onLoginSuccess(user)

    })


>>>>>>> f1e5104e35007d69342a40fef3ece6f3a58f48d7

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        ) {

        var username by remember { mutableStateOf("") }

        OutlinedTextField(
            modifier = Modifier.padding(20.dp),
            value = username,
            onValueChange = { username = it },
            label = {
                Text(
                    stringResource(R.string.username)
                )
            })

        var password by remember { mutableStateOf("") }

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = {
                Text(
                    stringResource(R.string.password)
                )
            },
            visualTransformation = PasswordVisualTransformation()
        )

        TextButton(modifier = Modifier.wrapContentSize(), onClick = {
<<<<<<< HEAD

            val credentials = UserCredentials(username, password)
            loginViewModel.login(credentials)
=======
            loginViewModel.login(UserCredentials(username, password))
            username = ""
            password = ""
>>>>>>> f1e5104e35007d69342a40fef3ece6f3a58f48d7

        }) {
            Text(text = stringResource(R.string.login))
        }

        LaunchedEffect(key1 = user, block = {
            username = user.username
            password = user.authority

        })


    }

}

