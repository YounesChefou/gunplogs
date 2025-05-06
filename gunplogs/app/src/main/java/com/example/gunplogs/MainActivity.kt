package com.example.gunplogs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gunplogs.data.Datasource
import com.example.gunplogs.ui.theme.GunplogsTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GunplogsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
//@Entity
//data class Kit (name : String){
//    @PrimaryKey val uid : Int,
//    @ColumnInfo(name = "name") val name : String?,
//    @ColumnInfo(name = "series") val series : String?,
//    @ColumnInfo(name = "scale") val scale : String?,
//    @ColumnInfo(name = "manufacturer") val manufacturer : String?
//}
//
//@Dao
//interface KitDao {
//    @Query("SELECT * FROM kit")
//    fun getAll(): List<Kit>
//
//    @Query("SELECT * FROM kit WHERE uid IN (:kitsIds)")
//    fun loadAllByIds(kitsIds: IntArray): List<Kit>
//
//    @Query("SELECT * FROM kit WHERE name LIKE :name LIMIT 1")
//    fun findByName(name: String): Kit
//
//    @Insert
//    fun insertAll(vararg kits: Kit)
//
//    @Delete
//    fun delete(kit: Kit)
//}
//
//@Database(entities = [Kit::class], version = 1)
//abstract class AppDatabase : RoomDatabase() {
//    abstract fun kitDao(): KitDao
//}



enum class listType {
    Default, Collection, Wishlist
}

@Composable
fun displayList(
    type : listType
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (type) {
            listType.Default -> {
                item {
                    Card(
                        modifier = Modifier.padding(20.dp),
                        shape = CardDefaults.outlinedShape
                    )
                    {
                        Text(text = Datasource().printKit(0))
                    }
                }
            }

            listType.Collection -> {
                items(Datasource().collectionUser.size)
                { index ->
                    Card(
                        modifier = Modifier.padding(20.dp),
                        shape = CardDefaults.outlinedShape
                    )
                    {
                        Text(text = Datasource().printKit(index))
                    }
                }
            }

            listType.Wishlist -> {
                items(Datasource().wishlistUser.size)
                { index ->
                    Card(
                        modifier = Modifier.padding(20.dp),
                        shape = CardDefaults.outlinedShape
                    )
                    {
                        Text(text = Datasource().printKit(index))
                    }
                }
            }
        }
    }
    println("i bring the pain")
}

// understand how to import data and how to display it
var type = listType.Collection

@Composable
fun menuButton(menuItem : listType,  modifier : Modifier) {
    Button (onClick = {
        type = menuItem
        println("is you coked up")
    }) {
        Text (
            text = menuItem.toString(),
        )
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0x8397FF6C) // LightGreen
    ) {
        Column(
                horizontalAlignment = Alignment.CenterHorizontally
                ){
            Column(
                modifier = modifier.padding(vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    Text(
                        text = "GunPLoggers",
                        modifier = modifier.padding(horizontal = 10.dp),
                        fontSize = 50.sp
                    )
                }
                Row {
                    menuButton(
                        menuItem = listType.Collection,
                        modifier = modifier
                    )

                    Spacer(
                        modifier = Modifier.width(20.dp)
                    )

                    menuButton(
                        menuItem = listType.Wishlist,
                        modifier = modifier
                    )
                }

            }
            Spacer(
                modifier = Modifier.height(20.dp)
            )

            displayList(type)

        }

        Box (
            modifier = Modifier.padding(20.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            IconButton(
                onClick = {
                    println("hellooooo sir ")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Rechercher modèles",
                    modifier = Modifier
                        .size(50.dp)
                )

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GunplogsTheme {
        Greeting()
    }
}