package com.sample.git.sample.ui.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.sample.git.sample.ui.view.IssueListItemParam
import com.sample.git.sample.ui.view.IssueListItemView
import com.sample.git.sample.ui.view.LogoutView
import com.sample.git.sample.ui.view.repo.RepoTitleView
import com.sample.git.sample.viewmodel.LoginVM
import com.sample.git.sample.viewmodel.OwnerRepoDetailScreenVM
import com.sample.git.sample.viewmodel.TopAppBarVM

@Composable
fun OwnerRepoDetailScreen(owner: String,
                          repoName: String,
                          topBarViewModel: TopAppBarVM,
                          loginViewModel: LoginVM,
                          navController: NavHostController
) {
    val viewModel: OwnerRepoDetailScreenVM = viewModel()
    val repoInfo by viewModel.repoInfo.collectAsStateWithLifecycle()
    val issues by viewModel.issues.collectAsStateWithLifecycle()
    val oAuthState by loginViewModel.oAuthState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    topBarViewModel.setTitle(repoName)

    viewModel.fetchRepoInfo(accessToken = oAuthState.accessToken
        , owner = owner
        , repoName = repoName
        , onSuccess = { }
        , onErrMsg = { }
    )

    viewModel.fetchIssues(owner = owner
        , repoName = repoName
        , accessToken = oAuthState.accessToken
        , { }
        , { }
    )


    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            repoInfo?.let { RepoTitleView(it) }
        }
        if (issues.isNotEmpty()) {
            item {
                Spacer(Modifier.height(16.dp))
                Text("Issues:"
                    , modifier = Modifier.padding(horizontal = 16.dp)
                    , style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(16.dp))
            }
            items(issues.size) { index ->
                val issue = issues[index]
                if (issue != null) {
                    IssueListItemView(IssueListItemParam(
                        index = index,
                        size = issues.size,
                        title = issue.title,
                        body = issue.body,
                        onClick = { }
                    ))
                    Spacer(Modifier.height(8.dp))
                }
            }
        }

        item {
            Spacer(Modifier.height(24.dp))
        }
        // create issue
        item {
            BtnCreateIssue(onCreateIssue = {
                viewModel.createIssue(accessToken = oAuthState.accessToken,
                    owner = owner,
                    repoName = repoName,
                    title = "New issue",
                    body = "This issue was created by app",
                    onSuccess = {
                        informUser(context, "Issue created successfully.")
                        viewModel.fetchIssues(
                            accessToken = oAuthState.accessToken,
                            owner = owner,
                            repoName = repoName,
                            onSuccess = {},
                            onErrMsg = {}
                        )
                    },
                    onErrMsg = { err ->
                        informUser(context, err)
                    })
            })
        }
        item {
            Spacer(Modifier.height(24.dp))
            LogoutView(loginViewModel, context, navController)
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun BtnCreateIssue(onCreateIssue: () -> Unit) {
    Button(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp), onClick = {
        onCreateIssue()
    }) {
        Text("Create an issue")
    }
}

fun informUser(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}