package com.vandoc.iptv.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vandoc.iptv.data.model.local.*

/**
 * @author Ichvandi
 * Created on 27/08/2022 at 11:49.
 */
@Database(
    entities = [Category::class, Country::class, Language::class, Region::class, Subdivision::class],
    version = 1
)
abstract class IPTVDatabase : RoomDatabase() {
    abstract fun dataDao(): DataDao
}
