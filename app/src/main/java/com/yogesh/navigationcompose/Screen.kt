package com.yogesh.navigationcompose

import android.util.Log

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object ProfileScreen : Screen("profile_screen")
    object SignInScreen : Screen("signin_screen")

    fun withArgs(vararg args: String?): String {
        val string = buildString {
            append(route)
            args.forEach { arg ->
                arg?.let {
                    append("?name=$it")
                }
            }
        }
        Log.d("Route withArgs", "= $string")
        return string
    }
//    fun withPopBackTo(id: Int?): String {
//        val string = buildString {
//            append(route)
//            id?.let { append("?id=$it") }
//        }
//        Log.d("Route withPopBackTo", "= $string")
//        return string
//    }
}
