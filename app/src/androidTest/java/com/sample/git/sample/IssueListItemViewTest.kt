package com.sample.git.sample

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.sample.git.sample.ui.view.IssueListItemParam
import com.sample.git.sample.ui.view.IssueListItemView
import org.junit.Rule
import org.junit.Test

class IssueListItemViewTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun showIssueListItemView() {
        rule.setContent { IssueListItemView(IssueListItemParam(
            size = 5,
            index = 1,
            title = "issue title 1",
            body = "this is body",
            onClick = {}
        )) }

        rule.onNodeWithText("issue title 1").performClick()
    }
}