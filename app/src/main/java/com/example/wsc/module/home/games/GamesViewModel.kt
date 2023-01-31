package com.example.wsc.module.home.games

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wsc.data.Async
import com.example.wsc.data.Result
import com.example.wsc.data.game.Game
import com.example.wsc.data.game.source.GamesRepository
import com.example.wsc.support.util.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the task list screen.
 */
@HiltViewModel
class GamesViewModel @Inject constructor(
    private val gamesRepository: GamesRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    private val _gamesAsync =
        gamesRepository.getGames()
            .map { handleResult(it) }
            .onStart { emit(Async.Loading) }

    val uiState: StateFlow<GamesUiState> = combine(
        _isLoading, _gamesAsync
    ) { isLoading, gamesAsync ->
        when (gamesAsync) {
            is Async.Loading -> {
                GamesUiState(isLoading = true)
            }
            is Async.Done -> {
                GamesUiState(
                    items = gamesAsync.data ?: listOf(),
                    isLoading = isLoading,
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileUiSubscribed,
        initialValue = GamesUiState(isLoading = true)
    )

    fun refresh() {
        _isLoading.value = true
        viewModelScope.launch {
            gamesRepository.getGames()
            _isLoading.value = false
        }
    }

    private fun handleResult(gamesResult: Result<List<Game>>): Async<List<Game>?> =
        if (gamesResult is Result.Success) {
            Async.Done(gamesResult.data)
        } else {
            Async.Done(null)
        }
}