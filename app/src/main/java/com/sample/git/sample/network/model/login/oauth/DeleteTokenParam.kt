package com.sample.git.sample.network.model.login.oauth

import com.google.gson.annotations.SerializedName

data class DeleteTokenParam(
    @SerializedName("access_token")
    val accessToken : String
)
