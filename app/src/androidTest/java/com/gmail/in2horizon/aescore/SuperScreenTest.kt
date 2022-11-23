package com.gmail.in2horizon.aescore


import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import com.gmail.in2horizon.aescore.data.AescoreRepository
import com.gmail.in2horizon.aescore.model.LoginViewModel
import com.gmail.in2horizon.aescore.ui.theme.AescoreTheme
import com.gmail.in2horizon.aescore.views.SuperScreen
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
       val loginViewModel= LoginViewModel(, AescoreRepository())


        composeTestRule.setContent {
            AescoreTheme {
                SuperScreen(loginViewModel)
            }
        }

        val context: Context = InstrumentationRegistry.getInstrumentation().getTargetContext()
        val usersTab = context.resources.getString(R.string.users_tab)
        val competitionsTab = context.resources.getString(R.string.competitions_tab)

        composeTestRule.onNodeWithText(usersTab).performClick()
        composeTestRule.onNodeWithText(usersTab+"Compose").assertIsDisplayed()


        composeTestRule.onNodeWithText(competitionsTab).performClick()
        composeTestRule.onNodeWithText(competitionsTab + "Compose").assertIsDisplayed()

    }
}