package com.sample.git.sample.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.sample.git.sample.ui.screen.DebugScreen
import com.sample.git.sample.ui.screen.FeedScreen
import com.sample.git.sample.ui.screen.LoginScreen
import com.sample.git.sample.ui.screen.OwnerRepoDetailScreen
import com.sample.git.sample.ui.screen.ProfileScreen
import com.sample.git.sample.ui.screen.RepoDetailScreen
import com.sample.git.sample.ui.screen.SearchScreen
import com.sample.git.sample.viewmodel.LoginVM
import com.sample.git.sample.viewmodel.TopAppBarVM

@Composable
fun NavigationComponent(navController: NavHostController, topBarViewModel: TopAppBarVM, loginViewModel: LoginVM) {
    NavHost(navController, startDestination = ScreenRouteFeed) {
        composable<ScreenRouteFeed> {
            FeedScreen(topBarViewModel, navController)
        }
        composable<ScreenRouteLogin> {
            LoginScreen(loginViewModel, topBarViewModel, navController)
        }
        composable<ScreenRouteSearch> {
            SearchScreen(loginViewModel, topBarViewModel, navController)
        }
        composable<ScreenRouteProfile> {
            ProfileScreen(loginViewModel, topBarViewModel, navController)
        }
        composable<ScreenRouteRepoDetail> { backStackEntry ->
            val route = backStackEntry.toRoute<ScreenRouteRepoDetail>()
            RepoDetailScreen(route.owner, route.repo, topBarViewModel,loginViewModel, navController)
        }
        composable<ScreenRouteOwnerRepoDetail> { backStackEntry ->
            val route = backStackEntry.toRoute<ScreenRouteOwnerRepoDetail>()
            OwnerRepoDetailScreen(route.owner, route.repo, topBarViewModel,loginViewModel, navController)
        }
        composable<ScreenRouteDebug> {
            DebugScreen(loginViewModel, topBarViewModel, navController)
        }
    }
}