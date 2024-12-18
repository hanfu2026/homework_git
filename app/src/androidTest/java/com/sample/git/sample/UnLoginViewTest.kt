package com.sample.git.sample

import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.sample.git.sample.ui.view.UnLoginView
import org.junit.Rule
import org.junit.Test

class UnLoginViewTest {

    @get: Rule
    val rule = createComposeRule()

    @Test
    fun clickButtonTest() {
        rule.setContent { UnLoginView {  } }

        rule.onNode(
            hasText("Log in")
            and
            hasClickAction()
        ).performClick()
    }
}