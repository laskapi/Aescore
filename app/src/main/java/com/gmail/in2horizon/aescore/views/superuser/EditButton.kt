package com.gmail.in2horizon.aescore.views.superuser

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.gmail.in2horizon.aescore.R

@Composable
fun EditButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(modifier = modifier, contentAlignment = Alignment.CenterEnd) {

        val initialColor=MaterialTheme.colorScheme.primary
        val changedColor=MaterialTheme.colorScheme.secondary
        var color by remember { mutableStateOf(initialColor) }
        FilledIconButton(
            onClick = {
                onClick.invoke()
                color = changedColor
            },
            colors = IconButtonDefaults.filledIconButtonColors(containerColor = color)
        ) {
            Text(text = "dsf",color= MaterialTheme.colorScheme.primary)
            Icon(Icons.Rounded.Edit, contentDescription = stringResource(id = R.string.edit))
        }
    }
}