package com.sample.git.sample.ui.screen

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.sample.git.sample.navigation.NavigationComponent
import com.sample.git.sample.viewmodel.LoginVM
import com.sample.git.sample.viewmodel.TopAppBarVM
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.xml.sax.Locator

class FeedScreenTest {
    @get: Rule
    val composeTestRule = createComposeRule()
    private lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            NavigationComponent(navController = navController, TopAppBarVM(), LoginVM())

            FeedScreen(TopAppBarVM(), navController)

        }
    }
}