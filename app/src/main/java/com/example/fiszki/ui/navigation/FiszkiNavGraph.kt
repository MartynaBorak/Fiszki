package com.example.fiszki.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.fiszki.ui.home.*
import com.example.fiszki.ui.zestaw.*

@Composable
fun FiszkiNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    var zestawId: Int by remember { mutableStateOf(0) }
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ){
        //home
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToZestawEntry = { navController.navigate(ZestawEntryDestination.route) },
                navigateToZestawScreen = {
                    navController.navigate("${ZestawScreenDestination.route}/${it}")
                },
                navigateToSettings = { navController.navigate(SettingsDestination.route) }
            )
        }
        composable(route = SettingsDestination.route) {
            SettingsScreen(navigateUp = { navController.navigateUp() })
        }
        composable(route = ZestawEntryDestination.route){
            ZestawEntryScreen(navigateBack = { navController.popBackStack() })
        }

        //zestaw
        composable(
            route = ZestawScreenDestination.routeWithArgs,
            arguments = listOf(navArgument(ZestawScreenDestination.zestawIdArg) {
                type = NavType.IntType
            })
        ) {
            ZestawScreen(
                navigateToFiszkaEntry = { id ->
                    navController.navigate(FiszkaEntryDestination.route)
                        .also { zestawId = id }
                },
                navigateToFiszkaDetails = {
                    navController.navigate("${FiszkaDetailsDestination.route}/${it}")
                },
                navigateToZestawEdit = {
                    navController.navigate("${ZestawEditDestination.route}/${it}")
                },
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = ZestawEditDestination.routeWithArgs,
            arguments = listOf(navArgument(ZestawEditDestination.zestawIdArg) {
                type = NavType.IntType
            })
        ) {
            ZestawEditScreen(
                navigateBack = { navController.navigateUp() },
                navigateHome = {
                    navController.popBackStack(route = HomeDestination.route, inclusive = false)
                }
            )
        }

        composable( route = FiszkaEntryDestination.route ) {
            FiszkaEntryScreen(
                navigateBack = { navController.navigateUp() },
                zestawId = zestawId
            )
        }

        composable(
            route = FiszkaDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(FiszkaDetailsDestination.fiszkaIdArg) {
                type = NavType.IntType
            })
        ) {
            FiszkaDetailsScreen(
                navigateToFiszkaEdit = {
                    navController.navigate("${FiszkaEditDestination.route}/${it}")
                },
                navigateBack = { navController.navigateUp() }
            )
        }

        composable(
            route = FiszkaEditDestination.routeWithArgs,
            arguments = listOf(navArgument(FiszkaEditDestination.fiszkaIdArg) {
                type = NavType.IntType
            })
        ) {
            FiszkaEditScreen(navigateBack = { navController.navigateUp() })
        }


        //learn
        //memory
    }
}