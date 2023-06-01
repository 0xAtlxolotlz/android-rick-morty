package com.nauatlakatl.rickmorty

import androidx.navigation.NavController
import com.nauatlakatl.rickmorty.RickMortyScreens.HOME_SCREEN

private object RickMortyScreens {
    const val HOME_SCREEN = "home"
}

object RickMortyDestinations {
    const val HOME_ROUTE = HOME_SCREEN
}

class RickMortyNavigationActions(private val navController: NavController) {

    fun navigateUp() {
        navController.navigateUp()
    }

    fun navigateToHome() {
        navController.navigate(HOME_SCREEN)
    }
}