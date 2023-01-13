package com.gmail.in2horizon.aescore


import com.gmail.in2horizon.aescore.data.*
import com.gmail.in2horizon.aescore.viewModels.UsersViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import okhttp3.Credentials
import org.junit.*
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class RetrofitLoginTest {

    private val dispatcher = StandardTestDispatcher()



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
        val response: Response<User> = call.execute()
        Assert.assertEquals(response.code(), 200)
    }


    @Test
    fun loginFromViewModel() = runTest {
        val repository = UserRepository()
<<<<<<< HEAD
        val usersViewModel = UsersViewModel(, repository)
        usersViewModel.login(UserCredentials("super", "super")).join()
        Assert.assertEquals(true, usersViewModel.loggingAttempt.value)
=======
        val usersViewModel = UsersViewModel(repository)
        usersViewModel.login(UserCredentials("super", "super"), onLoginSuccess()).join()
        Assert.assertEquals(true, usersViewModel.isLoggedIn.value)
<<<<<<< HEAD
>>>>>>> f1e5104e35007d69342a40fef3ece6f3a58f48d7
=======
>>>>>>> f1e5104e35007d69342a40fef3ece6f3a58f48d7
    }

}