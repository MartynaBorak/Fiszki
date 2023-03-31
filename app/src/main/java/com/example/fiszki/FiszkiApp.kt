package com.example.fiszki

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fiszki.ui.navigation.FiszkiNavHost

@Composable
fun FiszkiApp(navController: NavHostController = rememberNavController()){
    FiszkiNavHost(navController = navController)
}