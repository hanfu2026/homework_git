package com.sample.git.sample.network.service

import com.sample.git.sample.network.constant.Endpoints
import com.sample.git.sample.network.model.feed.FeedModel
import com.sample.git.sample.network.model.issue.create.CreateIssueParam
import com.sample.git.sample.network.model.issue.create.IssueCreateModel
import com.sample.git.sample.network.model.login.oauth.AuthenticatedUser
import com.sample.git.sample.network.model.login.oauth.DeleteTokenParam
import com.sample.git.sample.network.model.repos.RepoModel
import com.sample.git.sample.network.model.repos.issue.RepoIssueModel
import com.sample.git.sample.network.model.search.SearchRepoModel
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @Headers("Accept: application/vnd.github+json")
    @GET("feeds")
    suspend fun getFeeds(): Response<FeedModel>

    @Headers("Accept: application/vnd.github+json")
    @GET("user")
    suspend fun getAuthenticatedUser(
        @Header("Authorization") token: String,
    ): Response<AuthenticatedUser>

    @GET("users/{user}/repos")
    suspend fun getUserRepos(
        @Path("user") user: String): Response<List<RepoModel>>

    @GET("users/{user}/repos")
    suspend fun getAuthenticatedUserRepos(
        @Header("Authorization") authToken: String,
        @Path("user") user: String): Response<List<RepoModel>>

    @Headers("Accept: application/vnd.github+json")
    @GET("search/repositories")
    suspend fun searchRepositories(
        @Header("Authorization") authToken: String,
        @Query(value = "q", encoded = true) query: String,
        @Query(value = "sort") sort: String = "stars",
        @Query(value = "order") order: String = "desc",
        @Query("page") page: Long
    ): Response<SearchRepoModel>

    @Headers("Accept: application/vnd.github+json")
    @HTTP(method = "DELETE", path = "applications/{client_id}/token", hasBody = true)
    suspend fun deleteToken(
        @Header("Authorization") token: String,
        @Path("client_id") clientId: String = Endpoints.CLIENT_ID,
        @Body param: DeleteTokenParam
    ): Response<ResponseBody>

    // get repo issue
//    @Headers("Accept: application/vnd.github+json")
//    @GET("repos/{owner}/{repo}/issues")
//    suspend fun getAuthenticatedRepoIssues(
//        @Header("Authorization") authToken: String,
//        @Path("owner") owner: String,
//        @Path("repo") repo: String
//    ): Response<List<IssueModel>>

    @Headers("Accept: application/vnd.github.html")
    @GET("repos/{owner}/{repo}/issues")
    suspend fun getIssues(
        @Header("Authorization") token: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String,
//        @Query("state") state: String,
//        @Query("sort") sortBy: String,
//        @Query("page") page: Int
    ): Response<List<RepoIssueModel>>



    // create an issue
    @Headers("Accept: application/vnd.github+json")
    @POST("repos/{owner}/{repo}/issues")
    suspend fun createIssue(
        @Header("Authorization") authToken: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Body create: CreateIssueParam
    ): Response<IssueCreateModel>

    // get repo
    @Headers("Accept: application/vnd.github+json")
    @GET("repos/{user}/{repo}")
    suspend fun getRepoInfo(
        @Path("user") user: String,
        @Path("repo") repo: String
    ): Response<RepoModel>

    // get owner Repo
    @Headers("Accept: application/vnd.github+json")
    @GET("repos/{owner}/{repo}")
    suspend fun getAuthenticatedRepoInfo(
        @Header("Authorization") authToken: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<RepoModel>
}