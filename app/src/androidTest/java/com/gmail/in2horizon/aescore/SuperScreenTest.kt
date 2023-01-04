package com.gmail.in2horizon.aescore


import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import com.gmail.in2horizon.aescore.data.UserRepository
import com.gmail.in2horizon.aescore.model.UsersViewModel
import com.gmail.in2horizon.aescore.ui.theme.AescoreTheme
import com.gmail.in2horizon.aescore.views.superuser.SuperScreen
import com.gmail.in2horizon.aescore.views.superuser.SuperTabs
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule

import org.junit.Test

@HiltAndroidTest
class SuperScreenTest {

  /*  @get:Rule
    var hiltRule = HiltAndroidRule(this)
*/
    @get:Rule
    val composeTestRule = createComposeRule()



    @Test
    fun superTabsgetDisplayed() {
       val usersViewModel= UsersViewModel(UserRepository())


        composeTestRule.setContent {
            AescoreTheme {
                SuperScreen(usersViewModel)
            }
        }

        val context: Context = InstrumentationRegistry.getInstrumentation().getTargetContext()
        val usersTab = SuperTabs.UsersTab.name//context.resources.getString(R.string.users_tab)
        val usersNavHost=context.resources.getString(R.string.users_nav_host)


        val competitionsTab = context.resources.getString(R.string.competitions_tab)

        composeTestRule.onNodeWithText(usersTab).performClick()
        composeTestRule.onNodeWithContentDescription(usersNavHost).assertIsDisplayed()


        composeTestRule.onNodeWithText(competitionsTab).performClick()
        composeTestRule.onNodeWithText(competitionsTab + "Compose").assertIsDisplayed()

    }
}