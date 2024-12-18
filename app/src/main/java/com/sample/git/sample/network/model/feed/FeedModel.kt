package com.sample.git.sample.network.model.feed

import com.google.gson.annotations.SerializedName

data class FeedModel(
    @SerializedName("security_advisories_url")
    val securityAdvisoriesUrl: String = "",
    @SerializedName("_links")
    val Links: Links,
    @SerializedName("current_user_url")
    val currentUserUrl: String = "",
    @SerializedName("current_user_organization_url")
    val currentUserOrganizationUrl: String = "",
    @SerializedName("current_user_organization_urls")
    val currentUserOrganizationUrls: List<String>?,
    @SerializedName("user_url")
    val userUrl: String = "",
    @SerializedName("current_user_actor_url")
    val currentUserActorUrl: String = "",
    @SerializedName("current_user_public_url")
    val currentUserPublicUrl: String = "",
    @SerializedName("timeline_url")
    val timelineUrl: String = ""
)