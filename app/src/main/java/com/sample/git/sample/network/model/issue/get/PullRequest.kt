package com.sample.git.sample.network.model.issue.get

import com.google.gson.annotations.SerializedName

data class PullRequest(@SerializedName("patch_url")
                       val patchUrl: String = "",
                       @SerializedName("html_url")
                       val htmlUrl: String = "",
                       @SerializedName("diff_url")
                       val diffUrl: String = "",
                       @SerializedName("url")
                       val url: String = "")