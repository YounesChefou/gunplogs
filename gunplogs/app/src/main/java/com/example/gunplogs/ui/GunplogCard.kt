package com.example.gunplogs.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gunplogs.R
import com.example.gunplogs.data.OldGunplogDatabase
import com.example.gunplogs.model.Kit
import com.example.gunplogs.ui.theme.GunplogsTheme

@Composable
fun GunplogCard(
    kit : Kit,
    addCollectionClicked : () -> Unit,
    addWishlistClicked : () -> Unit,
){
    ElevatedCard (
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFCCC2DC)
        ),
        modifier = Modifier
            .fillMaxWidth(),
        shape = CardDefaults.outlinedShape
    )
    {
        Text(
            text = OldGunplogDatabase().printKit(kit),
            modifier = Modifier
                .padding(8.dp)
        )

        Row (
            modifier = Modifier.align(Alignment.End)
        ){
            IconButton(onClick = addCollectionClicked) {
                Icon(
                    imageVector = if (kit.IsInCollection) Icons.Default.CheckCircle else Icons.Default.AddCircle,
                    contentDescription = stringResource(R.string.add_collection),
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
            IconButton(onClick = addWishlistClicked) {
                Icon(
                    imageVector = if (kit.IsInWishlist) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(R.string.add_wishlist, kit.name),
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}

@Preview
@Composable
fun GunplaCardPreview1() {
    var kit = OldGunplogDatabase().getKit(0)
    GunplogsTheme {
        GunplogCard(
            kit = kit,
            addCollectionClicked = {},
            addWishlistClicked = {}
        )
    }
}

@Preview
@Composable
fun GunplaCardPreview2() {
    var kit = OldGunplogDatabase().getKit(0)
    kit.IsInCollection = true
    kit.IsInWishlist = true
    GunplogsTheme {
        GunplogCard(
            kit = kit,
            addCollectionClicked = {},
            addWishlistClicked = {}
        )
    }
}

@Preview
@Composable
fun GunplaCardPreview3() {
    var kit = OldGunplogDatabase().getKit(0)
    kit.IsInWishlist = true
    GunplogsTheme {
        GunplogCard(
            kit = kit,
            addCollectionClicked = {},
            addWishlistClicked = {}
        )
    }
}