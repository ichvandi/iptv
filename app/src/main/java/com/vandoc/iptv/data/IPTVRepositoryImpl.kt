package com.vandoc.iptv.data

import com.vandoc.iptv.base.Resource
import com.vandoc.iptv.data.model.local.*
import com.vandoc.iptv.data.model.request.SearchChannelsRequest
import com.vandoc.iptv.data.remote.IPTVDataSource
import com.vandoc.iptv.util.mapResource
import com.vandoc.iptv.util.mapper.toLocalModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @author Ichvandi
 * Created on 19/06/2022 at 10:37.
 */
class IPTVRepositoryImpl @Inject constructor(
    private val dataSource: IPTVDataSource
) : IPTVRepository {

    override suspend fun searchChannels(queries: SearchChannelsRequest): Flow<Resource<ChannelPaging?>> {
        return flow {
            emit(Resource.Loading)
            dataSource.searchChannels(queries)
                .flowOn(Dispatchers.IO)
                .mapResource { it?.toLocalModel() }
                .collect(::emit)
        }
    }

    override suspend fun getChannelDetail(channelId: String): Flow<Resource<Channel?>> {
        return flow {
            emit(Resource.Loading)
            dataSource.getChannelDetail(channelId)
                .flowOn(Dispatchers.IO)
                .mapResource { it?.toLocalModel() }
                .collect(::emit)
        }
    }

    override suspend fun getLanguages(): Flow<Resource<List<Language>>> {
        return flow {
            emit(Resource.Loading)
            dataSource.getLanguages()
                .flowOn(Dispatchers.IO)
                .mapResource { items -> items?.map { it.toLocalModel() }.orEmpty() }
                .collect(::emit)
        }
    }

    override suspend fun getCategories(): Flow<Resource<List<Category>>> {
        return flow {
            emit(Resource.Loading)
            dataSource.getCategories()
                .flowOn(Dispatchers.IO)
                .mapResource { items -> items?.map { it.toLocalModel() }.orEmpty() }
                .collect(::emit)
        }
    }

    override suspend fun getRegions(): Flow<Resource<List<Region>>> {
        return flow {
            emit(Resource.Loading)
            dataSource.getRegions()
                .flowOn(Dispatchers.IO)
                .mapResource { items -> items?.map { it.toLocalModel() }.orEmpty() }
                .collect(::emit)
        }
    }

    override suspend fun getCountries(): Flow<Resource<List<Country>>> {
        return flow {
            emit(Resource.Loading)
            dataSource.getCountries()
                .flowOn(Dispatchers.IO)
                .mapResource { items -> items?.map { it.toLocalModel() }.orEmpty() }
                .collect(::emit)
        }
    }

    override suspend fun getSubdivisions(): Flow<Resource<List<Subdivision>>> {
        return flow {
            emit(Resource.Loading)
            dataSource.getSubdivisions()
                .flowOn(Dispatchers.IO)
                .mapResource { items -> items?.map { it.toLocalModel() }.orEmpty() }
                .collect(::emit)
        }
    }

}