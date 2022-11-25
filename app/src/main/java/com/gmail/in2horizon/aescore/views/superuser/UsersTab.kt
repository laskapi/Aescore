package com.gmail.in2horizon.aescore.views.superuser

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.gmail.in2horizon.aescore.R
import com.gmail.in2horizon.aescore.data.User
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.flow.StateFlow

enum class UsersTabs {
    ListUsersTab, DetailUsersTab
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun UsersTab(users: StateFlow<List<User>>) {

    val navController = rememberAnimatedNavController()
    val usersList by users.collectAsState()
    var selectedUser by remember { mutableStateOf(User()) }

    val contentDescription = stringResource(id = R.string.users_nav_host)


    AnimatedNavHost(
        navController, UsersTabs.ListUsersTab.name,
        modifier = Modifier.semantics { this.contentDescription = contentDescription },
        contentAlignment = Alignment.Center,


        ) {

        composable(
            UsersTabs.ListUsersTab.name,
            enterTransition = {
                when (initialState.destination.route) {
                    UsersTabs.DetailUsersTab.name -> null
                    else -> EnterTransition.None
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    UsersTabs.DetailUsersTab.name -> null
                    else -> ExitTransition.None
                }
            }
        )
        {
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .background(Color.Cyan),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(usersList) {
                    UsersListElement(user = it, setSelectedUser = { user ->
                        selectedUser = user
                    })
                }
            }
        }


        composable(UsersTabs.DetailUsersTab.name,
            enterTransition = null,
            exitTransition = null

        ) {
            UserDetailsElement(selectedUser, { selectedUser = User() })
        }
    }

    when {
        selectedUser.username.isEmpty() -> navController.navigate(UsersTabs.ListUsersTab.name)
        else -> navController.navigate(UsersTabs.DetailUsersTab.name)
    }


}

@Composable
fun UsersListElement(user: User, setSelectedUser: (User) -> Unit) {
    Row(modifier = Modifier.padding(8.dp)) {
        Text(text = user.username)
        Spacer(modifier = Modifier.width(8.dp))
        FilledIconButton(onClick = { setSelectedUser(user) }) {
            Icon(Icons.Rounded.Edit, contentDescription = "edit")
        }
    }
}
