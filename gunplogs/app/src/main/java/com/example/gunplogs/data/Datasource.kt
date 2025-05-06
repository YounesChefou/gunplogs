package com.example.gunplogs.data

import com.example.gunplogs.model.Kit

class Datasource {

    val kits = listOf<Kit>(
        Kit(
            uid = 1,
            name = "RG 1/144 UNICORN GUNDAM 03 PHENEX (NARRATIVE Ver.)",
            series = "narrative",
            scale = "rg-c",
            manufacturer = "bandai"
        ),
        Kit(
            uid = 2,
            name = "RG 1/144 CROSSBONE GUNDAM X2",
            series = "crossbone",
            scale = "rg-c",
            manufacturer = "bandai"
        ),
        Kit(
            uid = 3,
            name = "HG 1/144 Death Army",
            series = "ggundam",
            scale = "hg",
            manufacturer = "bandai"
        ),
        Kit(
            uid = 4,
            name = "MG 1/100 SINANJU (ANIME COLOR Ver.)",
            series = "unicorn",
            scale = "mg",
            manufacturer = "bandai"
        )
    )

    var wishlistUser : List<Kit> = listOf<Kit>(kits.get(1))

    var collectionUser : List<Kit> = listOf<Kit>(kits.get(3))

    fun loadKits() : List<Kit> {
        return kits
    }

    fun getKit(index : Int) : Kit {
        return kits.get(index)
    }

    fun printKit(index : Int) : String {
        return buildString {
            append("Name : ${kits.get(index).name}\n")
            append("Series : ${kits.get(index).series}\n")
            append("Scale : ${kits.get(index).scale}\n")
        }
    }
}