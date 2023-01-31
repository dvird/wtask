package com.example.wsc.module

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.wsc.module.home.games.GamesScreen
import com.example.wsc.module.home.gamestory.GamePreviewScreen

@Composable
fun GamesNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = WSCDestinations.GAMES_ROUTE,
    navActions: WSCNavigationActions = remember(navController) {
        WSCNavigationActions(navController)
    }
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination

    NavHost(
        navController = navController, startDestination = startDestination, modifier = modifier
    ) {

        composable(WSCDestinations.GAMES_ROUTE,
            arguments = listOf(),
            content = {
                GamesScreen(
                  onGameClick = { game -> navActions.navigateToGame(game.id) },
                )
        })
        composable(WSCDestinations.GAME_DETAIL_ROUTE, content = {
            GamePreviewScreen(navController=navController)
        })
    }
}