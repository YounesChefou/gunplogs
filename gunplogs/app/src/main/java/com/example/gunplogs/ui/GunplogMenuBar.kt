package com.example.gunplogs.ui

import android.view.Display
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.gunplogs.R
import com.example.gunplogs.ui.theme.GunplogsTheme

@Composable
fun GunplogMenuBar(
    modifier : Modifier = Modifier,
    onCategoryChange : (String) -> Unit,
) {
    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ){
        val strAllKits = stringResource(R.string.all_kits)
        val strCollection = stringResource(R.string.collection)
        val strWishlist = stringResource(R.string.wishlist)
        Button(
            onClick = { onCategoryChange(strAllKits) }
        )
        {
            Text(text = strAllKits)
        }
        Button(
            onClick = { onCategoryChange(strCollection)}
        )
        {
            Text(text = strCollection)
        }
        Button(
            onClick = { onCategoryChange(strWishlist) }
        )
        {
            Text(text = strWishlist)
        }
    }
}

@Preview
@Composable
fun GunplogMenuBarPreview() {
    GunplogsTheme {
        GunplogMenuBar(
            onCategoryChange = {}
        )
    }
}