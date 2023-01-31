package com.example.wsc.module.home.gamestory

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.wsc.data.game.Game
import com.example.wsc.module.home.gamestory.view.VideoView
import com.example.wsc.support.util.LoadingContent
import com.ui.simplestories.Stories

@Composable
fun GamePreviewScreen(
    modifier: Modifier = Modifier,
    viewModel: GamePreviewViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController
) {
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = modifier.fillMaxSize(),
        backgroundColor = Color.Black,
    ) { paddingValues ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        GamePreviewContent(
            loading = uiState.isLoading,
            empty = uiState.game == null && !uiState.isLoading,
            game = uiState.game,
            onRefresh = viewModel::refresh,
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun GamePreviewContent(
    loading: Boolean,
    empty: Boolean,
    game: Game?,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

    val stories = game?.wscGame?.primeStory?.pages ?: listOf()
    LoadingContent(
        loading = loading,
        empty = empty,
        onRefresh = onRefresh,
        emptyContent = {
        }) {
        Stories(
            numberOfPages = stories.size,
            slideDurationInSeconds = 5,
            onComplete = {
                navController.popBackStack()
            }) {
            VideoView(videoUri = stories[it].videoUrl, onEnded = {})
        }
    }
}