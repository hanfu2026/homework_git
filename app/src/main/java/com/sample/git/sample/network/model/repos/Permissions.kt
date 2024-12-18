package com.sample.git.sample.network.model.repos

import com.google.gson.annotations.SerializedName

data class Permissions(@SerializedName("pull")
                       val pull: Boolean = false,
                       @SerializedName("maintain")
                       val maintain: Boolean = false,
                       @SerializedName("admin")
                       val admin: Boolean = false,
                       @SerializedName("triage")
                       val triage: Boolean = false,
                       @SerializedName("push")
                       val push: Boolean = false)