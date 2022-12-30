package com.gmail.in2horizon.aescore.views.superuser


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import com.gmail.in2horizon.aescore.R
import com.gmail.in2horizon.aescore.model.CompetitionViewModel
import com.gmail.in2horizon.aescore.model.LoginViewModel
import com.gmail.in2horizon.aescore.model.MyViewModel
import com.gmail.in2horizon.aescore.views.TabCompetitions
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch


enum class SuperTabs() {
    Users,
    Competitions
}

@OptIn(ExperimentalPagerApi::class, ExperimentalAnimationApi::class)
@Composable
fun SuperScreen(
    loginViewModel: LoginViewModel,
    compViewModel: CompetitionViewModel
) {


    val contentDescription = stringResource(id = R.string.super_screen_description)

    val navController = rememberAnimatedNavController()
    val errorMessage by loginViewModel.errorMessage.collectAsState()
    var entityToDelete: Long? by remember { mutableStateOf(null) }




    Column(modifier = Modifier.semantics { this.contentDescription = contentDescription }) {
        Text(text = "Witaj SUPER")

        val pagerState = rememberPagerState(0)
        val scope = rememberCoroutineScope()

        TabRow(selectedTabIndex = pagerState.currentPage) {
            SuperTabs.values().forEachIndexed { index, tab ->
                Tab(selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(
                            text = tab.name,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                )
            }
        }

        HorizontalPager(count = SuperTabs.values().size, state = pagerState) { page ->
            when (page) {
                0 -> {
                    TabUsers(loginViewModel, navController,
                        {
                            UsersListScreenCompose(/*loginViewModel = loginViewModel,*/
                                showUserDetails = {
                                    loginViewModel.loadSelectedEntity(it)
                                    navController.navigate(TabState.DETAILS.name + "/${it}")
                                }, deleteUser = { entityToDelete = it })
                        },
                        {
                            UserDetailsScreenCompose(
                                loginViewModel = loginViewModel,
                                back = { navController.navigate(TabState.LIST.name) })

                        }

                    )
                }

//                1 -> TabUsers(compViewModel)

            }

        }

    }
    if (errorMessage != loginViewModel.NO_ERROR && entityToDelete == null) {

        val msg = stringResource(id = (errorMessage))
        AlertDialog(onDismissRequest = { loginViewModel.clearErrorMessage() },
            confirmButton = {
                ElevatedButton(onClick = { loginViewModel.clearErrorMessage() }) {
                    Text(text = stringResource(id = android.R.string.ok))
                }
            },
            text = { Text(text = msg, style = MaterialTheme.typography.titleLarge) })

    }

    entityToDelete?.let {
        DeleteEntityDialog(viewModel = loginViewModel, it, dismiss = { entityToDelete = null })
    }

}