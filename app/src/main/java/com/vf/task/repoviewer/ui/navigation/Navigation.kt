package com.vf.task.repoviewer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vf.task.repoviewer.ui.screens.DetailsScreen
import com.vf.task.repoviewer.ui.screens.IssuesScreen
import com.vf.task.repoviewer.ui.screens.MainScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(route = Screen.DetailsScreen.route) {
            DetailsScreen(navController = navController)
        }
        composable(
            route = Screen.IssuesScreen.route + "/{owner}/{name}",
            arguments = listOf(navArgument("owner") {
                type = NavType.StringType
            },
                navArgument("name") {
                    type = NavType.StringType
                })
        ) {
            it.arguments?.getString("owner")
                ?.let { it1 -> it.arguments!!.getString("name")
                    ?.let { it2 -> IssuesScreen(owner = it1, name = it2) } }
        }
    }
}