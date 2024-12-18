package com.sample.git.sample.ui.view.repo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

data class RepoListItemParam(
    val index: Int,
    val size: Int,
    val owner: String,
    val name: String,
    val description: String?,
    val privacy: Boolean,
    val onClick: (user: String) -> Unit
)

@Composable
fun RepoListItem(param: RepoListItemParam) {
    val dspStr = "Repo list ${param.index} of ${param.size}"

    Card(modifier = Modifier.defaultMinSize(minHeight = 48.dp)
        .fillMaxWidth()
        .padding(horizontal = 8.dp)
        .clickable {
            param.onClick(param.name)
        }.semantics(true) {
            role = Role.Button
            contentDescription = dspStr
        }
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(8.dp)
            , Arrangement.SpaceBetween
            , Alignment.CenterVertically
        ) {
            Column {
                Text(param.name
                , style = MaterialTheme.typography.titleMedium)
                if (param.description != null) {
                    Text(param.description
                        , style = MaterialTheme.typography.labelMedium
                    )
                }
            }

            Text(text = if (param.privacy) "Private" else "Public"
                , style = MaterialTheme.typography.labelMedium
                , textAlign = TextAlign.Right
            )
        }
    }
}