package com.sample.git.sample.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.git.sample.network.model.repos.RepoModel
import com.sample.git.sample.network.repository.GithubRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileScreenVM: ViewModel() {
    private val _userRepos = MutableLiveData<List<RepoModel>?>()
    val userRepos: LiveData<List<RepoModel>?> = _userRepos

    private val repository = GithubRepository()

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

    fun fetchUserRepo(token: String, user: String) {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.getAuthenticatedUserRepos(token, user)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val repos = response.body()
                    if (repos != null) {
                        _userRepos.value = repos
                    } else {
                        onError("Error : repo null")
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
