package com.nauatlakatl.rickmorty

import androidx.navigation.NavController
import com.nauatlakatl.rickmorty.RickMortyDestinationArgs.CHARACTER_ID_ARG
import com.nauatlakatl.rickmorty.RickMortyScreens.CHARACTER_DETAILS_SCREEN
import com.nauatlakatl.rickmorty.RickMortyScreens.HOME_SCREEN

private object RickMortyScreens {
    const val HOME_SCREEN = "home"
    const val CHARACTER_DETAILS_SCREEN = "character_details"
}

object RickMortyDestinationArgs {
    const val CHARACTER_ID_ARG = "characterId"
}

object RickMortyDestinations {
    const val HOME_ROUTE = HOME_SCREEN
    const val CHARACTER_DETAILS_ROUTE = "$CHARACTER_DETAILS_SCREEN/{$CHARACTER_ID_ARG}"
}

class RickMortyNavigationActions(private val navController: NavController) {

    fun navigateUp() {
        navController.navigateUp()
    }

    fun navigateToHome() {
        navController.navigate(HOME_SCREEN)
    }

    fun navigateToCharacterDetails(id: Int) {
        navController.navigate("$CHARACTER_DETAILS_SCREEN/$id")
    }
}