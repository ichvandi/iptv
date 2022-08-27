package com.vandoc.iptv.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vandoc.iptv.data.model.local.Language

/**
 * @author Ichvandi
 * Created on 27/08/2022 at 12:23.
 */
@Dao
interface DataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLanguages(languages: List<Language>): List<Long>

    @Query("SELECT * FROM Language")
    suspend fun getLanguages(): List<Language>

    @Query("SELECT * FROM Language WHERE LOWER(name) LIKE '%' || :query || '%'")
    suspend fun searchLanguages(query: String): List<Language>

}