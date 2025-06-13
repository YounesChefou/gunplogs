package com.example.gunplogs.data

import com.example.gunplogs.model.Kit
import kotlinx.coroutines.flow.Flow

interface KitsRepository {
    fun getKitStream(id: Int): Kit

    fun getKitCollection() : Flow<List<Kit>>

    fun getKitWishlist() : Flow<List<Kit>>

    fun getAllKitsStream(): Flow<List<Kit>>

    suspend fun insertKit(kit: Kit)

    suspend fun deleteKit(kit: Kit)

    suspend fun updateKit(kit: Kit)
}