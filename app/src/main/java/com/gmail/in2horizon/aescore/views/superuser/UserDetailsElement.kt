package com.gmail.in2horizon.aescore.views.superuser

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.gmail.in2horizon.aescore.data.User

@Composable
fun UserDetailsElement(user: User, deselectUser: () -> Unit) {
    Column(Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = user.username)
        Button(onClick = deselectUser)
        {
            Text(text = "back")
        }


    }
}