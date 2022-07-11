package com.vandoc.iptv.di

import com.vandoc.iptv.data.IPTVRepository
import com.vandoc.iptv.data.IPTVRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @author Ichvandi
 * Created on 19/06/2022 at 10:48.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesIPTVRepository(repository: IPTVRepositoryImpl): IPTVRepository

}