package com.gmail.in2horizon.aescore.views.superuser

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gmail.in2horizon.aescore.R
import com.gmail.in2horizon.aescore.data.Authority
import com.gmail.in2horizon.aescore.model.LoginViewModel
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.LinkedHashSet


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsScreenCompose(

    loginViewModel: LoginViewModel,
    back: () -> Unit
) {

    val TAG:String = "userDetailsScreenCompose"
    val authorities = loginViewModel.authorities
    val user=loginViewModel.selectedUser.collectAsState()

    val coroutine= rememberCoroutineScope()

    var pass1 by remember {
        mutableStateOf("")
    }
    var pass2 by remember {
        mutableStateOf("")
    }
    var correctPass=if (pass1 == pass2) pass1 else null

    Column(
        Modifier.fillMaxSize(0.9f),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            var isEnabled by remember {
                mutableStateOf(false)
            }

            OutlinedTextField(
                modifier = Modifier.weight(0.7f),
                value = user.value.username,
                onValueChange = {loginViewModel.updateLocalSelectedUser(username=it)},
                label = { Text(text = stringResource(id = R.string.username)) },
                enabled = isEnabled
            )
            EditButton(
                modifier = Modifier.weight(0.3f),
            ) {
                isEnabled = true
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            var isEnabled by remember {
                mutableStateOf(false)
            }
            OutlinedTextField(
                modifier = Modifier.weight(0.7f),
                value = user.value.email,
                onValueChange = {loginViewModel.updateLocalSelectedUser(email = it) },
                label = { Text(text = stringResource(id = R.string.email)) },
                enabled = isEnabled
            )
            EditButton(
                modifier = Modifier.weight(0.3f),
            ) {
                isEnabled = true
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            var isEnabled by remember {
                mutableStateOf(false)
            }
            Column(
                modifier = Modifier.weight(0.7f),
            ) {
                OutlinedTextField(
                    value = pass1,
                    onValueChange = { pass1 = it },
                    label = { Text(text = stringResource(id = R.string.change_password)) },
                    enabled = isEnabled
                )
                if (isEnabled) {
                    OutlinedTextField(
                        value = pass2,
                        onValueChange = { pass2 = it },
                        label = {
                            Text(
                                text =
                                if (pass1 == pass2 && pass1.isNotEmpty()) "Correct"
                                else if (pass2.isEmpty()) "Repeat password"
                                else "Incorrect"
                            )
                        },
                        enabled = isEnabled,
                    )
                }
            }
            EditButton(
                modifier = Modifier.weight(0.3f),
            ) {
                isEnabled = true
            }
        }

        DetailUsersTabListField(user.value.authorities, authorities)


        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(onClick = {
                back()
            }) {
                Text(text = stringResource(id = android.R.string.cancel))
            }
            OutlinedButton(onClick = {
                correctPass?.ifEmpty{null}.let{loginViewModel.updateLocalSelectedUser(password = it)}
                coroutine.launch {
                    if(loginViewModel.updateUser(user.value)) {
                        back()
                    }
                }
            }, enabled = (correctPass!=null)) {
                Text(text = stringResource(id = R.string.save_and_exit))
            }

        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailUsersTabListField(
    selectionSet: HashSet<Authority>, fullSet: LinkedHashSet<Authority>
) {
    val TAG = "userListField"
    var isEnabled by remember {
        mutableStateOf(false)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .weight(0.7f)

                .border(
                    border = ButtonDefaults.outlinedButtonBorder, //BorderStroke(1.dp,MaterialTheme.colorScheme.onSurface.copy(alpha=1f)) ,
                    shape = RoundedCornerShape(4.dp)
                )

                .padding(PaddingValues(horizontal = 8.dp))
        ) {
            fullSet.forEach {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    var value by remember {
                        mutableStateOf(selectionSet.contains(it))
                    }
                    when (value) {
                        true -> selectionSet.add(it)
                        false -> selectionSet.remove(it)

                    }
                    Log.d(TAG, selectionSet.toString())
                    Text(text = it.authority, modifier = Modifier.weight(1f))

                    Checkbox(
                        checked = value,
                        onCheckedChange = { value = !value },
                        enabled = isEnabled,
                        modifier = Modifier.weight(1f)
                    )
                }
            }


        }
        Column(Modifier.weight(0.3f), horizontalAlignment = Alignment.End) {
            EditButton() {
                isEnabled = true
            }
        }

    }

}


