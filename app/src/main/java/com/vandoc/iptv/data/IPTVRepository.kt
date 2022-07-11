package com.vandoc.iptv.data

import com.vandoc.iptv.base.Resource
import com.vandoc.iptv.data.model.Channel
import kotlinx.coroutines.flow.Flow

/**
 * @author Ichvandi
 * Created on 19/06/2022 at 10:36.
 */
interface IPTVRepository {

    suspend fun getChannels(query: Map<String, String>): Flow<Resource<List<Channel>>>

}