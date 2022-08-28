package com.vandoc.iptv.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vandoc.iptv.data.model.local.*

/**
 * @author Ichvandi
 * Created on 27/08/2022 at 12:23.
 */
@Dao
interface DataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<Category>): List<Long>

    @Query("SELECT * FROM Category ORDER BY name")
    suspend fun getCategories(): List<Category>

    @Query("SELECT * FROM Category WHERE LOWER(name) LIKE '%' || :query || '%' ORDER BY name")
    suspend fun searchCategories(query: String): List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountries(categories: List<Country>): List<Long>

    @Query("SELECT * FROM Country ORDER BY name")
    suspend fun getCountries(): List<Country>

    @Query("SELECT * FROM Country WHERE LOWER(name) LIKE '%' || :query || '%' ORDER BY name")
    suspend fun searchCountries(query: String): List<Country>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLanguages(languages: List<Language>): List<Long>

    @Query("SELECT * FROM Language ORDER BY name")
    suspend fun getLanguages(): List<Language>

    @Query("SELECT * FROM Language WHERE LOWER(name) LIKE '%' || :query || '%' ORDER BY name")
    suspend fun searchLanguages(query: String): List<Language>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRegions(regions: List<Region>): List<Long>

    @Query("SELECT * FROM Region ORDER BY name")
    suspend fun getRegions(): List<Region>

    @Query("SELECT * FROM Region WHERE LOWER(name) LIKE '%' || :query || '%' ORDER BY name")
    suspend fun searchRegions(query: String): List<Region>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubdivisions(subdivisions: List<Subdivision>): List<Long>

    @Query("SELECT * FROM Subdivision ORDER BY name")
    suspend fun getSubdivisions(): List<Subdivision>

    @Query("SELECT * FROM Subdivision WHERE LOWER(name) LIKE '%' || :query || '%' ORDER BY name")
    suspend fun searchSubdivisions(query: String): List<Subdivision>

}