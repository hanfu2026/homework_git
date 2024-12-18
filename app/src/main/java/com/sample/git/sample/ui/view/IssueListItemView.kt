package com.sample.git.sample.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

data class IssueListItemParam(
    val index: Int,
    val size: Int,
    val title: String,
    val body: String?,
    val onClick: (index: Int) -> Unit
)

@Composable
fun IssueListItemView(param: IssueListItemParam) {
    val dspStr = "Issue list ${param.index} of ${param.size}"
    Card(modifier = Modifier.defaultMinSize(minHeight = 48.dp)
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .clickable {
            param.onClick(param.index)
        }.semantics(true) {
            role = Role.Button
            contentDescription = dspStr
        }
    ) {
        Column {
            Text(param.title
                , style = MaterialTheme.typography.titleMedium)
            if (param.body != null) {
                Spacer(Modifier.height(8.dp))
                Text(param.body
                    , style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}