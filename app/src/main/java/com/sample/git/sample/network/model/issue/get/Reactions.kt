package com.sample.git.sample.network.model.issue.get

import com.google.gson.annotations.SerializedName

data class Reactions(
    @SerializedName("confused")
    val confused: Int = 0,
//    @SerializedName("-1")
//    val : Int = 0,
    @SerializedName("total_count")
    val totalCount: Int = 0,
//    @SerializedName("+1")
//    val : Int = 0,
    @SerializedName("rocket")
    val rocket: Int = 0,
    @SerializedName("hooray")
    val hooray: Int = 0,
    @SerializedName("eyes")
    val eyes: Int = 0,
    @SerializedName("url")
    val url: String = "",
    @SerializedName("laugh")
    val laugh: Int = 0,
    @SerializedName("heart")
    val heart: Int = 0)