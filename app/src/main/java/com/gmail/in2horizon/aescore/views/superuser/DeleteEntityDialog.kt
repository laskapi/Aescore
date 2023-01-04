package com.gmail.in2horizon.aescore.views.superuser

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.gmail.in2horizon.aescore.R
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteEntityDialog(
    delete: () -> Unit, confirmAuth: (String) -> Deferred<Boolean>, dismiss: () -> Unit

) {

    val scope = rememberCoroutineScope()
    var password by remember {
        mutableStateOf("")
    }
    var errorMessage by remember { mutableStateOf("") }
    val incorrect_pass_msg = stringResource(id = R.string.incorrect_password)

    AlertDialog(onDismissRequest = { dismiss() }, text = {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(text = errorMessage, color = Color.Red)
            Text(text = stringResource(id = R.string.retype_your_password_to_confirm))

            TextField(
                value = password, onValueChange = {
                    password = it
                }, visualTransformation = PasswordVisualTransformation()
            )

        }
    }, confirmButton = {
        ElevatedButton(
            onClick = {

                scope.launch {
                    if (confirmAuth(password).await()) {
                            delete()
                            dismiss()

                    } else {
                        errorMessage = incorrect_pass_msg
                    }
                }

            },
        ) { Text(text = stringResource(id = R.string.delete_user)) }
    }, dismissButton = {
        ElevatedButton(onClick = {
            dismiss()
        }) {
            Text(text = stringResource(id = android.R.string.cancel))
        }

    })
}