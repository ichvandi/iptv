package com.vandoc.iptv.data

import com.vandoc.iptv.base.Resource
import com.vandoc.iptv.data.model.local.*
import com.vandoc.iptv.data.model.request.SearchChannelsRequest
import kotlinx.coroutines.flow.Flow

/**
 * @author Ichvandi
 * Created on 19/06/2022 at 10:36.
 */
interface IPTVRepository {
    suspend fun searchChannels(queries: SearchChannelsRequest): Flow<Resource<ChannelPaging?>>
    suspend fun getChannelDetail(channelId: String): Flow<Resource<Channel?>>
    suspend fun getLanguages(): Flow<Resource<List<Language>>>
    suspend fun searchLanguages(query: String): Flow<Resource<List<Language>>>
    suspend fun getCategories(): Flow<Resource<List<Category>>>
    suspend fun searchCategories(query: String): Flow<Resource<List<Category>>>
    suspend fun getRegions(): Flow<Resource<List<Region>>>
    suspend fun searchRegions(query: String): Flow<Resource<List<Region>>>
    suspend fun getCountries(): Flow<Resource<List<Country>>>
    suspend fun searchCountries(query: String): Flow<Resource<List<Country>>>
    suspend fun getSubdivisions(): Flow<Resource<List<Subdivision>>>
    suspend fun searchSubdivisions(query: String): Flow<Resource<List<Subdivision>>>

}