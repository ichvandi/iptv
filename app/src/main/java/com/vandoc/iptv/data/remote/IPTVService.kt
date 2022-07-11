package com.vandoc.iptv.data.remote

import com.vandoc.iptv.base.BaseResponse
import com.vandoc.iptv.data.model.Channel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * @author Ichvandi
 * Created on 19/06/2022 at 10:04.
 */
interface IPTVService {

    @GET("channel")
    suspend fun getChannels(
        @QueryMap
        query: Map<String, String>
    ): Response<BaseResponse<List<Channel>>>

}