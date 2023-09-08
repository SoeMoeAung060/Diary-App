package com.example.diaryapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.diaryapp.presentation.screens.auth.AuthenticationScreen
import com.example.diaryapp.util.Constants.WRITE_SCREEN_ARGUMENT_KEY
import com.stevdzasan.onetap.OneTapSignInState
import com.stevdzasan.onetap.rememberOneTapSignInState

@Composable
fun SetupNavGraph(
    startDestination: String,
    navController: NavHostController
) {
    NavHost(
        startDestination = startDestination,
        navController = navController
        ){
        authenticationRoute()
        homeRoute()
        writeRoute()
    }

}

fun NavGraphBuilder.authenticationRoute(){
    composable(Screens.Authentication.route){

        val oneTapState = rememberOneTapSignInState()
        AuthenticationScreen(
            loadingState = oneTapState.opened,
            oneTapState = oneTapState,
            onButtonClicked = {
                oneTapState.open()
        })
    }
}

fun NavGraphBuilder.homeRoute(){
    composable(Screens.Home.route){

    }
}

fun NavGraphBuilder.writeRoute(){
    composable(Screens.Write.route,
        arguments = listOf(navArgument(WRITE_SCREEN_ARGUMENT_KEY){
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ){

    }
}