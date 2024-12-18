package com.sample.git.sample.network.model.feed

import com.google.gson.annotations.SerializedName

data class SecurityAdvisories(@SerializedName("href")
                              val href: String = "",
                              @SerializedName("type")
                              val type: String = "")