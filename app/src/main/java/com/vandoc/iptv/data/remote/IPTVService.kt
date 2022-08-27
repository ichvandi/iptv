package com.vandoc.iptv.data.remote

import com.vandoc.iptv.base.BaseResponse
import com.vandoc.iptv.data.model.response.*
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

    @GET("data/languages")
    suspend fun getLanguages(): Response<BaseResponse<List<LanguageResponse>>>

    @GET("data/categories")
    suspend fun getCategories(): Response<BaseResponse<List<CategoryResponse>>>

    @GET("data/regions")
    suspend fun getRegions(): Response<BaseResponse<List<RegionResponse>>>

    @GET("data/countries")
    suspend fun getCountries(): Response<BaseResponse<List<CountryResponse>>>

    @GET("data/subdivisions")
    suspend fun getSubdivisions(): Response<BaseResponse<List<SubdivisionResponse>>>

}