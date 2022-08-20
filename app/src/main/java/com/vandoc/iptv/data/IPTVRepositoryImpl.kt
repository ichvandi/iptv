package com.vandoc.iptv.data

import com.vandoc.iptv.base.Resource
import com.vandoc.iptv.data.model.local.ChannelPaging
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

}