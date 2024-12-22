package com.sample.git.sample.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.sample.git.sample.viewmodel.LoginVM
import com.sample.git.sample.viewmodel.TopAppBarVM
import junit.framework.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {
    @get: Rule
    val composeTestRule = createComposeRule()
    private lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            NavigationComponent(navController = navController, TopAppBarVM(), LoginVM())
        }
    }

    @Test
    fun appNavHost_verifyStartDestination() {
        navController.assertCurrentRouteName(ScreenRouteFeed.javaClass.name)
    }

    private fun NavController.assertCurrentRouteName(expectedRouteName: String) {
        Assert.assertEquals(expectedRouteName, currentBackStackEntry?.destination?.route)
    }
}
