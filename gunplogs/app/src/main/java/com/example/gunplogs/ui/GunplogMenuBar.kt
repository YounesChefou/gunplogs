package com.example.gunplogs.ui

import android.view.Display
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gunplogs.R
import com.example.gunplogs.model.Category
import com.example.gunplogs.ui.shapes.menuBoxRight
import com.example.gunplogs.ui.shapes.menuBoxLeft
import com.example.gunplogs.ui.shapes.hexShape
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
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ){
        Box(
            modifier = Modifier
                .clip(menuBoxLeft())
                .fillMaxHeight(fraction = 0.9f)
                .background(Color.Transparent)
                .width(150.dp)
                .border(5.dp, Color.Cyan, menuBoxLeft())
        ){
            Box(
                modifier = Modifier
                    .fillMaxHeight(fraction = 0.7f)
                    .width(125.dp)
                    .clip(menuBoxLeft())
                    .clickable(
                        onClick = { onCategoryChange(Category.COLLECTION)},
                    )
                    .background(Color.Magenta)
                    .align(Alignment.Center)
            )
            {
                Text(
                    text = stringResource(R.string.collection),
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
        Box(
            modifier = Modifier
                .clip(hexShape())
                .background(Color.Transparent)
                .size(75.dp)
                .border(5.dp, Color.Cyan, hexShape())
        )
        {
            Box(
                modifier = Modifier
                    .clip(hexShape())
                    .size(50.dp)
                    .background(Color.Magenta)
                    .align(alignment = Alignment.Center)
            ){
                Text(
                    text = "+",
                    modifier = Modifier
                        .align(Alignment.Center),
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                        fontSize = 25.sp,
                    )
                )
            }
        }
        Box(
            modifier = Modifier
                .clip(menuBoxRight())
                .fillMaxHeight(fraction = 0.9f)
                .background(Color.Transparent)
                .width(150.dp)
                .border(5.dp, Color.Cyan, menuBoxRight())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(fraction = 0.7f)
                    .width(125.dp)
                    .clip(menuBoxRight())
                    .clickable(
                        onClick = { onCategoryChange(Category.WISHLIST) },
                    )
                    .background(Color.Magenta)
                    .align(Alignment.Center)
            )
            {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = stringResource(R.string.wishlist),
                )
            }
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
