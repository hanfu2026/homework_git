package com.sample.git.sample.ui.view.repo

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sample.git.sample.network.model.repos.RepoModel

@Composable
fun RepoInfoView(repoInfo: RepoModel) {

    Text("Owner: ${repoInfo.owner.login}")
    Text("description: ${repoInfo.description}")

    Spacer(Modifier.height(24.dp))
    HorizontalDivider()

}