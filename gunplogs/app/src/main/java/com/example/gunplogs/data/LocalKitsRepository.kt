package com.example.gunplogs.data

import com.example.gunplogs.model.Kit
import kotlinx.coroutines.flow.Flow

class LocalKitsRepository(private val kitDao : KitDao) : KitsRepository {
    override fun getKitStream(uid: Int?): Kit = kitDao.loadKit(uid)

    override fun getKitCollection() : List<Kit> = kitDao.loadCollection()

    override fun getKitWishlist() : List<Kit> = kitDao.loadWishlist()

    override fun getAllKitsStream(): List<Kit> = kitDao.loadAll()

    override suspend fun insertKit(kit: Kit) = kitDao.insertKit(kit)

    override suspend fun deleteKit(kit: Kit) = kitDao.deleteKit(kit)

    override suspend fun updateKit(kit: Kit) = kitDao.updateKit(kit)
}