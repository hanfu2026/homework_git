package com.sample.git.sample.network.model.login.oauth

import com.google.gson.annotations.SerializedName

data class Plan(
    @SerializedName("private_repos")
    val privateRepos: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("collaborators")
    val collaborators: Int = 0,
    @SerializedName("space")
    val space: Int = 0
)