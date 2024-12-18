package com.sample.git.sample.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.git.sample.network.model.login.oauth.AuthenticatedUser
import com.sample.git.sample.network.repository.AuthorityRepository
import com.sample.git.sample.network.repository.GithubRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import java.util.zip.GZIPInputStream

class LoginVM: ViewModel() {
    private val _oAuthState = MutableStateFlow(OAuthState())
    val oAuthState: StateFlow<OAuthState> = _oAuthState.asStateFlow()

    private val _authenticatedUser = MutableStateFlow<AuthenticatedUser?>(null)
    val authenticatedUser = _authenticatedUser.asStateFlow()

    private val _loginStatus = MutableStateFlow(LoginStatus.LOGOUT)
    val loginStatus = _loginStatus.asStateFlow()

    private val githubRepository = GithubRepository()
    private val authRepository = AuthorityRepository()

    private val errorMessage = MutableLiveData<String>()
    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.d("GS", "Error: ${throwable.localizedMessage}")
    }
    private val loadingLogin = MutableLiveData(false)
    private val loadingToken = MutableLiveData(false)
    private val loadingUser = MutableLiveData(false)

    private var tokenJob: Job? = null
    private var userJob: Job? = null
    private var loginJob: Job? = null
    private var logoutJob: Job? = null

    fun setCode(code: String) {
        _oAuthState.update { it.copy(code = code) }
    }

    fun setToken(token: String) {
        _oAuthState.update { it.copy(accessToken = token) }
    }

    fun setLoginStatus(status: LoginStatus) {
        _loginStatus.update { status }
    }

    fun clearVMToken() {
        _oAuthState.update { it.copy(code = "", accessToken = "")}
    }

    fun getLoading(): Boolean {
        return (loadingToken.value == true || loadingUser.value == true)
    }

    private fun onError(message: String) {
        errorMessage.value = message
        setLoginStatus(LoginStatus.LOGOUT)
    }

    override fun onCleared() {
        super.onCleared()
        loginJob?.cancel()
        tokenJob?.cancel()
        userJob?.cancel()
        logoutJob?.cancel()
    }

    fun login(onSuccess: (data: ResponseBody) -> Unit, onErrorMsg: (err: String) -> Unit) {
        setLoginStatus(LoginStatus.LOADING)
        loginJob = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = authRepository.login()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        onSuccess(data)
                    } else {
                        onError("Error : data null")
                    }
                } else {
                    onError("Error : ${response.message()} ")
                    onErrorMsg("${response.code()}: ${response.message()}")
                }
                loadingLogin.value = false
            }
        }
    }

    fun getAccessToken(code: String, onSuccess: (token: String) -> Unit, onErrorMsg: (err: String) -> Unit) {
        tokenJob = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = authRepository.getAccessToken(code)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val token = response.body()?.accessToken
                    if (token?.isNotBlank() == true) {
                        Log.d("GS", "access Token: $token")
                        setToken(token)
                        onSuccess(token)
                    } else {
                        onError("Error : access token null ")
                        onErrorMsg("${response.code()}: ${response.message()}")
                        setLoginStatus(LoginStatus.LOGOUT)
                    }
                } else {
                    onError("Error : ${response.message()} ")
                    setLoginStatus(LoginStatus.LOGOUT)
                }
                loadingToken.value = false
            }
        }
    }

    fun getAuthenticatedUser(token: String, onSuccess: (user: AuthenticatedUser) -> Unit, onErrorMsg: (err: String) -> Unit) {
        loadingUser.value = true
        userJob = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = githubRepository.getAuthenticatedUser(token)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val user = response.body()
                    if (user != null) {
                        Log.d("GS", "getAuthenticatedUser: ${user.login}")
                        _authenticatedUser.update {user}
                        setLoginStatus(LoginStatus.LOGIN)
                        onSuccess(user)
                    } else {
                        onError("Error : authenticated user null ")
                        onErrorMsg("${response.code()}: ${response.message()}")
                        setLoginStatus(LoginStatus.LOGOUT)
                    }
                } else {
                    Log.d("GS", "error: ${response.code()}")
                    onError("Error : ${response.message()} ")
                    onErrorMsg("${response.code()}: ${response.message()}")
                    setLoginStatus(LoginStatus.LOGOUT)
                }
                loadingUser.value = false
            }
        }
    }

    fun deleteAppToken(token: String, onSuccess: () -> Unit, onErrorMsg: (err: String) -> Unit) {
        logoutJob = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = githubRepository.deleteToken(token)
            withContext(Dispatchers.Main) {

                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    Log.d("GS", response.message())
                    onError("Error : ${response.message()} ")
                    onErrorMsg(errorMessage.value ?: "")
                }
                loadingUser.value = false
            }
        }
    }
}

enum class LoginStatus {
    LOGOUT,
    LOADING,
    LOGIN
}

data class OAuthState(
    val code: String = "",
    val accessToken: String = "",
    val updateToken: String = "",
    val state: String = "",
)