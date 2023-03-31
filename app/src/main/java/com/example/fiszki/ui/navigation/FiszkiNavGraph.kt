package com.example.fiszki.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fiszki.ui.home.*

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
                navigateToZestawScreen = {},
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
        //ZestawScreen, ZestawEditScreen, FiszkaEntryScreen, FiszkaDetailsScreen, FiszkaEditScreen


        //learn
        //memory
    }
}