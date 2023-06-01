package com.nauatlakatl.rickmorty

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nauatlakatl.rickmorty.features.details.CharacterDetailsScreen
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
            HomeScreen(
                onClickCard = { characterId -> navActions.navigateToCharacterDetails(characterId) }
            )
        }
        composable(
            RickMortyDestinations.CHARACTER_DETAILS_ROUTE,
            arguments = listOf(
                navArgument(RickMortyDestinationArgs.CHARACTER_ID_ARG) { type = NavType.IntType }
            )
        ) { navBackStack ->
            val arguments = navBackStack.arguments
            val characterId = arguments?.getInt(RickMortyDestinationArgs.CHARACTER_ID_ARG)!!
            CharacterDetailsScreen(characterId = characterId)
        }
    }
}