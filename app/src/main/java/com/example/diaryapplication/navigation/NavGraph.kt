package com.example.diaryapplication.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.diaryapplication.data.repository.MongoDB
import com.example.diaryapplication.presentation.screens.auth.AuthenticationScreen
import com.example.diaryapplication.presentation.screens.auth.AuthenticationViewModel
import com.example.diaryapplication.presentation.screens.home.HomeScreen
import com.example.diaryapplication.util.Constants.APP_ID
import com.example.diaryapplication.util.Constants.WRITE_SCREEN_ARGUMENT_KEY
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState
import io.realm.kotlin.mongodb.App
import com.example.diaryapplication.presentation.components.DisplayAlertDialog
import com.example.diaryapplication.presentation.screens.home.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SetupNavGraph(
    startDestination: String,
    navController: NavHostController
) {
    NavHost(
        startDestination = startDestination,
        navController = navController
    ) {
        authenticationRoute(
            navigateToHome = {
                navController.popBackStack()
                navController.navigate(Screens.Home.route)
            }
        )
        homeRoute(
            navigateToWrite = {
                navController.navigate(Screens.Write.route)
            },
            navigateToAuth = {
                navController.popBackStack()
                navController.navigate(Screens.Authentication.route)
            }
        )
        writeRoute()
    }

}

fun NavGraphBuilder.authenticationRoute(
    navigateToHome: () -> Unit
) {
    composable(Screens.Authentication.route) {

        val viewModel: AuthenticationViewModel = viewModel()
        val loadingState by viewModel.loadingState
        val authenticated by viewModel.authenticated
        val oneTapState = rememberOneTapSignInState()
        val messageBarState = rememberMessageBarState()
        AuthenticationScreen(
            authenticated = authenticated,
            loadingState = loadingState,
            oneTapState = oneTapState,
            messageBarState = messageBarState,
            onButtonClicked = {
                oneTapState.open()
                viewModel.setLoading(true)
            },
            onTokenIdReceived = { tokenId ->
                viewModel.signInWithMongoAtlas(
                    tokenId = tokenId,
                    onSuccess = {
                        messageBarState.addSuccess("Successfully Authentication")
                        viewModel.setLoading(false)

                    },
                    onError = {
                        messageBarState.addError(it)
                        viewModel.setLoading(false)
                    }
                )
            },
            onDialogDismissed = { message ->
                messageBarState.addError(Exception(message))
                viewModel.setLoading(false)

            },
            navigateToHome = navigateToHome

        )
    }
}

@SuppressLint("UnrememberedMutableState")
fun NavGraphBuilder.homeRoute(
    navigateToWrite: () -> Unit,
    navigateToAuth: () -> Unit,
) {
    composable(Screens.Home.route) {

        val viewModel : HomeViewModel = viewModel()
        val diaries by viewModel.diaries
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var signOutDialogOpen by remember { mutableStateOf(false) }


        HomeScreen(
            diaries = diaries,
            drawerState = drawerState,
            onSignOutClicked = {
                signOutDialogOpen = true
            },
            onMenuClicked = {
                scope.launch() {
                    drawerState.open()
                }
            },
            navigateToWrite = navigateToWrite,
        )

        LaunchedEffect(key1 = Unit){
            MongoDB.configureTheRealm()
        }



        DisplayAlertDialog(
            title = "Sign Out",
            message = "Are you sure you want to sign out from your Google Account?",
            dialogOpened = signOutDialogOpen,
            onYesClicked = {
                val user = App.create(APP_ID).currentUser
                scope.launch(Dispatchers.IO) {
                    if (user != null) {
                        user.logOut()
                        withContext(Dispatchers.Main) {
                            navigateToAuth()
                        }
                    }

                }
            },
            onDialogClosed = {
                signOutDialogOpen = false
            }
        )
    }
}

fun NavGraphBuilder.writeRoute() {
    composable(
        Screens.Write.route,
        arguments = listOf(navArgument(WRITE_SCREEN_ARGUMENT_KEY) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {

    }
}