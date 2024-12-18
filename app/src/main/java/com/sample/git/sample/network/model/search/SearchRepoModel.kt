package com.sample.git.sample.network.model.search

import com.google.gson.annotations.SerializedName
import com.sample.git.sample.network.model.repos.RepoModel

data class SearchRepoModel(@SerializedName("total_count")
                      val totalCount: Int = 0,
                           @SerializedName("incomplete_results")
                      val incompleteResults: Boolean = false,
                           @SerializedName("items")
                      val items: List<RepoModel>?)