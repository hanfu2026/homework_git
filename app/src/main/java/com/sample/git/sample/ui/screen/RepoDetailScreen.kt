package com.sample.git.sample.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.sample.git.sample.navigation.navToLogin
import com.sample.git.sample.ui.view.LogoutView
import com.sample.git.sample.ui.view.UnLoginView
import com.sample.git.sample.ui.view.repo.RepoTitleView
import com.sample.git.sample.viewmodel.LoginStatus
import com.sample.git.sample.viewmodel.LoginVM
import com.sample.git.sample.viewmodel.RepoDetailScreenVM
import com.sample.git.sample.viewmodel.TopAppBarVM

@Composable
fun RepoDetailScreen(owner: String,
                     repoName: String,
                     topBarViewModel: TopAppBarVM,
                     loginViewModel: LoginVM,
                     navController: NavHostController
) {
    val loginStatus by loginViewModel.loginStatus.collectAsStateWithLifecycle()
    val oAuthState by loginViewModel.oAuthState.collectAsStateWithLifecycle()
    val viewModel: RepoDetailScreenVM = viewModel()
    val repoInfo by viewModel.repoInfo.collectAsStateWithLifecycle()
    val errMsg by viewModel.errorMessage.observeAsState()

    val context = LocalContext.current
    topBarViewModel.setTitle(repoName)

    viewModel.fetchRepo(oAuthState.accessToken, owner, repoName, { }, { })

    if (loginStatus == LoginStatus.LOGOUT) {
        UnLoginView {
            navToLogin(navController)
        }
    } else {
        Column(Modifier.fillMaxWidth().padding(16.dp)) {
            if (!errMsg.isNullOrBlank()) {
                Text(errMsg!!)
            }
            Spacer(Modifier.height(8.dp))

            repoInfo?.let { RepoTitleView(it) }

            Spacer(Modifier.height(24.dp))

            LogoutView(loginViewModel, context, navController)
        }
    }
}