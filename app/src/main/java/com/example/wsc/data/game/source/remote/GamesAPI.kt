package com.example.wsc.data.game.source.remote

import com.example.wsc.data.Result
import com.example.wsc.data.game.Game
import kotlinx.coroutines.flow.Flow

interface GamesAPI {
    fun getGames(): Flow<Result<List<Game>>>

    fun getGame(id: String): Flow<Result<Game>>

    suspend fun saveGame(game: Game)

    suspend fun deleteGame(id: String)
}
