package com.gmail.in2horizon.aescore

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.viewmodel.CreationExtras
import com.gmail.in2horizon.aescore.model.LoginViewModel
import com.gmail.in2horizon.aescore.ui.theme.AescoreTheme
import com.gmail.in2horizon.aescore.views.NavCompose
import com.gmail.in2horizon.aescore.views.SuperScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

      // val loginViewModel: LoginViewModel by viewModels()

    de
    ,l
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loginViewModel:LoginViewModel by viewModels<LoginViewModel>()
            //    val loginViewModel: LoginViewModel=ViewModelProvider(this,SavedStateViewModelFactory())
          //  .get()

        setContent {
            AescoreTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    NavCompose(loginViewModel)

                }
            }
        }
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