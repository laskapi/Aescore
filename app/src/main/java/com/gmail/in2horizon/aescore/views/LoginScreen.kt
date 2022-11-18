package com.gmail.in2horizon.aescore.views

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
import com.gmail.in2horizon.aescore.R
import com.gmail.in2horizon.aescore.data.UserCredentials
import com.gmail.in2horizon.aescore.data.UserModel
import kotlinx.coroutines.Job


@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun LoginScreen(login: (UserCredentials) -> Job, onLoginSuccess:(UserModel)->Unit) {


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

    ) {

        var username by remember { mutableStateOf("") }

        OutlinedTextField(
            modifier=Modifier.padding(20.dp),
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
            username = ""
            password = ""
            login(UserCredentials(username, password))
        }) {
            Text(text = stringResource(R.string.login))

        }

    }
}

