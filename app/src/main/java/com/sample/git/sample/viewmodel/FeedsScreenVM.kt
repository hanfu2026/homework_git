package com.sample.git.sample.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.git.sample.network.model.feed.FeedModel
import com.sample.git.sample.network.repository.GithubRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FeedsScreenVM: ViewModel() {
    private val repository = GithubRepository()

    private val _feeds = MutableLiveData<FeedModel>()
    val feeds: LiveData<FeedModel> = _feeds

    private val loading = MutableLiveData(false)
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

    fun fetchFeeds() {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.getFeeds()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val feeds = response.body()
                    if (feeds != null) {

                    } else {
                        onError("Error : feeds data null ")
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