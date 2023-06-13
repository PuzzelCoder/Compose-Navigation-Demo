package com.yogesh.navigationcompose

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(
            route = Screen.ProfileScreen.route + "?name={name}", // optional param "name", if required as mandatory "name" argument then change to  "/{name}
            arguments = listOf(
                navArgument(name = "name") {
                    type = NavType.StringType
                    defaultValue = "John"
                    nullable = true
                }
            )
        ) { entry ->
            ProfileScreen(navController = navController, name = entry.arguments?.getString("name"))
        }
        composable(
            route = Screen.SignInScreen.route + "?name={name}", // optional param "name", if required as mandatory "name" argument then change to  "/{name}
            arguments = listOf(
                navArgument(name = "name") {
                    type = NavType.StringType
                    defaultValue = "John"
                    nullable = true
                },
                navArgument(name = "id") {
                    type = NavType.StringType
                    defaultValue = "-1"
                    nullable = true
                }
            )
        ) { entry ->
            SignInScreen(navController = navController, name = entry.arguments?.getString("name"))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    var text by remember { mutableStateOf("") }
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {
        val quesize = navController.backQueue.size
        Log.d("Route", "MainScreen: queue Size= $quesize")
        Text(text = "Main Screen")
        TextField(
            value = text,
            onValueChange = {
                text = it
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            modifier = Modifier.align(Alignment.End),
            onClick = {
                navController.navigate(Screen.ProfileScreen.withArgs(text))
            }
        ) {
            Text(text = "Profile ->")
        }
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            modifier = Modifier.align(Alignment.End),
            onClick = { navController.navigate(Screen.SignInScreen.withArgs(text)) }
        ) {
            Text(text = "Sign In ->")
        }
    }
}

@Composable
fun ProfileScreen(navController: NavController, name: String?) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val currentRoute = navController.currentBackStackEntry?.destination?.route
        val currentDestinationId = navController.currentDestination?.id
//        val currentRoute = navController.currentBackStackEntry?.destination?.id
        val quesize = navController.backQueue.size
        Log.d("Route", "ProfileScreenId: = $currentDestinationId")
        Log.d("Route", "ProfileScreen: queue Size= $quesize")
        Text(text = "Profile Screen", fontSize = 40.sp, fontWeight = FontWeight.Bold)
        Text(text = "Hello $name")
        Text(text = "currentRoute=  $currentRoute")
        Spacer(modifier = Modifier.height(16.dp))
//        signin_screen?id=-1741170247
        Button(onClick = {
            navController.navigate(
                Screen.SignInScreen.withArgs(
                    currentDestinationId.toString()
                )
            )
        }) {
            Text(text = "SignIn ->")
        }
    }
}

@Composable
fun SignInScreen(navController: NavController, name: String?) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Sign In Screen", fontSize = 40.sp, fontWeight = FontWeight.Bold)
        val quesize = navController.backQueue.size
        Log.d("Route", "SignInScreen: queue Size= $quesize")
        Log.d("Route", "SignInScreen: name= $name")
        Button(onClick = {
            if (name != null) {
                Log.d("Route", "SignInScreen: -1")
                navController.popBackStack(Screen.ProfileScreen.route + "?name={name}", false)
            } else {
                Log.d("Route", "SignInScreen: 1 ")
//                navController.popBackStack(route = Screen.ProfileScreen.route, false)
                navController.navigate(Screen.ProfileScreen.withArgs(name))
            }
//            navController.popBackStack()
//            navController.popBackStack(route = Screen.ProfileScreen.withArgs(name ?: ""), false)
        }) {
            Text(text = "Profile ->")
        }
    }
}
