package com.gmail.in2horizon.aescore.views.`super`

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.gmail.in2horizon.aescore.R
import com.gmail.in2horizon.aescore.data.User
import kotlinx.coroutines.Deferred

@Composable
fun UsersListElement(
    user: User, showUserDetails: () -> Unit, deleteUser: () -> Unit, confirmAuth:
        (String) -> Deferred<Boolean>
) {
    val context = LocalContext.current
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
            Text(text = user.username, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = user.email,
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable(enabled = true, onClick = {
                    sendMail(user.email, context)
                })
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            EditButton(onClick = { showUserDetails() })

            Spacer(modifier = Modifier.width(8.dp))

            FilledIconButton(onClick = { showDeleteDialog = true }) {
                Icon(
                    Icons.Rounded.Delete, contentDescription = stringResource(id = R.string.delete)
                )
            }
        }
    }
    if (showDeleteDialog) {
        DeleteEntityDialog(delete = deleteUser, confirmAuth = confirmAuth,
            dismiss = { showDeleteDialog = false })
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
