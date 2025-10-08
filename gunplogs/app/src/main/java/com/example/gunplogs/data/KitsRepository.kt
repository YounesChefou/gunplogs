package com.example.gunplogs.data

import com.example.gunplogs.model.Kit
import kotlinx.coroutines.flow.Flow

interface KitsRepository {
    /**
     * Retrieves Kit based on its id in database
     */
    fun getKitStream(id: Int?): Kit

    /**
     * Retrieves the list of Kit where IsInCollection is TRUE
     */
    fun getKitCollection() : List<Kit>

    /**
     * Retrieves the list of Kit where IsInWishlist is TRUE
     */
    fun getKitWishlist() : List<Kit>

    /**
     * Retrieves all kits available in database
     */
    fun getAllKitsStream(): Flow<List<Kit>>

    /**
     * Inserts new Kit in database.
     */
    suspend fun insertKit(kit: Kit)

    /**
     * Deletes kit from database.
     */
    suspend fun deleteKit(kit: Kit)

    /**
     * Updates info on Kit in database.
     */
    suspend fun updateKit(kit: Kit)
}