package com.sample.git.sample.network.model.search

import com.google.gson.annotations.SerializedName

data class RepoItem(@SerializedName("stargazers_count")
                     val stargazersCount: Int = 0,
                    @SerializedName("pushed_at")
                     val pushedAt: String = "",
                    @SerializedName("language")
                     val language: String = "",
                    @SerializedName("subscription_url")
                     val subscriptionUrl: String = "",
                    @SerializedName("branches_url")
                     val branchesUrl: String = "",
                    @SerializedName("issue_comment_url")
                     val issueCommentUrl: String = "",
                    @SerializedName("labels_url")
                     val labelsUrl: String = "",
                    @SerializedName("score")
                     val score: Int = 0,
                    @SerializedName("subscribers_url")
                     val subscribersUrl: String = "",
                    @SerializedName("releases_url")
                     val releasesUrl: String = "",
                    @SerializedName("svn_url")
                     val svnUrl: String = "",
                    @SerializedName("id")
                     val id: Int = 0,
                    @SerializedName("master_branch")
                     val masterBranch: String = "",
                    @SerializedName("forks")
                     val forks: Int = 0,
                    @SerializedName("archive_url")
                     val archiveUrl: String = "",
                    @SerializedName("git_refs_url")
                     val gitRefsUrl: String = "",
                    @SerializedName("forks_url")
                     val forksUrl: String = "",
                    @SerializedName("visibility")
                     val visibility: String = "",
                    @SerializedName("statuses_url")
                     val statusesUrl: String = "",
                    @SerializedName("ssh_url")
                     val sshUrl: String = "",
                    @SerializedName("license")
                     val license: License,
                    @SerializedName("full_name")
                     val fullName: String = "",
                    @SerializedName("size")
                     val size: Int = 0,
                    @SerializedName("languages_url")
                     val languagesUrl: String = "",
                    @SerializedName("html_url")
                     val htmlUrl: String = "",
                    @SerializedName("collaborators_url")
                     val collaboratorsUrl: String = "",
                    @SerializedName("clone_url")
                     val cloneUrl: String = "",
                    @SerializedName("name")
                     val name: String = "",
                    @SerializedName("pulls_url")
                     val pullsUrl: String = "",
                    @SerializedName("default_branch")
                     val defaultBranch: String = "",
                    @SerializedName("hooks_url")
                     val hooksUrl: String = "",
                    @SerializedName("trees_url")
                     val treesUrl: String = "",
                    @SerializedName("tags_url")
                     val tagsUrl: String = "",
                    @SerializedName("private")
                     val private: Boolean = false,
                    @SerializedName("contributors_url")
                     val contributorsUrl: String = "",
                    @SerializedName("has_downloads")
                     val hasDownloads: Boolean = false,
                    @SerializedName("open_issues_count")
                     val openIssuesCount: Int = 0,
                    @SerializedName("notifications_url")
                     val notificationsUrl: String = "",
                    @SerializedName("description")
                     val description: String = "",
                    @SerializedName("created_at")
                     val createdAt: String = "",
                    @SerializedName("watchers")
                     val watchers: Int = 0,
                    @SerializedName("deployments_url")
                     val deploymentsUrl: String = "",
                    @SerializedName("keys_url")
                     val keysUrl: String = "",
                    @SerializedName("has_projects")
                     val hasProjects: Boolean = false,
                    @SerializedName("archived")
                     val archived: Boolean = false,
                    @SerializedName("has_wiki")
                     val hasWiki: Boolean = false,
                    @SerializedName("updated_at")
                     val updatedAt: String = "",
                    @SerializedName("comments_url")
                     val commentsUrl: String = "",
                    @SerializedName("stargazers_url")
                     val stargazersUrl: String = "",
                    @SerializedName("disabled")
                     val disabled: Boolean = false,
                    @SerializedName("git_url")
                     val gitUrl: String = "",
                    @SerializedName("has_pages")
                     val hasPages: Boolean = false,
                    @SerializedName("owner")
                     val owner: Owner,
                    @SerializedName("commits_url")
                     val commitsUrl: String = "",
                    @SerializedName("compare_url")
                     val compareUrl: String = "",
                    @SerializedName("git_commits_url")
                     val gitCommitsUrl: String = "",
                    @SerializedName("blobs_url")
                     val blobsUrl: String = "",
                    @SerializedName("git_tags_url")
                     val gitTagsUrl: String = "",
                    @SerializedName("merges_url")
                     val mergesUrl: String = "",
                    @SerializedName("downloads_url")
                     val downloadsUrl: String = "",
                    @SerializedName("has_issues")
                     val hasIssues: Boolean = false,
                    @SerializedName("url")
                     val url: String = "",
                    @SerializedName("contents_url")
                     val contentsUrl: String = "",
                    @SerializedName("mirror_url")
                     val mirrorUrl: String = "",
                    @SerializedName("milestones_url")
                     val milestonesUrl: String = "",
                    @SerializedName("teams_url")
                     val teamsUrl: String = "",
                    @SerializedName("fork")
                     val fork: Boolean = false,
                    @SerializedName("issues_url")
                     val issuesUrl: String = "",
                    @SerializedName("events_url")
                     val eventsUrl: String = "",
                    @SerializedName("issue_events_url")
                     val issueEventsUrl: String = "",
                    @SerializedName("assignees_url")
                     val assigneesUrl: String = "",
                    @SerializedName("open_issues")
                     val openIssues: Int = 0,
                    @SerializedName("watchers_count")
                     val watchersCount: Int = 0,
                    @SerializedName("node_id")
                     val nodeId: String = "",
                    @SerializedName("homepage")
                     val homepage: String = "",
                    @SerializedName("forks_count")
                     val forksCount: Int = 0)