package com.vandoc.iptv.data.remote

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.vandoc.iptv.base.Resource
import com.vandoc.iptv.data.model.request.SearchChannelsRequest
import com.vandoc.iptv.data.model.response.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Ichvandi
 * Created on 19/06/2022 at 10:26.
 */
class IPTVDataSource @Inject constructor(
    private val service: IPTVService,
    private val remoteConfig: FirebaseRemoteConfig
) {

    suspend fun searchChannels(queries: SearchChannelsRequest): Flow<Resource<ChannelPagingResponse>> {
        return flow {
            try {
                val queriesMap = hashMapOf<String, String>().apply {
                    queries.name?.let { put("name", it) }
                    queries.language?.let { put("language", it) }
                    queries.category?.let { put("category", it) }
                    queries.region?.let { put("region", it) }
                    queries.country?.let { put("country", it) }
                    queries.subdivision?.let { put("subdivision", it) }
                    queries.broadcastArea?.let { put("broadcast_area", it) }
                    queries.orderBy?.let { put("order_by", it) }
                    queries.orderType?.let { put("order_type", it) }
                    queries.limit?.let { put("limit", "$it") }
                    queries.cursor?.let { put("cursor", it) }
                }
                val response = service.searchChannels(queriesMap)
                if (response.isSuccessful) {
                    if (remoteConfig.getBoolean("is_nsfw")) {
                        emit(Resource.Success(response.body()?.data))
                    } else {
                        emit(Resource.Success(response.body()?.data))
                    }
                } else {
                    Timber.e(response.toString())
                    emit(Resource.Error.Unknown("An error occurred!"))
                }
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resource.Error.Unknown("An error occurred!"))
            }
        }
    }

    suspend fun getChannelDetail(channelId: String): Flow<Resource<ChannelResponse>> {
        return flow {
            try {
                val response = service.getChannelDetail(channelId)
                if (response.isSuccessful) {
                    emit(Resource.Success(response.body()?.data))
                } else {
                    Timber.e(response.toString())
                    emit(Resource.Error.Unknown("An error occurred!"))
                }
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resource.Error.Unknown("An error occurred!"))
            }
        }
    }

    suspend fun getLanguages(): Flow<Resource<List<LanguageResponse>>> {
        return flow {
            try {
                val response = service.getLanguages()
                if (response.isSuccessful) {
                    emit(Resource.Success(response.body()?.data))
                } else {
                    Timber.e(response.toString())
                    emit(Resource.Error.Unknown("An error occurred!"))
                }
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resource.Error.Unknown("An error occurred!"))
            }
        }
    }

    suspend fun getCategories(): Flow<Resource<List<CategoryResponse>>> {
        return flow {
            try {
                val response = service.getCategories()
                if (response.isSuccessful) {
                    emit(Resource.Success(response.body()?.data))
                } else {
                    Timber.e(response.toString())
                    emit(Resource.Error.Unknown("An error occurred!"))
                }
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resource.Error.Unknown("An error occurred!"))
            }
        }
    }

    suspend fun getRegions(): Flow<Resource<List<RegionResponse>>> {
        return flow {
            try {
                val response = service.getRegions()
                if (response.isSuccessful) {
                    emit(Resource.Success(response.body()?.data))
                } else {
                    Timber.e(response.toString())
                    emit(Resource.Error.Unknown("An error occurred!"))
                }
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resource.Error.Unknown("An error occurred!"))
            }
        }
    }

    suspend fun getCountries(): Flow<Resource<List<CountryResponse>>> {
        return flow {
            try {
                val response = service.getCountries()
                if (response.isSuccessful) {
                    emit(Resource.Success(response.body()?.data))
                } else {
                    Timber.e(response.toString())
                    emit(Resource.Error.Unknown("An error occurred!"))
                }
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resource.Error.Unknown("An error occurred!"))
            }
        }
    }

    suspend fun getSubdivisions(): Flow<Resource<List<SubdivisionResponse>>> {
        return flow {
            try {
                val response = service.getSubdivisions()
                if (response.isSuccessful) {
                    emit(Resource.Success(response.body()?.data))
                } else {
                    Timber.e(response.toString())
                    emit(Resource.Error.Unknown("An error occurred!"))
                }
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resource.Error.Unknown("An error occurred!"))
            }
        }
    }

//    private fun noNsfw(channel: Channel): Boolean {
//        val nsfwRegex = "adult|porn|xxx".toRegex()
//        return !channel.isNsfw
//                || channel.categories?.any { it.name.lowercase() == "xxx" } == false
//                || !channel.name.lowercase().contains(nsfwRegex)
//                || !channel.id.lowercase().contains(nsfwRegex)
//    }

}