package com.sample.git.sample

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.sample.git.sample.ui.view.NoDataFoundView
import org.junit.Rule
import org.junit.Test

class NoDataFoundTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun showDefault() {
        rule.setContent { NoDataFoundView("No Data") }
        rule.onNodeWithText("No Data").assertExists()
    }
}