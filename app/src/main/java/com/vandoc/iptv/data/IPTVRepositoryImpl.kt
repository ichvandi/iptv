package com.vandoc.iptv.data

import com.vandoc.iptv.base.Resource
import com.vandoc.iptv.data.local.DataDao
import com.vandoc.iptv.data.model.local.*
import com.vandoc.iptv.data.model.request.SearchChannelsRequest
import com.vandoc.iptv.data.remote.IPTVDataSource
import com.vandoc.iptv.util.mapResource
import com.vandoc.iptv.util.mapper.toLocalModel
import com.vandoc.iptv.util.networkBoundResource
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
    private val dataSource: IPTVDataSource,
    private val dao: DataDao
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
        return networkBoundResource(
            onQuery = { dao.getLanguages() },
            onFetch = { dataSource.getLanguages() },
            onMapping = { items -> items?.map { it.toLocalModel() } },
            onResult = { resource ->
                if (resource is Resource.Success) {
                    dao.insertLanguages(resource.data.orEmpty())
                }
            }
        )
    }

    override suspend fun searchLanguages(query: String): Flow<Resource<List<Language>>> {
        return flow {
            emit(Resource.Loading)
            val result = dao.searchLanguages(query)
            emit(Resource.Success(result))
        }
    }

    override suspend fun getCategories(): Flow<Resource<List<Category>>> {
        return networkBoundResource(
            onQuery = { dao.getCategories() },
            onFetch = { dataSource.getCategories() },
            onMapping = { items -> items?.map { it.toLocalModel() } },
            onResult = { resource ->
                if (resource is Resource.Success) {
                    dao.insertCategories(resource.data.orEmpty())
                }
            }
        )
    }

    override suspend fun searchCategories(query: String): Flow<Resource<List<Category>>> {
        return flow {
            emit(Resource.Loading)
            val result = dao.searchCategories(query)
            emit(Resource.Success(result))
        }
    }

    override suspend fun getRegions(): Flow<Resource<List<Region>>> {
        return networkBoundResource(
            onQuery = { dao.getRegions() },
            onFetch = { dataSource.getRegions() },
            onMapping = { items -> items?.map { it.toLocalModel() } },
            onResult = { resource ->
                if (resource is Resource.Success) {
                    dao.insertRegions(resource.data.orEmpty())
                }
            }
        )
    }

    override suspend fun searchRegions(query: String): Flow<Resource<List<Region>>> {
        return flow {
            emit(Resource.Loading)
            val result = dao.searchRegions(query)
            emit(Resource.Success(result))
        }
    }

    override suspend fun getCountries(): Flow<Resource<List<Country>>> {
        return networkBoundResource(
            onQuery = { dao.getCountries() },
            onFetch = { dataSource.getCountries() },
            onMapping = { items -> items?.map { it.toLocalModel() } },
            onResult = { resource ->
                if (resource is Resource.Success) {
                    dao.insertCountries(resource.data.orEmpty())
                }
            }
        )
    }

    override suspend fun searchCountries(query: String): Flow<Resource<List<Country>>> {
        return flow {
            emit(Resource.Loading)
            val result = dao.searchCountries(query)
            emit(Resource.Success(result))
        }
    }

    override suspend fun getSubdivisions(): Flow<Resource<List<Subdivision>>> {
        return networkBoundResource(
            onQuery = { dao.getSubdivisions() },
            onFetch = { dataSource.getSubdivisions() },
            onMapping = { items -> items?.map { it.toLocalModel() } },
            onResult = { resource ->
                if (resource is Resource.Success) {
                    dao.insertSubdivisions(resource.data.orEmpty())
                }
            }
        )
    }

    override suspend fun searchSubdivisions(query: String): Flow<Resource<List<Subdivision>>> {
        return flow {
            emit(Resource.Loading)
            val result = dao.searchSubdivisions(query)
            emit(Resource.Success(result))
        }
    }

}