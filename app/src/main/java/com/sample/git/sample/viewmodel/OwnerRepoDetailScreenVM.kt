package com.sample.git.sample.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.git.sample.network.model.issue.create.IssueCreateModel
import com.sample.git.sample.network.model.repos.RepoModel
import com.sample.git.sample.network.model.repos.issue.RepoIssueModel
import com.sample.git.sample.network.repository.GithubRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OwnerRepoDetailScreenVM : ViewModel() {
    private val _repoInfo = MutableStateFlow<RepoModel?>(null)
    val repoInfo: StateFlow<RepoModel?> = _repoInfo.asStateFlow()

    private val _issues = MutableStateFlow<List<RepoIssueModel?>>(listOf())
    val issues: StateFlow<List<RepoIssueModel?>> = _issues.asStateFlow()

    private val repository = GithubRepository()

    val repoLoading = MutableLiveData(false)
    val issueLoading = MutableLiveData(false)
    val createLoading = MutableLiveData(false)

    private var job: Job? = null
    private var getIssueJob: Job? = null
    private var createIssueJob: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("GS", "Error: ${throwable.localizedMessage}")
    }

    val errorMessage = MutableLiveData<String>()
    private fun onError(message: String) {
        errorMessage.value = message
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
        getIssueJob?.cancel()
        createIssueJob?.cancel()
    }

    fun fetchRepoInfo(owner: String, repoName: String, accessToken: String, onSuccess: (repo: RepoModel) -> Unit, onErrMsg: (err: String) -> Unit) {
        repoLoading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.getAuthenticatedRepoInfo(owner = owner, repo = repoName, accessToken = accessToken)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val repo = response.body()
                    if (repo != null) {
                        _repoInfo.value = repo
                        onSuccess(repo)
                    } else {
                        onError("Error : repo null")
                    }
                } else {
                    Log.d("GS", "error: ${response.code()}")
                    onError("Error : ${response.message()} ")
                }
                repoLoading.value = false
            }
        }
    }

    fun fetchIssues(owner: String, repoName: String, accessToken: String, onSuccess: (repo: RepoModel) -> Unit, onErrMsg: (err: String) -> Unit) {
        issueLoading.value = true
        getIssueJob = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.getAuthenticatedRepoIssues(authToken = accessToken, owner = owner, repo = repoName)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val ret = response.body()
                    Log.d("GS", "response = $response")
                    if (ret != null) {
                        _issues.value = ret
                        Log.d("GS", "ret = $ret")
                    } else {
                        onError("Error : repo issue null")
                    }
                } else {
                    Log.d("GS", "error: ${response.code()}")
                    onError("Error : ${response.message()} ")
                }
                issueLoading.value = false
            }
        }
    }

    fun createIssue(accessToken: String
                    , owner: String
                    , repoName: String
                    , title: String
                    , body: String?
                    , onSuccess: (repo: IssueCreateModel) -> Unit
                    , onErrMsg: (err: String) -> Unit
    ) {
        createLoading.value = true
        createIssueJob = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.createIssue(token = accessToken
                , owner = owner
                , repo = repoName
                , title = title
                , body = body
            )
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val repo = response.body()
                    if (repo != null) {
                        onSuccess(repo)
                    } else {
                        onError("Error : repo null")
                    }
                } else {
                    Log.d("GS", "error: ${response.code()}")
                    onError("Error : ${response.message()} ")
                    onErrMsg(errorMessage.value ?: "")
                }
                createLoading.value = false
            }
        }
    }
}