package com.vandoc.iptv.data.remote

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.vandoc.iptv.base.Resource
import com.vandoc.iptv.data.model.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @author Ichvandi
 * Created on 19/06/2022 at 10:26.
 */
class IPTVDataSource @Inject constructor(
    private val service: IPTVService,
    private val remoteConfig: FirebaseRemoteConfig
) {

    suspend fun getChannels(query: Map<String, String>): Flow<Resource<List<Channel>>> {
        val response = service.getChannels(query)
        return flow {
            if (response.isSuccessful) {
                if (remoteConfig.getBoolean("is_nsfw")) {
                    emit(Resource.Success(response.body()?.data.orEmpty()))
                } else {
                    emit(Resource.Success(response.body()?.data.orEmpty().filter(::noNsfw)))
                }
            } else {
                emit(Resource.Error.Unknown("An error occurred!"))
            }
        }
    }

    private fun noNsfw(channel: Channel): Boolean {
        val nsfwRegex = "adult|porn|xxx".toRegex()
        return !channel.isNsfw
                || channel.categories?.any { it.name.lowercase() == "xxx" } == false
                || !channel.name.lowercase().contains(nsfwRegex)
                || !channel.id.lowercase().contains(nsfwRegex)
    }

}