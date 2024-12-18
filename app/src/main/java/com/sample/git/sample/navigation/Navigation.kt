package com.sample.git.sample.navigation

import androidx.navigation.NavHostController


fun navToLogin(navController: NavHostController) {
    navController.navigate(ScreenRouteLogin) {
        launchSingleTop = true
    }
}

fun navToSearch(navController: NavHostController) {
    navController.navigate(ScreenRouteSearch) {
        launchSingleTop = true
    }
}

fun navToProfile(navController: NavHostController) {
    navController.navigate(ScreenRouteProfile) {
        launchSingleTop = true
    }
}

fun navToDebug(navController: NavHostController) {
    navController.navigate(ScreenRouteDebug) {
        launchSingleTop = true
    }
}

fun navToRepoDetail(navController: NavHostController, owner: String, repo: String) {
    navController.navigate(ScreenRouteRepoDetail(owner = owner, repo = repo)) {
        launchSingleTop = true
    }
}

fun navToOwnerRepoDetail(navController: NavHostController, owner: String, repo: String) {
    navController.navigate(ScreenRouteOwnerRepoDetail(owner = owner, repo = repo)) {
        launchSingleTop = true
    }
}
