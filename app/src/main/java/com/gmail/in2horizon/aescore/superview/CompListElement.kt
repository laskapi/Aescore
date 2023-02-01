package com.gmail.in2horizon.aescore.superview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gmail.in2horizon.aescore.R
import com.gmail.in2horizon.aescore.data.Competition
import com.gmail.in2horizon.aescore.views.`super`.DeleteEntityDialog
import com.gmail.in2horizon.aescore.views.`super`.EditButton
import kotlinx.coroutines.Deferred

@Composable
fun CompListElement(item: Competition, showDetails: () -> Unit, deleteItem: () -> Unit,
                    confirmAuth:
    (String) -> Deferred<Boolean>
) {

    var showDeleteDialog by remember { mutableStateOf(false) }


    Row(
        modifier = Modifier
            .padding(8.dp, 16.dp)
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(
                    8.dp
                )
            ),
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .padding(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = item.name, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.width(8.dp))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            EditButton(onClick = { showDetails() })

            Spacer(modifier = Modifier.width(8.dp))

            FilledIconButton(onClick = { showDeleteDialog = true }) {
                Icon(
                    Icons.Rounded.Delete, contentDescription = stringResource(id = R.string.delete)
                )
            }
        }
    }
    if (showDeleteDialog) {
        DeleteEntityDialog(delete = deleteItem, confirmAuth = confirmAuth,
            dismiss = { showDeleteDialog = false })
    }
}