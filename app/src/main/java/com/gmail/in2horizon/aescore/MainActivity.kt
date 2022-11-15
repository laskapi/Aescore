package com.gmail.in2horizon.aescore

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gmail.in2horizon.aescore.data.ApiService
import com.gmail.in2horizon.aescore.data.SignInBody
import com.gmail.in2horizon.aescore.ui.theme.AescoreTheme
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.ResponseBody
import retrofit2.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AescoreTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")

                }
            }
        }
    }


    @Composable
    fun Greeting(name: String) {


        Text(text = "Hello $name!")

        TextButton(onClick = {
            val signInInfo = SignInBody("super", "super")
            val apiService: ApiService = retrofit.create(ApiService::class.java)
            apiService.signin().enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "fail: "+t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {

                    Toast.makeText(
                        this@MainActivity, response.code().toString(), Toast
                            .LENGTH_LONG
                    ).show()
                }
            })
        }) {
            Text(text = "login")

        }
    }


    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        AescoreTheme {
            Greeting("Android")
        }
    }
}