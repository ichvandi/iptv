package com.vandoc.iptv.data.model

import com.google.gson.annotations.SerializedName

data class Channel(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("native_name")
    val nativeName: String,
    @SerializedName("network")
    val network: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("subdivision")
    val subdivision: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("broadcast_area")
    val broadcastArea: List<String>?,
    @SerializedName("languages")
    val languages: List<Language>?,
    @SerializedName("categories")
    val categories: List<Category>?,
    @SerializedName("is_nsfw")
    val isNsfw: Boolean,
    @SerializedName("launched")
    val launched: String,
    @SerializedName("closed")
    val closed: String,
    @SerializedName("replaced_by")
    val replacedBy: String,
    @SerializedName("website")
    val website: String,
    @SerializedName("logo")
    val logo: String,
    @SerializedName("url")
    val url: List<String>?,
    @SerializedName("status")
    val status: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("deleted_at")
    val deletedAt: String?
)