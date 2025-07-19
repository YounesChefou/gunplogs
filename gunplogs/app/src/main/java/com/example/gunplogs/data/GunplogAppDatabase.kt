package com.example.gunplogs.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gunplogs.model.Kit

@Database(entities = [Kit::class], version = 2, exportSchema = false)
abstract class GunplogAppDatabase : RoomDatabase() {
    abstract fun kitDao(): KitDao
    companion object {
        @Volatile
        private var Instance: GunplogAppDatabase? = null
//        La variable Instance conservera une référence à la base de données,
//        si vous en avez créé une. Ainsi, une seule instance de la base de données
//        est ouverte à la fois. Cela est particulièrement intéressant,
//        car les bases de données ont besoin de beaucoup de ressources,
//        que ce soit pour leur création ou pour leur gestion.

        fun getDatabase(context: Context): GunplogAppDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, GunplogAppDatabase::class.java, "kit_database")
                    .addCallback(PrepopulateDatabaseCallback(context))
                    .allowMainThreadQueries()
                    .build()
                    .also { Instance = it }
            }
            // synchronized permet d'éviter la condition de concurrence
            //
        }

    }
}
