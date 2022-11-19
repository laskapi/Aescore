package com.gmail.in2horizon.aescore

import com.gmail.in2horizon.aescore.data.ApiService
import com.gmail.in2horizon.aescore.data.RetrofitClient
import com.gmail.in2horizon.aescore.data.UserCredentials
import com.gmail.in2horizon.aescore.data.AescoreRepository
import com.gmail.in2horizon.aescore.model.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import okhttp3.Credentials
import okhttp3.ResponseBody
import org.junit.*
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class RetrofitLoginTest {

    val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun loginGetsHttp200() {

        var retrofit = RetrofitClient.getRetrofitInstance()
        val apiService = retrofit.create(ApiService::class.java)
        RetrofitClient.setCredentials(Credentials.basic("super", "super"))
        val call = apiService.login()
        val response: Response<ResponseBody> = call.execute()
        Assert.assertEquals(response.code(), 200)
    }


    @Test
    fun loginFromViewModel() = runTest {
        val repository = AescoreRepository()
        val loginViewModel = LoginViewModel(repository)
        loginViewModel.login(UserCredentials("super", "super"), onLoginSuccess()).join()
        Assert.assertEquals(true, loginViewModel.isLoggedIn.value)
    }

}