package com.gmail.in2horizon.aescore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.gmail.in2horizon.aescore.model.LoginViewModel
import com.gmail.in2horizon.aescore.ui.theme.AescoreTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AescoreTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Login()

                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Login() {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            var username by remember { mutableStateOf("") }

            OutlinedTextField(value = username,
                onValueChange = { username = it },
                label = {
                    Text(
                        stringResource(R.string.username))
                })

            var password by remember{mutableStateOf("")}

            OutlinedTextField(value = password,
                onValueChange = { password = it },
                label = {
                    Text(
                        stringResource(R.string.password))
                },
            visualTransformation = PasswordVisualTransformation())

            TextButton(modifier = Modifier.wrapContentSize(), onClick = {
                loginViewModel.signin(username,password)
            }) {
                val myText by loginViewModel.login.collectAsState()
                Text(text = myText.toString())

            }

        }
    }


    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        AescoreTheme {
            Login()
        }
    }
}