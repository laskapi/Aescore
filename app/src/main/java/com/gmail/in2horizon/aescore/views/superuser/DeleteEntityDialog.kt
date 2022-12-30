package com.gmail.in2horizon.aescore.views.superuser

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.gmail.in2horizon.aescore.R
import com.gmail.in2horizon.aescore.data.UserCredentials
import com.gmail.in2horizon.aescore.model.MyViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteEntityDialog(
    viewModel: MyViewModel, id: Long, dismiss: () -> Unit
) {
    val errorMessage = viewModel.errorMessage.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var password by remember {
        mutableStateOf("")
    }
    AlertDialog(onDismissRequest = { dismiss() }, text = {
        Column() {
            if (errorMessage.value != viewModel.NO_ERROR) {
                Text(text = stringResource(id = errorMessage.value), color = Color.Red)
            } else {
                Text(text = stringResource(id = R.string.retype_your_password_to_confirm))

            }
            TextField(
                value = password, onValueChange = {
                    viewModel.setErrorMessage(viewModel.NO_ERROR)
                    password = it
                }, visualTransformation = PasswordVisualTransformation()
            )

        }
    }, confirmButton = {
        ElevatedButton(
            onClick = {

                coroutineScope.launch {
                    val authenticated = viewModel.confirmAuthentication(
                        UserCredentials(viewModel.loggedInUser.value.username, password)
                    )
                    if (authenticated) {
                        viewModel.deleteEntity(id)
                        dismiss()
                    }
                }

            },
        ) { Text(text = stringResource(id = R.string.delete_user)) }
    }, dismissButton = {
        ElevatedButton(onClick = {
            viewModel.setErrorMessage(viewModel.NO_ERROR)
            dismiss()
        }) {
            Text(text = stringResource(id = android.R.string.cancel))
        }

    })
}