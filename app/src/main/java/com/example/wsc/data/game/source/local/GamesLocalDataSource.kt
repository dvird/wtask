package com.example.wsc.data.game.source.local

import com.example.wsc.data.Result
import com.example.wsc.data.Result.Error
import com.example.wsc.data.Result.Success
import com.example.wsc.data.game.Game
import com.example.wsc.data.game.source.GamesDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext

/**
 * Concrete implementation of a data source as a db.
 */
class GamesLocalDataSource internal constructor(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : GamesDataSource {

    var data = mutableListOf<Game>()

    override fun getGames(): Flow<Result<List<Game>>> {
        return flowOf(Success(data))
    }

    override fun getGame(id: String): Flow<Result<Game>> {
        try {
            return flowOf(Success(data.first { it.id == id }))
        } catch (e: Exception) {
            return flowOf(Error(e))
        }
    }

    override suspend fun saveGame(game: Game): Unit = withContext(ioDispatcher) {
        data.add(game)
    }

    override suspend fun saveGames(game: List<Game>): Unit = withContext(ioDispatcher) {
        data = game.toMutableList()
    }

    override suspend fun deleteGame(id: String) = withContext<Unit>(ioDispatcher) {
        data.removeAll { t -> t.id == id }
    }
}
