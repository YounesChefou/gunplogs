package com.example.gunplogs.ui

import android.view.Display
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.gunplogs.R
import com.example.gunplogs.model.Category
import com.example.gunplogs.ui.theme.GunplogsTheme

@Composable
fun GunplogMenuBar(
    modifier : Modifier = Modifier,
    onCategoryChange : (Category) -> Unit,
    currentCategory : Category
) {
    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ){

        Button(
            onClick = { onCategoryChange(Category.ALL) },
            colors = if (currentCategory == Category.ALL) ButtonDefaults.buttonColors(Color.LightGray)
            else ButtonDefaults.buttonColors(Color.Magenta)
        )
        {
            Text(text = stringResource(R.string.all_kits))
        }
        Button(
            onClick = { onCategoryChange(Category.COLLECTION)},
            colors = if (currentCategory == Category.COLLECTION) ButtonDefaults.buttonColors(Color.LightGray)
            else ButtonDefaults.buttonColors(Color.Magenta)
        )
        {
            Text(text = stringResource(R.string.collection))
        }
        Button(
            onClick = { onCategoryChange(Category.WISHLIST)},
            colors = if (currentCategory == Category.WISHLIST) ButtonDefaults.buttonColors(Color.LightGray)
            else ButtonDefaults.buttonColors(Color.Magenta)
        )
        {
            Text(text = stringResource(R.string.wishlist))
        }
    }
}

@Preview
@Composable
fun GunplogMenuBarPreview1() {
    GunplogsTheme {
        GunplogMenuBar(
            onCategoryChange = {},
            currentCategory = Category.ALL
        )
    }
}

@Preview
@Composable
fun GunplogMenuBarPreview2() {
    GunplogsTheme {
        GunplogMenuBar(
            onCategoryChange = {},
            currentCategory = Category.COLLECTION
        )
    }
}

@Preview
@Composable
fun GunplogMenuBarPreview3() {
    GunplogsTheme {
        GunplogMenuBar(
            onCategoryChange = {},
            currentCategory = Category.WISHLIST
        )
    }
}
