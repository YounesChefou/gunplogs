package com.example.gunplogs.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.ColumnInfo
import com.example.gunplogs.R
import com.example.gunplogs.data.OldGunplogDatabase
import com.example.gunplogs.model.Kit
import com.example.gunplogs.ui.theme.GunplogsTheme

@Composable
fun GunplogInfoPage(
    kit : Kit,
    modifier : Modifier = Modifier,
    onClickAddKit : () -> (Unit),
    onClickAddWishlist : () -> (Unit),
    onClickReturnToMainScreen : () -> (Unit)
    //viewModel: GunplogViewModel = viewModel()
)
{
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
    ){
        Row {
            IconButton(onClick = onClickReturnToMainScreen) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.return_to_main_screen),
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }
        Row {
            Image(
                painter = painterResource(id = R.drawable.placeholder),
                contentDescription = stringResource(id = R.string.placeholder)
            )
            Text(
                text = kit.name,
            )
        }
        Row {
            Column {
                Text(
                    text = "Echelle : " + kit.scale
                )
                Text(
                    text = "Constructeur : " + kit.manufacturer
                )
                Text(
                    text = "Date de sortie : " + kit.date
                )
            }
        }
        Row (
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ){
            IconButton(onClick = onClickAddKit) {
                Icon(
                    imageVector = if (kit.IsInCollection) Icons.Default.CheckCircle else Icons.Default.AddCircle,
                    contentDescription = stringResource(R.string.add_collection),
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
            IconButton(onClick = onClickAddWishlist) {
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
fun GunplogInfoPagePreview(){
    val kit = OldGunplogDatabase().getKit(0)
    GunplogsTheme {
        GunplogInfoPage(
            kit = kit,
            onClickAddKit = {},
            onClickAddWishlist = {},
            onClickReturnToMainScreen = {}
        )
    }
}
