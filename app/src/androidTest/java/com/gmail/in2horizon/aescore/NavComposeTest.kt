package com.gmail.in2horizon.aescore

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import com.gmail.in2horizon.aescore.data.AescoreRepository
import com.gmail.in2horizon.aescore.model.LoginViewModel
import com.gmail.in2horizon.aescore.ui.theme.AescoreTheme
import com.gmail.in2horizon.aescore.views.NavCompose
import org.junit.Rule
import org.junit.Test

class NavComposeTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginRedirectsToSuperScreen() {

        composeTestRule.setContent {
            AescoreTheme {
                val loginViewModel = LoginViewModel(AescoreRepository())
                NavCompose(loginViewModel = loginViewModel)
            }
        }
        val context: Context = InstrumentationRegistry.getInstrumentation().getTargetContext()

        composeTestRule.onNodeWithText(
            text = context.getString(R.string.username)
        ).performTextInput("super")

        composeTestRule.onNodeWithText(
            text = context.getString(R.string.password)
        ).performTextInput("super")

        composeTestRule.onNodeWithText(
            text = context.getString(R.string.login)
        ).performClick()

        Thread.sleep(3000)

        composeTestRule.onNodeWithContentDescription(
            label=context.getString(R.string.super_screen_description)
        ).assertIsDisplayed()
    }
}