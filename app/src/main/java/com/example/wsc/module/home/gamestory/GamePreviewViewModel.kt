package com.example.wsc.module.home.gamestory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wsc.module.WSCDestinationsArgs
import com.example.wsc.data.Result
import com.example.wsc.data.Result.Success
import com.example.wsc.data.game.Game
import com.example.wsc.data.game.source.GamesRepository
import com.example.wsc.data.Async
import com.example.wsc.support.util.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Details screen.
 */
@HiltViewModel
class GamePreviewViewModel @Inject constructor(
    private val gamesRepository: GamesRepository, savedStateHandle: SavedStateHandle
) : ViewModel() {

    val gameId: String = savedStateHandle[WSCDestinationsArgs.GAME_ID_ARG]!!

    private val _isLoading = MutableStateFlow(false)
    private val _taskAsync =
        gamesRepository.getGame(gameId)
            .map { handleResult(it) }
            .onStart { emit(Async.Loading) }

    val uiState: StateFlow<GamePreviewUiState> = combine(
        _isLoading, _taskAsync
    ) { isLoading, taskAsync ->
        when (taskAsync) {
            Async.Loading -> {
                GamePreviewUiState(isLoading = true)
            }
            is Async.Done -> {
                GamePreviewUiState(
                    game = taskAsync.data,
                    isLoading = isLoading,
                )
            }
        }
    }.stateIn(
            scope = viewModelScope,
            started = WhileUiSubscribed,
            initialValue = GamePreviewUiState(isLoading = true)
        )

    fun refresh() {
        _isLoading.value = true
        viewModelScope.launch {
            _isLoading.value = false
        }
    }

    private fun handleResult(gamesResult: Result<Game>): Async<Game?> =
        if (gamesResult is Success) {
            Async.Done(gamesResult.data)
        } else {
            Async.Done(null)
        }
}
