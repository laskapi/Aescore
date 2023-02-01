package com.gmail.in2horizon.aescore.superview


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.in2horizon.aescore.R
import com.gmail.in2horizon.aescore.data.Competition
import com.gmail.in2horizon.aescore.data.User
import com.gmail.in2horizon.aescore.viewmodel.CompsViewModel
import com.gmail.in2horizon.aescore.viewmodel.UsersViewModel
import com.gmail.in2horizon.aescore.views.`super`.ItemsList
import com.gmail.in2horizon.aescore.views.`super`.UserDetails
import com.gmail.in2horizon.aescore.views.`super`.UsersListElement
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch


enum class SuperTabs() {
    Users, Competitions
}

@OptIn(ExperimentalPagerApi::class, ExperimentalAnimationApi::class)
@Composable
fun SuperRootScreen(

) {

    val contentDescription = stringResource(id = R.string.super_screen_description)

    val navController = rememberAnimatedNavController()

    val usersViewModel: UsersViewModel = hiltViewModel()
    val compsViewModel: CompsViewModel = hiltViewModel()

    Column(modifier = Modifier.semantics { this.contentDescription = contentDescription }) {
        Text(text = "Witaj SUPER")

        val pagerState = rememberPagerState(0)
        val scope = rememberCoroutineScope()


        TabRow(selectedTabIndex = pagerState.currentPage) {
            SuperTabs.values().forEachIndexed { index, tab ->
                Tab(selected = pagerState.currentPage == index, onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }, text = {
                    Text(
                        text = tab.name, maxLines = 2, overflow = TextOverflow.Ellipsis
                    )
                })
            }
        }

        HorizontalPager(count = SuperTabs.values().size, state = pagerState) { page ->
            when (page) {
                0 -> {
                    TabManager(navController, {
                        ItemsList(items = usersViewModel.users,
                            filter = { list, string -> filterUsers(list as List<User>, string) },
                            errorMessage = usersViewModel.errorMessage,
                            addNewItem = {
                                usersViewModel.loadSelectedUser(null)
                                navController.navigate((TabState.DETAILS.name))
                            },
                            listElement = {
                                UsersListElement(
                                    user = it as User,
                                    showUserDetails = {
                                        usersViewModel.loadSelectedUser(it.id)
                                        navController.navigate(TabState.DETAILS.name/* + "/${it.id}"*/)
                                    },
                                    deleteUser = { usersViewModel.deleteUser(it.id) },
                                    confirmAuth = usersViewModel::confirmAuthAsync
                                )
                            }

                        )
                    }, {
                        UserDetails(usersViewModel = usersViewModel,
                            back = { navController.navigate(TabState.LIST.name) })

                    }

                    )
                }

                1 -> TabManager(
                    navController, {
                        ItemsList(
                            items = compsViewModel.comps,
                            filter = { list, string ->
                                filterComps(
                                    list as List<Competition>,
                                    string
                                )
                            },
                            errorMessage = compsViewModel.errorMessage,
                            addNewItem = {
                                compsViewModel.loadSelectedComp(null)
                                compsViewModel.loadUsers()
                                navController.navigate((TabState.DETAILS.name))
                            },
                            listElement = {
                                CompListElement(
                                    item = it as Competition,
                                    showDetails = {
                                        compsViewModel.loadSelectedComp(it.id)
                                        compsViewModel.loadUsers()
                                        navController.navigate(TabState.DETAILS.name)
                                    },
                                    deleteItem = {compsViewModel.deleteComp(id = it.id)},
                                    confirmAuth = compsViewModel::confirmAuthAsync
                                )
                            }
                        )
                    },
                    {
                        CompDetails(compViewModel=compsViewModel) { navController.navigate(TabState.LIST.name) }
                    }
                )

            }

        }

    }

}

fun filterUsers(list: List<User>, searchText: String): List<User> {
    return list.filter {
        it.username.contains(searchText, true) || it.email.contains(searchText, true)
    }
}

fun filterComps(list: List<Competition>, searchText: String): List<Competition> {
    return list.filter {
        it.name.contains(searchText, true)
    }
}
