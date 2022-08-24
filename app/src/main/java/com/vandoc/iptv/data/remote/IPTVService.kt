package com.vandoc.iptv.data.remote

import com.vandoc.iptv.base.BaseResponse
import com.vandoc.iptv.data.model.response.ChannelPagingResponse
import com.vandoc.iptv.data.model.response.ChannelResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

/**
 * @author Ichvandi
 * Created on 19/06/2022 at 10:04.
 */
interface IPTVService {

    @GET("channels")
    suspend fun searchChannels(
        @QueryMap
        query: Map<String, String>
    ): Response<BaseResponse<ChannelPagingResponse>>

    @GET("channels/{channel_id}")
    suspend fun getChannelDetail(
        @Path("channel_id")
        channelId: String
    ): Response<BaseResponse<ChannelResponse>>

}