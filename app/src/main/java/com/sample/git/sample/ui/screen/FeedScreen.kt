package com.sample.git.sample.ui.screen

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.sample.git.sample.navigation.navToRepoDetail
import com.sample.git.sample.network.constant.Endpoints
import com.sample.git.sample.viewmodel.TopAppBarVM

@Composable
fun FeedScreen(topAppBarVM: TopAppBarVM, navController: NavHostController) {
    topAppBarVM.setTitle("Trending")

    Column {
        FeedWebView(Endpoints.TRENDING_URL, navController)
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun FeedWebView(url: String, navController: NavHostController){
    var backEnabled by remember { mutableStateOf(false)}
    var webView: WebView? = null

    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = object: WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    backEnabled = view?.canGoBack() == true
                }

//                override fun onPageFinished(view: WebView?, url: String?) {
//                    super.onPageFinished(view, url)
//                    view?.loadUrl("javascript:(function() { document.getElementsById('AppHeader').style.display='none';})()")
//                }

                @Deprecated("Deprecated in Java")
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    Log.d("GS", "url = $url")
                    if(url != null && url.startsWith(Endpoints.BASE_WEB_URL)){
                        val pathStr = url.replace(Endpoints.BASE_WEB_URL, "")
                        val path = pathStr.split("/")
                        if (path.isNotEmpty() && path.size == 2) {
                            Log.d("FH", "shouldOverrideUrlLoading true")
                            navToRepoDetail(navController = navController, path[0], path[1])
                            return true
                        }
                    }
                    return false
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    Log.d("GS", "onReceivedError")
                    Log.d("GS", "error: ${error?.description}")
                    if (error?.description == "net::ERR_CONNECTION_CLOSED") {
                        view?.loadUrl(request?.url.toString())
                        return
                    }
                    super.onReceivedError(view, request, error)
                }
            }
            settings.javaScriptEnabled = true
            settings.userAgentString = System.getProperty("http.agent")
            loadUrl(url)
            webView = this
        }
    }, update = {
        webView = it
        it.loadUrl(url)
    })

    BackHandler(enabled = backEnabled) {
        webView?.goBack()
    }
}