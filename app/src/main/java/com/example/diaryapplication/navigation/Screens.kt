package com.example.diaryapplication.navigation

import com.example.diaryapplication.util.Constants.WRITE_SCREEN_ARGUMENT_KEY

sealed class Screens(val route : String){
    object Authentication:Screens(route = "authentication_screen")
    object Home:Screens(route = "home_screen")
    object Write:Screens(route = "write_screen?$WRITE_SCREEN_ARGUMENT_KEY={$WRITE_SCREEN_ARGUMENT_KEY}"){
        fun passWrite(diaryId : String) =
            "write_screen?$WRITE_SCREEN_ARGUMENT_KEY=$diaryId"
    }
}
