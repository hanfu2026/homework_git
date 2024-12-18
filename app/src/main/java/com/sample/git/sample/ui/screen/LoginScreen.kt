package com.sample.git.sample.ui.screen


import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.sample.git.sample.utils.PreferenceHelper
import com.sample.git.sample.utils.getRandomString
import com.sample.git.sample.viewmodel.LoginStatus
import com.sample.git.sample.viewmodel.LoginVM
import com.sample.git.sample.viewmodel.TopAppBarVM

@Composable
fun LoginScreen(loginViewModel: LoginVM, topBarViewModel: TopAppBarVM, navController: NavHostController) {
    LaunchedEffect(Unit) {
        topBarViewModel.setTitle("Login")
    }
    LoginWebView(loginViewModel, navController)
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun LoginWebView(loginViewModel: LoginVM, navController: NavHostController){
    val state = getRandomString(16)
    val mUrl = "https://github.com/login/oauth/authorize?client_id=Ov23likfXce7Ok9Vaefm&redirect_uri=gs%3A%2F%2Flogin&scope=user%2Crepo%2Cgist%2Cnotifications%2Cread%3Aorg&state=$state"
    val context = LocalContext.current

    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = object: WebViewClient() {
                @Deprecated("Deprecated in Java")
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    if(url != null && url.startsWith("gs://login?code=")){
                        val code = url.replaceFirst("gs://login?code=", "").split("&").first()
                        Log.d("GS", "code = $code")
                        // ToDo: getAccessToken
                        loginViewModel.setCode(code)
                        PreferenceHelper.saveCode(context, code)
                        loginViewModel.getAccessToken(code, { token ->
                            loginViewModel.getAuthenticatedUser(token, onSuccess = { user ->
                                Log.d("GS", "user = ${user.login}")
                                loginViewModel.setLoginStatus(LoginStatus.LOGIN)
                                onLoginSuccessHandler(navController)
                            }, {
                                onLoginErrorHandler(navController)
                            })
                        }, {
                            onLoginErrorHandler(navController)
                        })

                        return true
                    }
                    return false
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    Log.d("GS", "request: ${request?.url}")
                    Log.d("GS", "error: ${error?.errorCode} ${error?.description}")

                    val isHandleNeeded = when (error?.errorCode) {
                        -6 -> true  // ERR_CONNECTION_CLOSED
                        -8 -> true  // ERR_CONNECTION_TIMED_OUT
                        else -> false
                    }

                    if (isHandleNeeded) {
                        loginViewModel.setCode(PreferenceHelper.getCode(context))
                        loginViewModel.setLoginStatus(LoginStatus.LOGOUT)
                        onLoginErrorHandler(navController)
                    }
                }
            }
            settings.javaScriptEnabled = true
            settings.userAgentString = System.getProperty("http.agent")

            loadUrl(mUrl)
        }

    }, update = {
        it.loadUrl(mUrl)
    })
}

private fun onLoginErrorHandler(navController: NavHostController) {
    navController.navigateUp()
}

private fun onLoginSuccessHandler(navController: NavHostController) {
    navController.navigateUp()
}