package com.sample.git.sample.ui.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.sample.git.sample.network.model.repos.issue.RepoIssueModel

@Composable
fun IssueListView(issues: List<RepoIssueModel?>, onIssueItemClicked: () -> Unit) {
    if (issues.isNotEmpty()) {
        LazyColumn {
            items(issues.size) { index ->
                val issue = issues[index]
                if (issue != null) {
                    IssueListItemView(IssueListItemParam(
                        index = index,
                        size = issues.size,
                        title = issue.title,
                        body = issue.body,
                        onClick = {
                            onIssueItemClicked()
                        }
                    ))
                }
            }
        }
    }
}