package com.sample.git.sample.network.model.feed

import com.google.gson.annotations.SerializedName

data class Links(@SerializedName("current_user_organization")
                 val currentUserOrganization: CurrentUserOrganization,
                 @SerializedName("current_user_organizations")
                 val currentUserOrganizations: List<CurrentUserOrganizationsItem>?,
                 @SerializedName("current_user_actor")
                 val currentUserActor: CurrentUserActor,
                 @SerializedName("timeline")
                 val timeline: Timeline,
                 @SerializedName("user")
                 val user: User,
                 @SerializedName("security_advisories")
                 val securityAdvisories: SecurityAdvisories,
                 @SerializedName("current_user_public")
                 val currentUserPublic: CurrentUserPublic,
                 @SerializedName("current_user")
                 val currentUser: CurrentUser)