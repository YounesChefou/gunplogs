package com.example.gunplogs.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Kit(
    @PrimaryKey(autoGenerate = true) val uid : Int = 0,
    val name : String,
    val series : String,
    val scale : String,
    val manufacturer : String,
    val date : String,
    val ean : String = "4573102591692", // TODO(), améliorez le script du JSON pour recuperer les EAN
    var IsInCollection : Boolean = false,
    var IsInWishlist : Boolean = false,
)