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
import com.gmail.in2horizon.aescore.data.User
import java.util.*
import kotlin.collections.LinkedHashSet

/*
enum class Authorities() {
    USER,
    ADMIN,
    SUPER
}*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun DetailUsersTabScreen(
    user: User, authorities: LinkedHashSet<Authority>,saveAndExit: (User)
    -> Unit, cancel:
        () ->
    Unit
) {

    val TAG = "detailUsersTabScreen"

    var username by remember {
        mutableStateOf(user.username)
    }
    var email by remember {
        mutableStateOf(user.email)
    }

    var pass1 by remember {
        mutableStateOf("")
    }
    var pass2 by remember {
        mutableStateOf("")
    }

    user.username = username
    user.email = email
    user.password = if (pass1 == pass2) pass1 else ""

    Column(
        Modifier
            .fillMaxSize(0.9f),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            var isEnabled by remember {
                mutableStateOf(false)
            }

            OutlinedTextField(
                modifier = Modifier.weight(0.7f),
                value = username,
                onValueChange = { username = it },
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
                value = email,
                onValueChange = { email = it },
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
                                text = if (pass1 == pass2 && pass1.isNotEmpty()) "Correct"
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

        DetailUsersTabListField(user.authorities, authorities)


        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement
                .SpaceBetween
        ) {
            OutlinedButton(onClick = { cancel }) {
                Text(text = stringResource(id = android.R.string.cancel))
            }
            OutlinedButton(onClick = { saveAndExit }) {
                Text(text = stringResource(id = R.string.save_and_exit))
            }

        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailUsersTabListField(
    selectionSet: HashSet<Authority>, fullSet: LinkedHashSet<Authority>) {
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
                Row(    verticalAlignment = Alignment.CenterVertically,) {
                    var value by remember {
                        mutableStateOf(selectionSet.contains(it))
                    }
                    when (value) {
                        true -> selectionSet.add(it)
                        false -> selectionSet.remove(it)

                    }
                    Log.d(TAG,selectionSet.toString())
                    Text(text = it.authority, modifier = Modifier.weight(1f))

                    Checkbox(checked = value, onCheckedChange = {value = !value},
                    enabled = isEnabled, modifier = Modifier.weight(1f))
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


