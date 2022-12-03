package com.gmail.in2horizon.aescore.views.superuser


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
import com.gmail.in2horizon.aescore.R
import com.gmail.in2horizon.aescore.model.LoginViewModel
import com.gmail.in2horizon.aescore.views.CompetitionsTab
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch


enum class SuperTabs() {
    Users,
    Competitions
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SuperScreen(
    loginViewModel: LoginViewModel,
    modifier: Modifier = Modifier
) {
    val contentDescription = stringResource(id = R.string.super_screen_description)

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
                    loginViewModel.loadUsers()
                    UsersTab(loginViewModel)
                }

                1 -> CompetitionsTab()

            }

        }

    }


}