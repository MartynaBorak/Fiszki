package com.example.fiszki.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.fiszki.ui.home.*
import com.example.fiszki.ui.zestaw.ZestawEditDestination
import com.example.fiszki.ui.zestaw.ZestawEditScreen
import com.example.fiszki.ui.zestaw.ZestawScreen
import com.example.fiszki.ui.zestaw.ZestawScreenDestination

@Composable
fun FiszkiNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
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
                navigateToFiszkaEntry = { /* TODO */ },
                navigateToFiszkaDetails = { /* TODO */ },
                navigateToZestawEdit = {
                    navController.navigate("${ZestawEditDestination.route}/${it}")
                },
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = ZestawEditDestination.routeWithArgs,
            arguments = listOf(navArgument(ZestawScreenDestination.zestawIdArg) {
                type = NavType.IntType
            })
        ) {
            ZestawEditScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
        //ZestawEditScreen, FiszkaEntryScreen, FiszkaDetailsScreen, FiszkaEditScreen


        //learn
        //memory
    }
}