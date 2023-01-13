package com.gmail.in2horizon.aescore.views.superComposables

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
            Icon(Icons.Rounded.Edit, contentDescription = stringResource(id = R.string.edit))
        }
    }
}