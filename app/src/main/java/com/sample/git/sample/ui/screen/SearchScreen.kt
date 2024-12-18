package com.sample.git.sample.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.sample.git.sample.navigation.navToLogin
import com.sample.git.sample.navigation.navToOwnerRepoDetail
import com.sample.git.sample.ui.view.LogoutView
import com.sample.git.sample.ui.view.NoDataFoundView
import com.sample.git.sample.ui.view.UnLoginView
import com.sample.git.sample.ui.view.repo.RepoListItem
import com.sample.git.sample.ui.view.repo.RepoListItemParam
import com.sample.git.sample.viewmodel.LoginStatus
import com.sample.git.sample.viewmodel.LoginVM
import com.sample.git.sample.viewmodel.SearchScreenVM
import com.sample.git.sample.viewmodel.TopAppBarVM


@Composable
fun SearchScreen(loginViewModel: LoginVM, topBarViewModel: TopAppBarVM, navController: NavHostController) {
    val loginStatus by loginViewModel.loginStatus.collectAsStateWithLifecycle()
    val oAuthState by loginViewModel.oAuthState.collectAsStateWithLifecycle()
    val viewModel: SearchScreenVM = viewModel()
    val repos by viewModel.repos.observeAsState()

    val context = LocalContext.current
    topBarViewModel.setTitle("Search")


    if (loginStatus == LoginStatus.LOGOUT) {
        UnLoginView {
            navToLogin(navController)
        }
    } else {
        LazyColumn {
            item {
                SearchContent(onSearchPressed = { keyword, language, order ->
                    val query = getQueryString(keyword, language)
                    viewModel.searchRepos(token = oAuthState.accessToken,
                        query = query,
                        order = order,
                        onSuccess = {},
                        onError = {})
                })
            }

            repos?.let {
                if (it.isEmpty()) {
                    item {NoDataFoundView()}
                } else {
                    items(it.size) { index ->
                        it[index]?.let { repo ->
                            RepoListItem(RepoListItemParam(
                                index = index,
                                size = it.size,
                                owner = repo.owner.login,
                                name = repo.name,
                                description = repo.description,
                                privacy = repo.private,
                                onClick = {
                                    navToOwnerRepoDetail(navController,
                                        repo.owner.login,
                                        repo.name
                                    )
                                }
                            ))

                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }

            item {
                Spacer(Modifier.height(24.dp))
                LogoutView(loginViewModel, context, navController)
            }
        }
    }
}

private enum class Orders(val value: String) {
    ASC("asc"),
    DESC("desc"),
}

private enum class Languages(val value: String) {
    PYTHON("python"),
    JAVA("java"),
    GO("golang"),
    RUST("rust"),
    JS("js")
}

@Composable
private fun SearchContent(onSearchPressed: (keyword: String, language: String, order: String) -> Unit) {
    var keywordValue by remember { mutableStateOf("LLM") }
    var languageValue by remember { mutableStateOf(Languages.PYTHON) }
    var orderValue by remember { mutableStateOf(Orders.ASC) }

    var languageDropDownExpanded by remember { mutableStateOf(false) }
    var orderDropDownExpanded by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {

        Text("Please Input your keyword here:")
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            value = keywordValue,
            onValueChange = { keywordValue = it },
            label = { Text("Keyword") }
        )
        Spacer(Modifier.height(16.dp))


        Text("Please Select programming language:")
        Spacer(Modifier.height(8.dp))
        Card(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp).clickable {
            languageDropDownExpanded = true
        }) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(languageValue.value, Modifier.padding(16.dp))
                Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "Language")
            }
        }

        DropdownMenu(
            expanded = languageDropDownExpanded,
            onDismissRequest = {
                languageDropDownExpanded = false
            },
        ) {
            Languages.entries.forEach { language ->
                DropdownMenuItem(
                    text = {
                        Text(text = language.value)
                    },
                    onClick = {
                        languageValue = language
                        languageDropDownExpanded = false
                    }
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Text("Please Select sort order:")
        Row(
            Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Sorted by ")
            Icon(imageVector = Icons.Filled.Star, contentDescription = "Rating")

            Card(
                modifier = Modifier.defaultMinSize(48.dp)
                    .padding(vertical = 8.dp, horizontal = 16.dp).clickable {
                        orderDropDownExpanded = true
                    }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(orderValue.value, Modifier.padding(16.dp))
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "Rating"
                    )
                }
            }
            DropdownMenu(
                expanded = orderDropDownExpanded,
                onDismissRequest = {
                    orderDropDownExpanded = false
                },
            ) {
                Orders.entries.forEach { order ->
                    DropdownMenuItem(
                        text = {
                            Text(text = order.value)
                        },
                        onClick = {
                            orderDropDownExpanded = false
                            orderValue = order
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Button(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            enabled = keywordValue.isNotBlank(),
            onClick = {
                onSearchPressed(keywordValue, languageValue.value, orderValue.value)
            }
        ) {
            Text("Search")
        }
    }
}

private fun getQueryString(keyword: String, language: String): String {
    val split = if (keyword.isNotBlank() && language.isNotBlank()) "+" else ""
    val lanStr = if (language.isNotBlank()) "language:$language" else ""
    return "$keyword${split}${lanStr}"
}