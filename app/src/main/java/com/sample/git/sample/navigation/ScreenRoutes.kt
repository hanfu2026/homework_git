package com.sample.git.sample.navigation

import kotlinx.serialization.Serializable

@Serializable
object ScreenRouteFeed
@Serializable
object ScreenRouteLogin
@Serializable
object ScreenRouteSearch
@Serializable
object ScreenRouteProfile
@Serializable
data class ScreenRouteRepoDetail(val owner: String, val repo: String)
@Serializable
data class ScreenRouteOwnerRepoDetail(val owner: String, val repo: String)
@Serializable
object ScreenRouteDebug