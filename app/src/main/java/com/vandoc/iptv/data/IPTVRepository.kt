package com.vandoc.iptv.data

import com.vandoc.iptv.base.Resource
import com.vandoc.iptv.data.model.local.Channel
import com.vandoc.iptv.data.model.local.ChannelPaging
import com.vandoc.iptv.data.model.request.SearchChannelsRequest
import kotlinx.coroutines.flow.Flow

/**
 * @author Ichvandi
 * Created on 19/06/2022 at 10:36.
 */
interface IPTVRepository {

    suspend fun searchChannels(queries: SearchChannelsRequest): Flow<Resource<ChannelPaging?>>

    suspend fun getChannelDetail(channelId: String): Flow<Resource<Channel?>>

}