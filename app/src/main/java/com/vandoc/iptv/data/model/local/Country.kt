package com.vandoc.iptv.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Country(
    @PrimaryKey
    val id: String,
    @ColumnInfo
    val idLanguage: String,
    @ColumnInfo
    val idRegion: String,
    @ColumnInfo
    val name: String,
    @ColumnInfo
    val flag: String
)