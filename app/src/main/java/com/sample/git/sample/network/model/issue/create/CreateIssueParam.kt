package com.sample.git.sample.network.model.issue.create

import com.google.gson.annotations.SerializedName

data class CreateIssueParam(
    @SerializedName("title")
    val title: String = "",
    @SerializedName("body")
    val body: String? = "",
)
