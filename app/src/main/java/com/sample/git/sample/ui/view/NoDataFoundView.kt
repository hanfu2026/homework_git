package com.sample.git.sample.ui.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun NoDataFoundView(text: String = "No Data Found") {
    Text(text, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
}
