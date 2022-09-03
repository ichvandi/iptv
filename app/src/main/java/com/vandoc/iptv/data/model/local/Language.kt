package com.vandoc.iptv.data.model.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity
data class Language(
    @PrimaryKey
    val id: String,
    @ColumnInfo
    val name: String
) : Parcelable