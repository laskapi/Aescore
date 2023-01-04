package com.gmail.in2horizon.aescore.views.superuser

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.gmail.in2horizon.aescore.R
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

enum class TabState {
    LIST, DETAILS
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TabManager(
    navController: NavHostController,
    listCompose: @Composable () -> Unit,
    detailCompose: @Composable () -> Unit
) {

    val TAG = "tabManager"
    val contentDescription = stringResource(id = R.string.users_nav_host)

    AnimatedNavHost(
        navController, TabState.LIST.name,
        modifier = Modifier.semantics { this.contentDescription = contentDescription },
        contentAlignment = Alignment.Center,
    ) {

        composable(TabState.LIST.name, enterTransition = {

            when (initialState.destination.route) {
                TabState.DETAILS.name -> null
                else -> EnterTransition.None
            }
        }, exitTransition = {
            when (targetState.destination.route) {
                TabState.DETAILS.name -> ExitTransition.None
                else -> ExitTransition.None
            }
        }) {
            listCompose()
        }

        composable(TabState.DETAILS.name + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType }),
            enterTransition = null,
            exitTransition = { ExitTransition.None }) { NavBackStackEntry ->
            //    val id = NavBackStackEntry.arguments?.getLong("id") ?: viewModel.EMPTY_ID
            detailCompose()


        }


    }

}

