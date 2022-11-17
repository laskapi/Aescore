package com.gmail.in2horizon.aescore

import com.gmail.in2horizon.aescore.data.ApiService
import com.gmail.in2horizon.aescore.data.RetrofitClient
import com.gmail.in2horizon.aescore.data.UserRepository
import com.gmail.in2horizon.aescore.model.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import okhttp3.Credentials
import okhttp3.ResponseBody
import org.junit.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit

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

    @Ignore
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
        val repository = UserRepository()
        val loginViewModel = LoginViewModel(repository)
        loginViewModel.signin("super", "super").join()
        Assert.assertEquals(true, loginViewModel.login.value)
    }

}