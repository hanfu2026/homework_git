package com.sample.git.sample.ui.view

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sample.git.sample.network.model.repos.RepoModel
import com.sample.git.sample.ui.view.repo.RepoListItem
import com.sample.git.sample.ui.view.repo.RepoListItemParam

@Composable
fun UserReposView(repos: List<RepoModel?>, onRepoItemClicked: (index: Int, owner: String, repo: String) -> Unit) {
    if (repos.isEmpty()) {
        NoDataFoundView()
    } else {
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (repos.isNotEmpty()) {
                items(repos.size) { index ->
                    repos[index]?.let { repo ->
                        Log.d("GS", "repo = $repo")
                        RepoListItem(RepoListItemParam(
                            index = index,
                            size = repos.size,
                            owner = repo.owner.login,
                            name = repo.name,
                            description = repo.description,
                            privacy = repo.private,
                            onClick = {
                                onRepoItemClicked(
                                    index,
                                    repo.owner.login,
                                    repo.name
                                )
                            }
                        ))

                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}