package com.sample.git.sample.network.model.feed

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("href")
    private val href: String = "",
    @SerializedName("type")
    private val type: String = ""
)