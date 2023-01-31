package com.example.wsc.data.game.source.remote.mock

import android.content.Context
import com.example.wsc.data.Result
import com.example.wsc.data.Result.Error
import com.example.wsc.data.game.Game
import com.example.wsc.data.game.source.GamesDataSource
import com.example.wsc.data.game.source.remote.GamesAPI
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import java.io.IOException
import java.io.InputStream


class MockGamesAPI(private val context: Context) : GamesAPI {

    var data: List<Game>? = null

    override fun getGames(): Flow<Result<List<Game>>> {
        if (data == null) {
            data = readJSONFile()
        }
        return flowOf(Result.Success(data!!))
    }

    override fun getGame(id: String): Flow<Result<Game>> {
        throw Exception("not implemented")
    }

    override suspend fun saveGame(game: Game) {

    }

    override suspend fun deleteGame(id: String) {

    }

    private fun readJSONFile(): List<Game>? {
        try {
            val inputStream: InputStream = context.assets.open("mock_api_data.json")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            val j = String(buffer)
            val r: List<Game>? = GsonBuilder()
                .setLenient()
                .create().fromJson(j, object : TypeToken<List<Game>>() {}.type)
            print(r)
            return r
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}
