package com.gmail.in2horizon.aescore.views


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gmail.in2horizon.aescore.R
import com.gmail.in2horizon.aescore.model.LoginViewModel

@Composable
fun SuperScreen(
    loginViewModel: LoginViewModel, modifier: Modifier = Modifier
) {
    var state by remember { mutableStateOf(0) }
    val titles = listOf(
        stringResource(R.string.users_tab),
        stringResource(
            R.string.competitions_tab
        )
    )
    val contentDescription = stringResource(id = R.string.super_screen_description)

    val visible by loginViewModel.user.collectAsState()
    AnimatedVisibility(
        visible = visible.authority.equals("SUPER"),
        enter = fadeIn
            (
            animationSpec = tween
                (2000)
        ),
    ) {


        Column(modifier = Modifier.semantics { this.contentDescription = contentDescription }) {


            Text(text = "Witaj SUPER")


            TabRow(selectedTabIndex = state) {
                titles.forEachIndexed { index, title ->
                    Tab(selected = state == index,
                        onClick = { state = index },
                        text = {
                            Text(
                                text = title,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    )
                }
            }
            when (state) {
                0 -> {
                    loginViewModel.loadUsers()
                    UsersTab(loginViewModel.users)
                }
                1 -> CompetitionsTab()

            }

        }

    }

}