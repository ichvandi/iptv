package com.vandoc.iptv.data.model.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * @author Ichvandi
 * Created on 19/08/2022 at 21:19.
 */
@Parcelize
data class SearchChannelsRequest(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("language")
    val language: String? = null,
    @SerializedName("category")
    val category: String? = null,
    @SerializedName("region")
    val region: String? = null,
    @SerializedName("country")
    val country: String? = null,
    @SerializedName("subdivision")
    val subdivision: String? = null,
    @SerializedName("broadcast_area")
    val broadcastArea: String? = null,
    @SerializedName("order_by")
    val orderBy: String? = null,
    @SerializedName("order_type")
    val orderType: String? = null,
    @SerializedName("limit")
    val limit: Int? = null,
    @SerializedName("cursor")
    val cursor: String? = null
) : Parcelable
