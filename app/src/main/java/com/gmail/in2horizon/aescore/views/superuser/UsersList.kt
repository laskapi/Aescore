package com.gmail.in2horizon.aescore.views.superuser

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.gmail.in2horizon.aescore.R
import com.gmail.in2horizon.aescore.views.MMessage
import com.gmail.in2horizon.aescore.views.MToast
import kotlinx.coroutines.flow.StateFlow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersList(
    items: StateFlow<List<Any>>,
    listElement: @Composable (item: Any) -> Unit,
    usersFilter: (List<Any>, String) -> List<Any>,
    errorMessage: StateFlow<MMessage>

) {


    val TAG: String = "usersListScreenCompose"

    val usersList by items.collectAsState()
    var searchText by remember {
        mutableStateOf("")
    }
    var message=errorMessage.collectAsState()

    MToast(message =message.value )

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

            OutlinedTextField(value = searchText,
                onValueChange = { searchText = it },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search, contentDescription = null
                    )
                },
                label = { Text(text = stringResource(id = android.R.string.search_go)) },
                placeholder = { Text(text = stringResource(id = R.string.type_to_search)) }

            )
        }

            LazyColumn(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)
            ) {
                items(usersFilter(usersList, searchText)  ) {
                    listElement(it)
                }
            }


        ElevatedButton(
            onClick = { /*showUserDetails(loginViewModel.EMPTY_ID) */ },
        ) {
            Text(text = stringResource(id = R.string.add_new_user))
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

