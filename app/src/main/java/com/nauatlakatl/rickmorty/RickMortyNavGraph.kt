package com.nauatlakatl.rickmorty

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nauatlakatl.rickmorty.features.home.HomeScreen

@Composable
fun RickMortyNavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    startDestination: String = RickMortyDestinations.HOME_ROUTE,
    navActions: RickMortyNavigationActions = remember(navHostController) {
        RickMortyNavigationActions(navHostController)
    }
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(RickMortyDestinations.HOME_ROUTE) {
            HomeScreen()
        }
    }
}