package com.sample.git.sample


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.sample.git.sample.navigation.ScreenRouteDebug
import com.sample.git.sample.navigation.ScreenRouteFeed
import com.sample.git.sample.navigation.ScreenRouteLogin
import com.sample.git.sample.navigation.ScreenRouteOwnerRepoDetail
import com.sample.git.sample.navigation.ScreenRouteProfile
import com.sample.git.sample.navigation.ScreenRouteRepoDetail
import com.sample.git.sample.navigation.ScreenRouteSearch
import com.sample.git.sample.navigation.navToDebug
import com.sample.git.sample.navigation.navToProfile
import com.sample.git.sample.navigation.navToSearch
import com.sample.git.sample.network.constant.Endpoints
import com.sample.git.sample.ui.screen.DebugScreen
import com.sample.git.sample.ui.screen.FeedScreen
import com.sample.git.sample.ui.screen.LoginScreen
import com.sample.git.sample.ui.screen.OwnerRepoDetailScreen
import com.sample.git.sample.ui.screen.ProfileScreen
import com.sample.git.sample.ui.screen.RepoDetailScreen
import com.sample.git.sample.ui.screen.SearchScreen
import com.sample.git.sample.ui.theme.GitSampleTheme
import com.sample.git.sample.utils.PreferenceHelper
import com.sample.git.sample.viewmodel.LoginStatus
import com.sample.git.sample.viewmodel.LoginVM
import com.sample.git.sample.viewmodel.TopAppBarVM

class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginVM by viewModels()
    private val topBarViewModel: TopAppBarVM by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            GitSampleTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val loginStatus by loginViewModel.loginStatus.collectAsStateWithLifecycle()

                Scaffold(modifier = Modifier.fillMaxSize()
                    , topBar = {
                        TopAppBar(title = { TitleView(topBarViewModel) }
                            , navigationIcon = {
                                val destination = navBackStackEntry?.destination?.route
                                if (destination != ScreenRouteFeed.javaClass.name) {
                                    IconButton(
                                        modifier = Modifier.testTag("navigateUpButton"),
                                        onClick = { navController.navigateUp() }) {
                                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.dsp_btn_back))
                                    }
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.Home,
                                        contentDescription = stringResource(R.string.dsp_btn_profile)
                                    )

                                }
                            }, actions = {
                                IconButton(
                                    modifier = Modifier.testTag("searchButton"),
                                    onClick = {
                                    navToSearch(navController)
                                }) {
                                    Icon(imageVector = Icons.Default.Search, contentDescription = stringResource(R.string.dsp_btn_search))
                                }
                                IconButton(modifier = Modifier.testTag("profileButton"),
                                    onClick = {
                                    navToProfile(navController)
                                }) {
                                    if (loginStatus == LoginStatus.LOGIN) {
                                        Icon(
                                            imageVector = Icons.Default.AccountCircle,
                                            contentDescription = stringResource(R.string.dsp_btn_profile)
                                        )
                                    } else {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_logout),
                                            contentDescription = "log out"
                                        )
                                    }
                                }
                                IconButton(onClick = {
                                    navToDebug(navController)
                                }) {
                                    Icon(imageVector = Icons.Default.Build, contentDescription = stringResource(R.string.dsp_btn_profile))
                                }
                            })

                    }) { innerPadding ->
                    val modifier = Modifier.padding(innerPadding)
                    Column(modifier = modifier) {
                        NavigationComponent(navController)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("GS", "onResume")
        when (intent?.action) {
            Intent.ACTION_VIEW -> {
                onHandleAuthIntent(intent = intent)
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d("GS", "onNewIntent")
        when (intent?.action) {
            Intent.ACTION_VIEW -> {
                onHandleAuthIntent(intent = intent)
            }
        }
    }

    private fun onHandleAuthIntent(intent: Intent?) {
        if (intent != null && intent.data != null) {
            Log.d("GS", intent.data.toString())
            val uri = intent.data
            if (uri.toString().startsWith(Endpoints.REDIRECT_URL)) {
                val tokenCode = uri!!.getQueryParameter("code")
                Log.d("GS", tokenCode ?: "null")
                if (tokenCode!!.isNotBlank()) {
                    // save code
                    loginViewModel.setCode(tokenCode)
                    PreferenceHelper.saveCode(this, tokenCode)
                    if (loginViewModel.loginStatus.value == LoginStatus.LOGIN) {
                        Log.d("GS", "continue login")

                    }
                } else {
                    Toast.makeText(this, "Something went wrong !", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()

        loginViewModel.clearVMToken()
        PreferenceHelper.clearSavedToken(this)
    }

    @Composable
    fun NavigationComponent(navController: NavHostController) {
        NavHost(navController, startDestination = ScreenRouteFeed) {
            composable<ScreenRouteFeed> {
                FeedScreen(topBarViewModel, navController)
            }
            composable<ScreenRouteLogin> {
                LoginScreen(loginViewModel, topBarViewModel, navController)
            }
            composable<ScreenRouteSearch> {
                SearchScreen(loginViewModel, topBarViewModel, navController)
            }
            composable<ScreenRouteProfile> {
                ProfileScreen(loginViewModel, topBarViewModel, navController)
            }
            composable<ScreenRouteRepoDetail> { backStackEntry ->
                val route = backStackEntry.toRoute<ScreenRouteRepoDetail>()
                RepoDetailScreen(route.owner, route.repo, topBarViewModel,loginViewModel, navController)
            }
            composable<ScreenRouteOwnerRepoDetail> { backStackEntry ->
                val route = backStackEntry.toRoute<ScreenRouteOwnerRepoDetail>()
                OwnerRepoDetailScreen(route.owner, route.repo, topBarViewModel,loginViewModel, navController)
            }
            composable<ScreenRouteDebug> {
                DebugScreen(loginViewModel, topBarViewModel, navController)
            }
        }
    }
}

@Composable
fun TitleView(topAppBarVM: TopAppBarVM) {
    val title by topAppBarVM.title.collectAsState()
    Text(title)
}


