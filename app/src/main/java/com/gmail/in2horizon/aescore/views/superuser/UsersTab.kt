package com.gmail.in2horizon.aescore.views.superuser

import android.content.Context
import android.content.Intent
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.gmail.in2horizon.aescore.R
import com.gmail.in2horizon.aescore.data.User
import com.gmail.in2horizon.aescore.data.UserCredentials
import com.gmail.in2horizon.aescore.model.LoginViewModel
import com.gmail.in2horizon.aescore.model.LoginViewModel.Companion.NO_ERROR
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.launch


enum class UsersTabs {
    ListUsersTab, DetailUsersTab
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun UsersTab(loginViewModel: LoginViewModel) {

    val TAG = "usersTab"

    val navController = rememberAnimatedNavController()
    val usersList by loginViewModel.users.collectAsState()
    val errorMessage by loginViewModel.errorMessage.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    var selectedUser by remember { mutableStateOf(User()) }
    var userToDelete by remember { mutableStateOf(User()) }

    val contentDescription = stringResource(id = R.string.users_nav_host)

    AnimatedNavHost(
        navController, UsersTabs.ListUsersTab.name,
        modifier = Modifier.semantics { this.contentDescription = contentDescription },
        contentAlignment = Alignment.Center,


        ) {

        composable(UsersTabs.ListUsersTab.name, enterTransition = {
            when (initialState.destination.route) {
                UsersTabs.DetailUsersTab.name -> null
                else -> EnterTransition.None
            }
        }, exitTransition = {
            when (targetState.destination.route) {
                UsersTabs.DetailUsersTab.name -> null
                else -> ExitTransition.None
            }
        }) {
            LazyColumn(
                Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(usersList) {
                    UsersListElement(user = it, showUserDetails = {
                        selectedUser = it
                    }, deleteUser = {
                        userToDelete = it
                    })
                }
            }
        }

        composable(
            UsersTabs.DetailUsersTab.name, enterTransition = null, exitTransition = null
        ) {
            DetailUsersTabScreen(user = selectedUser, loginViewModel.authorities,
                saveAndExit = {
                    loginViewModel.updateUser(it)
                    selectedUser = User()
                },
                cancel = { selectedUser = User() })
        }
    }

    when {
        selectedUser.username.isEmpty() -> navController.navigate(UsersTabs.ListUsersTab.name)
        else -> navController.navigate(UsersTabs.DetailUsersTab.name)
    }

    if (errorMessage != NO_ERROR) {

        Snackbar(action = {
            TextButton(onClick = { loginViewModel.clearErrorMessage() }) {
                Text(
                    text = stringResource(
                        id = android.R.string.ok
                    )
                )
            }
        }) {
            Text(text = stringResource(id = errorMessage))
        }
    }



    if (userToDelete.isNotEmpty()) {
        val currentUser = loginViewModel.loggedInUser.collectAsState()
        DeleteUserDialog(dismiss = { userToDelete },
            currentUser = currentUser.value,
            deleteUser = { password ->
                coroutineScope.launch {
                    val authenticated = loginViewModel.confirmAuthentication(
                        UserCredentials(currentUser.value.username, password)
                    )
                    if (authenticated) {
                        loginViewModel.deleteUser(userToDelete)
                        userToDelete = User()
                    }
                }
            })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteUserDialog(dismiss: () -> Unit, currentUser: User, deleteUser: (String) -> Unit) {
    var password by remember {
        mutableStateOf("")
    }
    AlertDialog(onDismissRequest = { dismiss() }, text = {
        Column() {
            TextField(
                value = password,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation()
            )
            Text(text = stringResource(id = R.string.retype_your_password_to_confirm))

        }
    }, confirmButton = {
        ElevatedButton(
            onClick = { deleteUser(password) },
        ) { Text(text = stringResource(id = R.string.delete_user)) }
    })
}


@Composable
fun UsersListElement(user: User, showUserDetails: () -> Unit, deleteUser: () -> Unit) {
    val ctx = LocalContext.current

    Row(
        modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            Text(text = user.username)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = user.email, Modifier.clickable(enabled = true, onClick = {
                sendMail(user.email, ctx)
            }))
            Spacer(modifier = Modifier.width(8.dp))
        }
        EditButton(onClick = { showUserDetails() })
        Spacer(modifier = Modifier.width(8.dp))
        FilledIconButton(onClick = { deleteUser() }) {
            Icon(Icons.Rounded.Delete, contentDescription = stringResource(id = R.string.delete))
        }
    }
}

fun sendMail(email: String, ctx: Context) {
    val i = Intent(Intent.ACTION_SENDTO)
    i.putExtra(Intent.EXTRA_EMAIL, email)
    ctx.startActivity(
        Intent.createChooser(
            i, ctx.getString(
                R.string.choose_email_client
            )
        )
    )
}

