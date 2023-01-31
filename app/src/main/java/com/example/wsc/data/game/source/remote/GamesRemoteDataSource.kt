

package com.example.wsc.data.game.source.remote

import com.example.wsc.data.Result
import com.example.wsc.data.Result.Error
import com.example.wsc.data.Result.Success
import com.example.wsc.data.game.Game
import com.example.wsc.data.game.source.GamesDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Implementation of the data source that adds a latency simulating network.
 */
class GamesRemoteDataSource(private val api: GamesAPI) : GamesDataSource {

    override fun getGames(): Flow<Result<List<Game>>> {
        return api.getGames()
    }

    override fun getGame(id: String): Flow<Result<Game>> {
        return flow { Error(Exception("Game not found")) }
    }

    override suspend fun saveGame(game: Game) {}

    override suspend fun deleteGame(id: String) {}
    override suspend fun saveGames(game: List<Game>) {
        TODO("Not yet implemented")
    }
}
