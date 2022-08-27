package com.vandoc.iptv.di

import android.content.Context
import androidx.room.Room
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSource
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.vandoc.iptv.data.local.DataDao
import com.vandoc.iptv.data.local.IPTVDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

/**
 * @author Ichvandi
 * Created on 19/06/2022 at 15:04.
 */
@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    fun provideRemoteConfig(): FirebaseRemoteConfig {
        val instance = FirebaseRemoteConfig.getInstance()
        val config = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(60 * 10) // 15 Minute
            .build()
        instance.setConfigSettingsAsync(config)
        return instance
    }

    @Provides
    fun provideDataSourceFactory(okHttpClient: OkHttpClient): DataSource.Factory {
        return OkHttpDataSource.Factory(okHttpClient)
    }

    @Provides
    fun provideMediaSourceFactory(dataSourceFactory: DataSource.Factory): MediaSource.Factory {
        return DefaultMediaSourceFactory(dataSourceFactory)
    }

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): IPTVDatabase {
        return Room.databaseBuilder(
            context,
            IPTVDatabase::class.java, "iptv-database"
        ).build()
    }

    @Provides
    @Singleton
    fun providesDao(database: IPTVDatabase): DataDao {
        return database.dataDao()
    }

}