package com.vandoc.iptv.data.model.response


import com.google.gson.annotations.SerializedName

data class NetworkResponse(
    @SerializedName("ip")
    val ip: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("country_iso")
    val countryIso: String,
    @SerializedName("region_name")
    val regionName: String,
    @SerializedName("region_code")
    val regionCode: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("time_zone")
    val timeZone: String
)
