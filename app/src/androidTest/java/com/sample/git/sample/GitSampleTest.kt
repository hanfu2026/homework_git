package com.sample.git.sample

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.sample.git.sample.ui.screen.FeedScreen
import org.junit.Rule
import org.junit.Test

class GitSampleTest {
    @get: Rule
    val rule = createAndroidComposeRule<MainActivity>()

    @Test
    fun show_landing_page() {
        rule.setContent {  }

//        rule.onNodeWithTag()
    }
}