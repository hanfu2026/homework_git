package com.sample.git.sample.network.repository


import com.sample.git.sample.network.RetrofitInstance
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
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class GithubRepository {
    private val githubService = RetrofitInstance.githubService

    suspend fun getFeeds(): Response<FeedModel> {
        return githubService.getFeeds()
    }

    suspend fun getUserRepos(user: String): Response<List<RepoModel>> {
        return githubService.getUserRepos(user)
    }

    suspend fun getAuthenticatedUserRepos(accessToken: String, user: String): Response<List<RepoModel>> {
        val token = if (accessToken.isNotBlank()) "Bearer $accessToken" else accessToken
        return githubService.getAuthenticatedUserRepos(token, user)
    }

    suspend fun getAuthenticatedUser(accessToken: String): Response<AuthenticatedUser> {
        return githubService.getAuthenticatedUser("Bearer $accessToken")
    }

    @OptIn(ExperimentalEncodingApi::class)
    suspend fun deleteToken(accessToken: String): Response<ResponseBody> {
        val base = "${Endpoints.CLIENT_ID}:${Endpoints.CLIENT_SECRET}"
        val token = "Basic " + Base64.encode(base.toByteArray())
        return githubService.deleteToken(
            token = token,
            param = DeleteTokenParam(accessToken = accessToken)
        )
    }

    suspend fun searchRepositories(token: String, query: String, order: String, page: Long = 1): Response<SearchRepoModel> {
        return githubService.searchRepositories(
            authToken = "Bearer $token",
            query = query,
            page = page,
            order = order
        )
    }

    suspend fun getRepoInfo(user: String, repo: String): Response<RepoModel> {
            return githubService.getRepoInfo(user = user, repo = repo)
    }

    suspend fun getAuthenticatedRepoInfo(owner: String, repo: String, accessToken: String): Response<RepoModel> {
        return githubService.getAuthenticatedRepoInfo(authToken = accessToken,
            owner = owner,
            repo = repo
        )
    }

    suspend fun getAuthenticatedRepoIssues(authToken: String,
                             owner: String,
                             repo: String): Response<List<RepoIssueModel>> {
//        return githubService.getAuthenticatedRepoIssues(authToken = "Bearer $authToken",
//            owner = owner,
//            repo = repo
//        )
        val ret = githubService.getIssues(token = "Bearer $authToken",
            owner = owner,
            repo = repo,
        )
        return ret
    }

    suspend fun createIssue(token: String,
                            owner: String,
                            repo: String,
                            title: String,
                            body: String?
                            ): Response<IssueCreateModel> {
        return githubService.createIssue(
            authToken = "Bearer $token",
            owner = owner,
            repo = repo,
            create = CreateIssueParam(title, body)
        )
    }
}