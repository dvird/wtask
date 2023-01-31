

package com.example.wsc.support.di

import android.content.Context
import com.example.wsc.data.game.source.DefaultGamesRepository
import com.example.wsc.data.game.source.GamesDataSource
import com.example.wsc.data.game.source.GamesRepository
import com.example.wsc.data.game.source.local.GamesLocalDataSource
//import com.example.todoapp.data.task.source.local.ToDoDatabase
import com.example.wsc.data.game.source.remote.GamesRemoteDataSource
import com.example.wsc.data.game.source.remote.mock.MockGamesAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RemoteGamesDataSource

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalGamesDataSource

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideGamesRepository(
        @RemoteGamesDataSource remoteDataSource: GamesDataSource,
        @LocalGamesDataSource localDataSource: GamesDataSource,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): GamesRepository {
        return DefaultGamesRepository(remoteDataSource, localDataSource, ioDispatcher)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @RemoteGamesDataSource
    @Provides
    fun provideGamesRemoteDataSource(@ApplicationContext appContext: Context): GamesDataSource = GamesRemoteDataSource(
        MockGamesAPI(appContext)
    )

    @Singleton
    @LocalGamesDataSource
    @Provides
    fun provideGamesLocalDataSource(
//        database: ToDoDatabase,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): GamesDataSource {
        return GamesLocalDataSource(
//            database.taskDao(),
            ioDispatcher)
    }
}

//@Module
//@InstallIn(SingletonComponent::class)
//object DatabaseModule {
//
//    @Singleton
//    @Provides
//    fun provideDataBase(@ApplicationContext context: Context): ToDoDatabase {
//        return Room.databaseBuilder(
//            context.applicationContext,
//            ToDoDatabase::class.java,
//            "Games.db"
//        ).build()
//    }
//}
