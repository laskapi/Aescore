package com.gmail.in2horizon.aescore.views

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun MToast(message: MMessage){
    if (message.message.isNotEmpty()) {
        Toast.makeText(LocalContext.current,message.message, Toast.LENGTH_LONG).show()
    }
}