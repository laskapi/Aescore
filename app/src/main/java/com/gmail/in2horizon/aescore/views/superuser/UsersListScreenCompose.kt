package com.gmail.in2horizon.aescore.views.superuser

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gmail.in2horizon.aescore.R
import com.gmail.in2horizon.aescore.data.User
import com.gmail.in2horizon.aescore.model.LoginViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.mapLatest


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersListScreenCompose(

    loginViewModel: LoginViewModel=hiltViewModel(), showUserDetails: (Long) -> Unit, deleteUser:
        (Long) -> Unit
) {


    val TAG: String = "usersListScreenCompose"


    val usersList by loginViewModel.users.collectAsState()
    var searchText by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxSize(0.9f),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            OutlinedTextField(value = searchText, onValueChange = { searchText = it },
                trailingIcon = { Icon(imageVector = Icons.Default
                    .Search  , contentDescription = null)},
                label={ Text(text=stringResource(id = android.R.string.search_go))},
                placeholder = { Text(text = stringResource(id = R.string.type_to_search))}

            )
        }


        LazyColumn(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
        ) {
            items(filterUsers(usersList, searchText)) {

                UsersListElement(user = it, showUserDetails = {
                    showUserDetails(it.id)
                }, deleteUser = {
                    deleteUser(it.id)
                })
            }
        }

        ElevatedButton(
            onClick = { showUserDetails(loginViewModel.EMPTY_ID) },
        ) {
            Text(text = stringResource(id = R.string.add_new_user))
        }
    }
}


@Composable
fun UsersListElement(user: User, showUserDetails: () -> Unit, deleteUser: () -> Unit) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .padding(8.dp, 16.dp)
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(
                    8
                        .dp
                )
            ),
        verticalAlignment = Alignment.CenterVertically,

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .padding(8.dp), horizontalAlignment = Alignment
                .Start
        ) {
            Text(text = user.username, style=MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = user.email,
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable(enabled = true, onClick = {
                    sendMail(user.email, context)
                })
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), horizontalArrangement = Arrangement
            .End) {
            EditButton(onClick = { showUserDetails() })
            Spacer(modifier = Modifier.width(8.dp))
            FilledIconButton(onClick = { deleteUser() }) {
                Icon(
                    Icons.Rounded.Delete, contentDescription = stringResource(id = R.string.delete)
                )
            }
        }
    }
}

fun filterUsers(fullList: List<User>, searchText: String): List<User> {
    return fullList.filter {
        it.username.contains(searchText, true) || it.email.contains(searchText, true)
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

