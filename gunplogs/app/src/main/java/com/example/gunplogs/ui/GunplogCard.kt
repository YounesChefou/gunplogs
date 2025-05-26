package com.example.gunplogs.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
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
import com.example.gunplogs.data.GunplogDatabase
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
            text = GunplogDatabase().printKit(kit),
            modifier = Modifier
                .padding(8.dp)
        )

        Row (
            modifier = Modifier.align(Alignment.End)
        ){
            IconButton(onClick = addCollectionClicked) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = stringResource(R.string.add_collection),
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
            IconButton(onClick = addWishlistClicked) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(R.string.add_wishlist, kit.name),
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}

@Preview
@Composable
fun GunplaCardPreview() {
    var kit = GunplogDatabase().getKit(0)
    GunplogsTheme {
        GunplogCard(
            kit = kit,
            addCollectionClicked = {},
            addWishlistClicked = {}
        )
    }
}