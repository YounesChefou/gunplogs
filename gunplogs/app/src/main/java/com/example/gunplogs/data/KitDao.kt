package com.example.gunplogs.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.gunplogs.model.Kit
import kotlinx.coroutines.flow.Flow

@Dao
interface KitDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertKit(kit : Kit)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertKits(vararg kits : Kit)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateKit(kit : Kit)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateKits(vararg kits : Kit)

    @Delete
    suspend fun deleteKit(kit : Kit)

    @Delete
    suspend fun deleteKits(vararg kits: Kit)

    @Query("SELECT * FROM kit WHERE uid = :uid")
    fun loadKit(uid : Int?) : Kit

    @Query("SELECT * FROM kit WHERE IsInCollection = TRUE")
    fun loadCollection() : Flow<List<Kit>>

    @Query("SELECT * FROM kit WHERE IsInWishlist = TRUE")
    fun loadWishlist() : Flow<List<Kit>>

    @Query("SELECT * FROM kit ORDER BY name ASC")
    fun loadAll() : Flow<List<Kit>>
}