package com.sample.git.sample.ui.view.repo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sample.git.sample.network.model.repos.RepoModel

@Composable
fun RepoTitleView(repoInfo: RepoModel) {
    Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp), Arrangement.SpaceBetween) {
        Text("${repoInfo.owner.login} / ${repoInfo.name}"
            , style = MaterialTheme.typography.titleMedium
        )
        Text(if (repoInfo.private) "private" else "public"
            , style = MaterialTheme.typography.labelMedium
            , textAlign = TextAlign.Right
        )
    }

    Spacer(Modifier.height(8.dp))

    Column(Modifier.padding(horizontal = 16.dp)) {
        Text("owner:"
            , style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(4.dp))
        Text(repoInfo.owner.login)
    }

    Spacer(Modifier.height(8.dp))

    if (!repoInfo.description.isNullOrBlank()) {
        Column(Modifier.padding(horizontal = 16.dp)) {
            Text("description:"
                , style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(4.dp))
            Text(repoInfo.description)
        }
    }
}