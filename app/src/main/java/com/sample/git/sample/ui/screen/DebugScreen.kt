package com.sample.git.sample.ui.screen

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.sample.git.sample.navigation.navToLogin
import com.sample.git.sample.network.model.repos.RepoModel
import com.sample.git.sample.network.service.getAuthorizationUrl
import com.sample.git.sample.ui.view.onLogout
import com.sample.git.sample.viewmodel.DebugScreenVM
import com.sample.git.sample.viewmodel.LoginVM
import com.sample.git.sample.viewmodel.OwnerRepoDetailScreenVM
import com.sample.git.sample.viewmodel.RepoDetailScreenVM
import com.sample.git.sample.viewmodel.TopAppBarVM
import com.sample.git.sample.viewmodel.UserRepoVM

@Composable
fun DebugScreen (loginViewModel: LoginVM, topBarViewModel: TopAppBarVM, navController: NavHostController) {
    val viewModel: DebugScreenVM = viewModel()
    val ownerRepoDetailScreenVM: OwnerRepoDetailScreenVM = viewModel()
    val repoDetailViewModel: RepoDetailScreenVM = viewModel()
    val responseText by viewModel.responseText.observeAsState("")
    val loginStatus by loginViewModel.loginStatus.collectAsStateWithLifecycle()
    val oAuthState by loginViewModel.oAuthState.collectAsStateWithLifecycle()
    val authenticatedUser by loginViewModel.authenticatedUser.collectAsStateWithLifecycle()

    val scrollState = rememberScrollState()


    val context = LocalContext.current
    LaunchedEffect(Unit) {
        topBarViewModel.setTitle("Debug")
    }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState)) {
        Spacer(Modifier.height(20.dp))
        ApiButton("Feeds") {
            viewModel.callFeedsApi()
        }
        HorizontalDivider()

        Spacer(Modifier.height(8.dp))
        ApiButton("login from browser") {
            val intent = Intent(Intent.ACTION_VIEW, getAuthorizationUrl())
            context.startActivity(intent)
        }
        Spacer(Modifier.height(8.dp))
        ApiButton("login by native") {
            navToLogin(navController)
        }
        Spacer(Modifier.height(8.dp))
        ApiButton("Get Access Token", oAuthState.code.isNotBlank()) {
            loginViewModel.getAccessToken(oAuthState.code, {

            }, {

            })
        }
        Spacer(Modifier.height(8.dp))
        ApiButton("Get User", oAuthState.accessToken.isNotBlank()) {
            loginViewModel.getAuthenticatedUser(loginViewModel.oAuthState.value.accessToken, { user ->
                viewModel.updateResponseText(user.toString())
            }, {

            })
        }
        Spacer(Modifier.height(8.dp))

        HorizontalDivider()

        Spacer(Modifier.height(8.dp))
        ApiButton("Get Profile repo", !authenticatedUser?.login.isNullOrBlank()) {
            if (authenticatedUser != null && authenticatedUser?.login.isNullOrBlank()) {
//                ownerRepoDetailScreenVM.fetchRepoInfo(
//                    owner = authenticatedUser!!.login,
//                    repoName = "Repo1"
//                    accessToken = oAuthState.value.accessToken,
//                    onSuccess = { repos -> viewModel.updateResponseText(repos.toString()) },
//                    onError = {}
//                )
            }
        }
        Spacer(Modifier.height(8.dp))
        ApiButton("Repo Detail") {
            repoDetailViewModel.fetchRepo(
                token = oAuthState.accessToken,
                "wavetermdev",
                "waveterm",
                { repo ->
                    viewModel.updateResponseText(repo.toString())
                }, {

                }
            )
        }
        Spacer(Modifier.height(8.dp))
        ApiButton("Search Repo", false) {

        }
        Spacer(Modifier.height(8.dp))
        ApiButton("Raise an issue", false) {

        }

        HorizontalDivider()
        Spacer(Modifier.height(8.dp))
        ApiButton("Log out") {
            loginViewModel.deleteAppToken(loginViewModel.oAuthState.value.accessToken, {
                onLogout(loginViewModel, context, navController)
            } , {

            })
        }

        Spacer(Modifier.height(20.dp))

        Text(loginStatus.name)
        if (oAuthState.code.isNotBlank()) {
            Text("code: ${oAuthState.code}")
        }
        if (oAuthState.accessToken.isNotBlank()) {
            Text("accessToken: ${oAuthState.accessToken}")
        }
        if (authenticatedUser?.name?.isNotBlank() == true) {
            Text("User: ${authenticatedUser?.name}")
        }
        Spacer(Modifier.height(8.dp))
        Text("Response:")
        Text(responseText)
    }
}

@Composable
private fun ApiButton(text: String, enabled: Boolean = true, onClick: () -> Unit) {
    Button(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        , enabled = enabled
        , onClick = {
            onClick()
        }) {
        Text(text)
    }
}