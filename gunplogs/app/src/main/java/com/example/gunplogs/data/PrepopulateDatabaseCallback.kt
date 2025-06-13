package com.example.gunplogs.data

import android.content.Context
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.gunplogs.R
import com.example.gunplogs.model.Kit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray

class PrepopulateDatabaseCallback(private val context: Context) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        CoroutineScope(Dispatchers.IO).launch {
            prePopulateKits(context)
        }
    }

    suspend fun prePopulateKits(context: Context) {
        try {
            val kitDao = GunplogAppDatabase.getDatabase(context).kitDao()

            // Ouverture du fichier JSON, transformé ensuite en tableau
            val kitList: JSONArray =
                context.resources.openRawResource(R.raw.base_gunpla).bufferedReader().use {
                    JSONArray(it.readText())
                }


            //
            kitList.takeIf { it.length() > 0 }?.let { list ->
                for (index in 0 until list.length()) {
                    val kitObj = list.getJSONObject(index)
                    kitDao.insertKit(
                        Kit(
                            name = kitObj.getString("name"),
                            series = kitObj.getString("series"),
                            scale = kitObj.getString("scale"),
                            manufacturer = kitObj.getString("manufacturer"),
                            date = kitObj.getString("date"),
                        )
                    )

                }
                Log.e("User App", "successfully pre-populated users into database")
            }
        } catch (exception: Exception) {
            Log.e(
                "User App",
                exception.localizedMessage ?: "failed to pre-populate users into database"
            )
        }
    }
}