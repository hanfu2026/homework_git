package com.sample.git.sample.network.model.login.oauth

import com.google.gson.annotations.SerializedName

data class AuthenticatedUser(
    @SerializedName("gists_url")
    val gistsUrl: String = "",
    @SerializedName("repos_url")
    val reposUrl: String = "",
    @SerializedName("two_factor_authentication")
    val twoFactorAuthentication: Boolean = false,
    @SerializedName("following_url")
    val followingUrl: String = "",
    @SerializedName("twitter_username")
    val twitterUsername: String = "",
    @SerializedName("bio")
    val bio: String = "",
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("login")
    val login: String = "",
    @SerializedName("type")
    val type: String = "",
    @SerializedName("blog")
    val blog: String = "",
    @SerializedName("private_gists")
    val privateGists: Int = 0,
    @SerializedName("total_private_repos")
    val totalPrivateRepos: Int = 0,
    @SerializedName("subscriptions_url")
    val subscriptionsUrl: String = "",
    @SerializedName("updated_at")
    val updatedAt: String = "",
    @SerializedName("site_admin")
    val siteAdmin: Boolean = false,
    @SerializedName("disk_usage")
    val diskUsage: Int = 0,
    @SerializedName("collaborators")
    val collaborators: Int = 0,
    @SerializedName("company")
    val company: String = "",
    @SerializedName("owned_private_repos")
    val ownedPrivateRepos: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("public_repos")
    val publicRepos: Int = 0,
    @SerializedName("gravatar_id")
    val gravatarId: String = "",
    @SerializedName("plan")
    val plan: Plan,
    @SerializedName("email")
    val email: String = "",
    @SerializedName("organizations_url")
    val organizationsUrl: String = "",
    @SerializedName("hireable")
    val hireable: Boolean = false,
    @SerializedName("starred_url")
    val starredUrl: String = "",
    @SerializedName("followers_url")
    val followersUrl: String = "",
    @SerializedName("public_gists")
    val publicGists: Int = 0,
    @SerializedName("url")
    val url: String = "",
    @SerializedName("received_events_url")
    val receivedEventsUrl: String = "",
    @SerializedName("followers")
    val followers: Int = 0,
    @SerializedName("avatar_url")
    val avatarUrl: String = "",
    @SerializedName("events_url")
    val eventsUrl: String = "",
    @SerializedName("html_url")
    val htmlUrl: String = "",
    @SerializedName("following")
    val following: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("location")
    val location: String = "",
    @SerializedName("node_id")
    val nodeId: String = "")