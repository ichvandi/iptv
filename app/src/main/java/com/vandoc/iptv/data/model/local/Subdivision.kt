package com.vandoc.iptv.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Subdivision(
    @PrimaryKey
    val id: String,
    @ColumnInfo
    val idCountry: String,
    @ColumnInfo
    val name: String
)