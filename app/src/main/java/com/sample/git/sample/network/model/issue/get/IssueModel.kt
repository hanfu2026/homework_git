package com.sample.git.sample.network.model.issue.get

import com.google.gson.annotations.SerializedName
import com.sample.git.sample.network.model.repos.issue.User

data class IssueModel(
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("body")
    val body: String = "",
    //                      @SerializedName("closed_by")
    //                      val closedBy: Null = null,
    @SerializedName("labels_url")
    val labelsUrl: String = "",
    @SerializedName("author_association")
    val authorAssociation: String = "",
    @SerializedName("number")
    val number: Int = 0,
    @SerializedName("updated_at")
    val updatedAt: String = "",
    //                      @SerializedName("performed_via_github_app")
    //                      val performedViaGithubApp: Null = null,
    @SerializedName("comments_url")
    val commentsUrl: String = "",
    //                      @SerializedName("active_lock_reason")
    //                      val activeLockReason: Null = null,
    @SerializedName("repository_url")
    val repositoryUrl: String = "",
    @SerializedName("id")
    val id: Long = 0,
    @SerializedName("state")
    val state: String = "",
    @SerializedName("locked")
    val locked: Boolean = false,
    @SerializedName("timeline_url")
    val timelineUrl: String = "",
    //                      @SerializedName("state_reason")
    //                      val stateReason: Null = null,
    @SerializedName("comments")
    val comments: Int = 0,
    //                      @SerializedName("closed_at")
    //                      val closedAt: Null = null,
    @SerializedName("url")
    val url: String = "",
    //                      @SerializedName("milestone")
    //                      val milestone: Null = null,
    @SerializedName("events_url")
    val eventsUrl: String = "",
    @SerializedName("html_url")
    val htmlUrl: String = "",
    @SerializedName("reactions")
    val reactions: Reactions,
    //                      @SerializedName("assignee")
    //                      val assignee: Null = null,
    @SerializedName("user")
    val user: User,
    @SerializedName("node_id")
    val nodeId: String = "")