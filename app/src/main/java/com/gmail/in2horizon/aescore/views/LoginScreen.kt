package com.gmail.in2horizon.aescore.views

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.gmail.in2horizon.aescore.R
import com.gmail.in2horizon.aescore.data.UserCredentials
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    login: (UserCredentials) -> Job, errorMessage: StateFlow<String>
) {

    val errorMessage = errorMessage.collectAsState()
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        ) {

        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }


        Text(text = errorMessage.value, color = Color.Red)

        OutlinedTextField(
            modifier = Modifier.padding(20.dp),
            value = username,
            onValueChange = {
                username = it
            },
            label = {
                Text(
                    stringResource(R.string.username)
                )
            },
        )


        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text(
                    stringResource(R.string.password)
                )
            },
            visualTransformation = PasswordVisualTransformation()
        )
        ////////////////////////////////////
        // for testing login only:
        //**************************
                username = "super"
                password = "super"
                val credentials = UserCredentials(username, password)
                login(credentials)
                //**************************
        /////////////////////////////////////

        TextButton(modifier = Modifier.wrapContentSize(), onClick = {
            scope.launch {
                val job = login(UserCredentials(username, password))
                job.join()
                username = ""
                password = ""

            }

        }) {
            Text(text = stringResource(R.string.login))
        }

    }

}

