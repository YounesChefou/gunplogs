package com.example.gunplogs.data

import com.example.gunplogs.model.Kit
import kotlin.collections.mutableListOf

class OldGunplogDatabase {

    private val kits = listOf<Kit>(
        Kit(
            uid = 15,
            name = "RG 1/144 UNICORN GUNDAM 03 PHENEX (NARRATIVE Ver.)",
            series = "narrative",
            scale = "rg-c",
            manufacturer = "bandai",
            date = "2025.Jun.01"
        ),
        Kit(
            uid = 2,
            name = "RG 1/144 CROSSBONE GUNDAM X2",
            series = "crossbone",
            scale = "rg-c",
            manufacturer = "bandai",
            date = "2025.Jun.01"
        ),
        Kit(
            uid = 3,
            name = "HG 1/144 Death Army",
            series = "ggundam",
            scale = "hg",
            manufacturer = "bandai",
            date = "2025.Jun.01"
        ),
        Kit(
            uid = 4,
            name = "MG 1/100 SINANJU (ANIME COLOR Ver.)",
            series = "unicorn",
            scale = "mg",
            manufacturer = "bandai",
            date = "2025.Jun.01"
        )
    )

    var collectionUser : List<Kit> = mutableListOf<Kit>(kits.get(3))

    var wishlistUser : List<Kit> = mutableListOf<Kit>(kits.get(1))

    // Returns a list with every kit in the database
    fun loadKits() : List<Kit> {
        return kits
    }

    // Returns a list with the kits in the user collection
    fun loadUserCollectionKits() : List<Kit> {
        return collectionUser
    }

    // Returns a list with the kits in the user wishlist
    fun loadUserWishlistKits() : List<Kit> {
        return wishlistUser
    }

    // Returns list based on Category
    fun loadCategoryList(category : String) : List<Kit> {
        when(category) {
            "Tous les kits" -> return loadKits()
            "Collection" -> return loadUserCollectionKits()
            "Wishlist" -> return loadUserWishlistKits()
        }
        return listOf<Kit>()
    }

    // Returns if kit is present in the currently selected category,
    fun isKitInList(kit : Kit, category : String) : Boolean {
        when(category) {
            "Tous les kits" -> return true
            "Collection" -> return collectionUser.contains(kit)
            "Wishlist" -> return wishlistUser.contains(kit)
            else -> return false
        }
        return false
    }
    fun getKit(index : Int) : Kit {
        return kits.get(index)
    }

    fun addToCollectionUser(kit : Kit) {
        collectionUser += kit
    }

    fun addToWishist(kit : Kit) {
        wishlistUser += kit
    }

    fun printKit(index : Int) : String {
        return buildString {
            append("Name : ${kits.get(index).name}\n")
            append("Series : ${kits.get(index).series}\n")
            append("Scale : ${kits.get(index).scale}\n")
        }
    }

    fun printKit(kit: Kit) : String {
        return buildString {
            append("Name : ${kit.name}\n")
            append("Series : ${kit.series}\n")
            append("Scale : ${kit.scale}\n")
        }
    }
}