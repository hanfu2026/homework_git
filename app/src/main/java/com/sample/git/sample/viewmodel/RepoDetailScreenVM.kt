package com.sample.git.sample.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.git.sample.network.model.repos.RepoModel
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

class RepoDetailScreenVM : ViewModel() {
    private val _repoInfo = MutableStateFlow<RepoModel?>(null)
    val repoInfo: StateFlow<RepoModel?> = _repoInfo.asStateFlow()

    private val repository = GithubRepository()

    val repoLoading = MutableLiveData(false)

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

    fun fetchRepo(token: String, user: String, repoName: String, onSuccess: (repo: RepoModel) -> Unit, onErrMsg: (err: String) -> Unit) {
        repoLoading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
//            val response = repository.getRepoInfo(user = user, repo = repoName)
            val response = repository.getAuthenticatedRepoInfo(accessToken = token, owner = user, repo = repoName)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val repo = response.body()
                    if (repo != null) {
                        _repoInfo.value = repo
                        onSuccess(repo)
                    } else {
                        onError("Error : repo null")
                        onErrMsg(errorMessage.value ?: "")
                    }
                } else {
                    Log.d("GS", "error: ${response.code()}")
                    onError("Error : ${response.message()} ")
                    onErrMsg(errorMessage.value ?: "")
                }
                repoLoading.value = false
            }
        }
    }
}