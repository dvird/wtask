package com.example.wsc.data.game.source

import com.example.wsc.data.Result
import com.example.wsc.data.game.Game
import kotlinx.coroutines.flow.Flow

/**
 * Interface to the data layer.
 */
interface GamesRepository {

    fun getGames(): Flow<Result<List<Game>>>

    fun getGame(id: String, forceUpdate: Boolean = false): Flow<Result<Game>>

    suspend fun saveGame(game: Game)

    suspend fun deleteGame(predicate: (Game) -> Boolean)
}
