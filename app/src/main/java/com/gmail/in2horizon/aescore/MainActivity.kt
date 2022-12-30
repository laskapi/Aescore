package com.gmail.in2horizon.aescore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gmail.in2horizon.aescore.model.CompetitionViewModel
import com.gmail.in2horizon.aescore.model.LoginViewModel
import com.gmail.in2horizon.aescore.ui.theme.AescoreTheme
import com.gmail.in2horizon.aescore.views.NavCompose
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

       val loginViewModel: LoginViewModel by viewModels()
    val competitionViewModel: CompetitionViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AescoreTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    NavCompose(loginViewModel,competitionViewModel)

                }
            }
        }
    }


    override fun onBackPressed() {
        moveTaskToBack(true)
    }


    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        AescoreTheme {
  //          SuperScreen(/*loginViewModel*/)
 //           LoginScreen(loginViewModel)
        }
    }


}