package com.gmail.in2horizon.aescore.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gmail.in2horizon.aescore.R
import com.gmail.in2horizon.aescore.data.User
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow


@Composable
fun UsersTab(/*loadUsers: () -> Job,*/ users: StateFlow<List<User>>): Unit {

    Text(text = stringResource(id = R.string.users_tab) + "Compose")
    val usersList by users.collectAsState()
    //  loadUsers()
    Column() {
        usersList.forEach { UserElement(it) }
    }
}

@Composable
fun UserElement(user: User): Unit {
    Row(modifier = Modifier.padding(8.dp)) {

        Text(text = user.username)

        Spacer(modifier = Modifier.width(8.dp))

        FilledIconButton(onClick = { /*editUser(user)*/ }) {
            Icon(Icons.Rounded.Edit, contentDescription = "edit")
        }
    }
}
