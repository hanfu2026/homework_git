package com.sample.git.sample.network.model.search

import com.google.gson.annotations.SerializedName

data class License(@SerializedName("html_url")
                   val htmlUrl: String = "",
                   @SerializedName("name")
                   val name: String = "",
                   @SerializedName("spdx_id")
                   val spdxId: String = "",
                   @SerializedName("key")
                   val key: String = "",
                   @SerializedName("url")
                   val url: String = "",
                   @SerializedName("node_id")
                   val nodeId: String = "")