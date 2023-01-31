package com.example.wsc.module.home.games

import com.example.wsc.data.game.Game

/**
 * UiState for the task list screen.
 */
data class GamesUiState(
    val items: List<Game> = emptyList(),
    val isLoading: Boolean = false,
)