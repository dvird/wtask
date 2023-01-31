package com.example.wsc.module

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.wsc.module.WSCDestinationsArgs.GAME_ID_ARG
import com.example.wsc.module.WSCScreens.GAMES_SCREEN
import com.example.wsc.module.WSCScreens.GAME_DETAIL_SCREEN

private object WSCScreens {
    const val GAMES_SCREEN = "games"
    const val GAME_DETAIL_SCREEN = "game"
}

object WSCDestinationsArgs {
    const val GAME_ID_ARG = "gameId"
}

object WSCDestinations {
    const val GAMES_ROUTE = "$GAMES_SCREEN"
    const val GAME_DETAIL_ROUTE = "$GAME_DETAIL_SCREEN/{$GAME_ID_ARG}"
}

class WSCNavigationActions(private val navController: NavHostController) {

    fun navigateToGames(userMessage: Int = 0) {
        val navigatesFromDrawer = userMessage == 0
        navController.navigate(
            GAMES_SCREEN
        ) {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = !navigatesFromDrawer
                saveState = navigatesFromDrawer
            }
            launchSingleTop = true
            restoreState = navigatesFromDrawer
        }
    }

    fun navigateToGame(id: String) {
        navController.navigate(
            "$GAME_DETAIL_SCREEN/$id".let {
                "$it?$GAME_ID_ARG=$id"
            }
        )
    }
}
