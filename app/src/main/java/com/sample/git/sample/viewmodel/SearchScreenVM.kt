package com.sample.git.sample.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.git.sample.network.model.repos.RepoModel
import com.sample.git.sample.network.model.search.SearchRepoModel
import com.sample.git.sample.network.repository.GithubRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchScreenVM : ViewModel() {
    private val repository = GithubRepository()

    private val _repos = MutableLiveData<List<RepoModel?>?>()
    val repos: LiveData<List<RepoModel?>?> = _repos

    val loading = MutableLiveData(false)
    private var job: Job? = null

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
    }

    fun searchRepos(token: String, query: String, order: String, onSuccess: (repos: SearchRepoModel) -> Unit, onError: () -> Unit) {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.searchRepositories(token = token, query = query, order = order)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val ret = response.body()
                    if (ret?.items != null) {
                        _repos.value = ret.items
                        onSuccess(ret)
                    } else {
                        onError("Error : repo null")
                        onError()
                    }
                } else {
                    Log.d("GS", "error: ${response.code()}")
                    onError("Error : ${response.message()} ")
                }
                loading.value = false
            }
        }
    }
}