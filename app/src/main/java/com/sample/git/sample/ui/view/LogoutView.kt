package com.sample.git.sample.ui.view

import android.content.Context
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sample.git.sample.ui.screen.informUser
import com.sample.git.sample.utils.PreferenceHelper
import com.sample.git.sample.viewmodel.LoginVM

@Composable
fun LogoutView(loginViewModel: LoginVM, context: Context, navController: NavHostController) {
    Spacer(Modifier.height(16.dp))
    Button(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        , onClick = {
            onLogout(loginViewModel, context, navController)
        }) {
        Text("Log out")
    }
}

fun onLogout(loginViewModel: LoginVM, context: Context, navController: NavHostController) {

    loginViewModel.deleteAppToken(loginViewModel.oAuthState.value.accessToken,
        onSuccess = {
            loginViewModel.clearVMToken()
            PreferenceHelper.clearSavedToken(context)

            informUser(context, "Log out successfully")
            navController.navigateUp()
        },
        onErrorMsg = { errMsg ->
            informUser(context, errMsg)
        }
    )
}