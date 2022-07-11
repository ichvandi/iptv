package com.vandoc.iptv.data

import com.vandoc.iptv.base.Resource
import com.vandoc.iptv.data.model.Channel
import com.vandoc.iptv.data.remote.IPTVDataSource
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

    override suspend fun getChannels(query: Map<String, String>): Flow<Resource<List<Channel>>> {
        return flow {
            emit(Resource.Loading)
            dataSource.getChannels(query).flowOn(Dispatchers.IO).collect(::emit)
        }
    }

}