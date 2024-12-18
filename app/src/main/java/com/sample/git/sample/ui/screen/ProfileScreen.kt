package com.sample.git.sample.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
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
import com.sample.git.sample.navigation.navToOwnerRepoDetail
import com.sample.git.sample.ui.view.LogoutView
import com.sample.git.sample.ui.view.NoDataFoundView
import com.sample.git.sample.ui.view.UnLoginView
import com.sample.git.sample.ui.view.UserReposView
import com.sample.git.sample.viewmodel.LoginStatus
import com.sample.git.sample.viewmodel.LoginVM
import com.sample.git.sample.viewmodel.ProfileScreenVM
import com.sample.git.sample.viewmodel.TopAppBarVM


@Composable
fun ProfileScreen(loginViewModel: LoginVM,
                  topBarViewModel: TopAppBarVM,
                  navController: NavHostController) {
    val viewModel: ProfileScreenVM = viewModel()
    val oAuthState by loginViewModel.oAuthState.collectAsStateWithLifecycle()
    val loginStatus by loginViewModel.loginStatus.collectAsStateWithLifecycle()
    val authenticatedUser by loginViewModel.authenticatedUser.collectAsStateWithLifecycle()
    val repos by viewModel.userRepos.observeAsState()

    val context = LocalContext.current
    topBarViewModel.setTitle("Profile")

    if (loginStatus == LoginStatus.LOGIN) {
        if (!authenticatedUser?.login.isNullOrBlank()) {
            authenticatedUser?.login?.let { viewModel.fetchUserRepo(oAuthState.accessToken, it) }
        }
    }

    if (loginStatus == LoginStatus.LOGOUT) {
        UnLoginView {
            navToLogin(navController)
        }
    } else {
            Column(Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
            if (authenticatedUser != null) {
                Text(authenticatedUser!!.login, style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(24.dp))

                Text("Repositories: (${ if(repos == null) 0 else repos!!.size })"
                    , style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(16.dp))

                if (repos != null) {
                    UserReposView(repos!!) { _, owner, name ->
                        navToOwnerRepoDetail(navController, owner, name)
                    }
                }
                Spacer(Modifier.height(12.dp))
            } else {
                NoDataFoundView()
            }
                Spacer(Modifier.height(24.dp))
                LogoutView(loginViewModel, context, navController)
        }
    }
}