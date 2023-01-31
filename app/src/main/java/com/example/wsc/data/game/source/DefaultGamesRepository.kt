package com.example.wsc.data.game.source

import com.example.wsc.data.Result
import com.example.wsc.data.game.Game
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * Default implementation of [GamesRepository]. Single entry point for managing games' data.
 */
class DefaultGamesRepository(
    private val remoteDataSource: GamesDataSource,
    private val localDataSource: GamesDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : GamesRepository {


    override fun getGames(): Flow<Result<List<Game>>> {
        return remoteDataSource
            .getGames()
            .onEach {
                if(it is Result.Success) {
                    localDataSource.saveGames(it.data)
                }
            }
    }

    override fun getGame(id: String, forceUpdate: Boolean): Flow<Result<Game>> {
//        if (forceUpdate) {
//          val remoteGame = remoteDataSource.getGame(id)
//        }
        return localDataSource.getGame(id)
    }

    override suspend fun saveGame(game: Game) {
        coroutineScope {
            launch { remoteDataSource.saveGame(game) }
            launch { localDataSource.saveGame(game) }
        }
    }

    override suspend fun deleteGame(predicate: (Game) -> Boolean) {
//        coroutineScope {
//            launch { remoteDataSource.deleteGame(gameId) }
//            launch { localDataSource.deleteGame(gameId) }
//        }
    }
}
