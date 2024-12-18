package com.sample.git.sample.network.model.issue.create

import com.google.gson.annotations.SerializedName
import com.sample.git.sample.network.model.issue.get.Milestone
import com.sample.git.sample.network.model.issue.get.PullRequest
import com.sample.git.sample.network.model.issue.get.User

data class IssueCreateModel(@SerializedName("assignees")
                            val assignees: List<AssigneesItem>?,
                            @SerializedName("created_at")
                            val createdAt: String? = "",
                            @SerializedName("title")
                            val title: String = "",
                            @SerializedName("body")
                            val body: String? = "",
                            @SerializedName("closed_by")
                            val closedBy: ClosedBy? = null,
                            @SerializedName("labels_url")
                            val labelsUrl: String? = "",
                            @SerializedName("author_association")
                            val authorAssociation: String? = "",
                            @SerializedName("number")
                            val number: Int? = 0,
                            @SerializedName("updated_at")
                            val updatedAt: String? = "",
                            @SerializedName("comments_url")
                            val commentsUrl: String? = "",
                            @SerializedName("active_lock_reason")
                            val activeLockReason: String = "",
                            @SerializedName("id")
                            val id: Long = 0,
                            @SerializedName("repository_url")
                            val repositoryUrl: String = "",
                            @SerializedName("state")
                            val state: String = "",
                            @SerializedName("locked")
                            val locked: Boolean = false,
                            @SerializedName("pull_request")
                            val pullRequest: PullRequest,
                            @SerializedName("state_reason")
                            val stateReason: String = "",
                            @SerializedName("comments")
                            val comments: Int = 0,
//                            @SerializedName("closed_at")
//                            val closedAt: Null = null,
                            @SerializedName("url")
                            val url: String = "",
                            @SerializedName("labels")
                            val labels: List<LabelsItem>?,
                            @SerializedName("milestone")
                            val milestone: Milestone,
                            @SerializedName("events_url")
                            val eventsUrl: String = "",
                            @SerializedName("html_url")
                            val htmlUrl: String = "",
                            @SerializedName("assignee")
                            val assignee: Assignee,
                            @SerializedName("user")
                            val user: User,
                            @SerializedName("node_id")
                            val nodeId: String = "")