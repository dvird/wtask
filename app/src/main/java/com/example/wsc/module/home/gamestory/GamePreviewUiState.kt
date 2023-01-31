package com.example.wsc.module.home.gamestory

import com.example.wsc.data.game.Game

/**
 * UiState for the Details screen.
 */
data class GamePreviewUiState(
    val game: Game? = null,
    val isLoading: Boolean = false,
)