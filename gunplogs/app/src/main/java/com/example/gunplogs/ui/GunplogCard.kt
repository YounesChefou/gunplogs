package com.example.gunplogs.ui

import android.media.Image
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gunplogs.R
import com.example.gunplogs.data.OldGunplogDatabase
import com.example.gunplogs.model.Kit
import com.example.gunplogs.ui.theme.GunplogsTheme
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.unit.TextUnit

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)
val fontName = GoogleFont("Allerta Stencil")
val fontFamily = FontFamily(
    Font(
        googleFont = fontName,
        fontProvider = provider,
        weight = FontWeight.Bold,
        style = FontStyle.Italic
    ))


@Composable
fun GunplogCard(
    kit : Kit,
    addCollectionClicked : (Kit) -> Unit,
    addWishlistClicked : (Kit) -> Unit,
    showInfoPage : (Int) -> Unit
){
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = { showInfoPage(kit.uid) }
            )
            .height(200.dp)
            .width(500.dp)
            .background(Color(0x2D0091FF))
            .border(width = 2.dp, color = Color(0xA8008DFC))
            .shadow(elevation = 2.dp, ambientColor = Color(0xA8008DFC))
    ) {
//        Image(
//            painter = painterResource(R.drawable.turn_a),
//            contentDescription = "TURN A TURN",
//            contentScale = ContentScale.FillBounds,
//            modifier = Modifier
//                .fillMaxSize()
//        )
//        val brush = SolidColor(Color(0x40EE4F1F))
//
//        Canvas(
//            modifier = Modifier
//                .fillMaxSize(),
//            onDraw = {
//                drawRect(brush)
//            }
//        )
        Row (
            modifier = Modifier
                .align(Alignment.Center)
        ){
            Text(
                text = printKit(kit),
                modifier = Modifier
                    .padding(8.dp),
                color = Color.White,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    shadow = Shadow(
                        color = Color(0xFAFF3000), blurRadius = 10f
                    )
                )
            )
        }
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ){
            gradeImage(kit.scale)
            Row (
            ){
                IconButton(onClick = {
                    addCollectionClicked(kit)
                }) {
                    Icon(
                        imageVector = if (kit.IsInCollection) Icons.Default.CheckCircle else Icons.Default.AddCircle,
                        contentDescription = stringResource(R.string.add_collection),
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
                IconButton(onClick = {
                    addWishlistClicked(kit)
                }){
                    Icon(
                        imageVector = if (kit.IsInWishlist) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = stringResource(R.string.add_wishlist, kit.name),
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }
//        Image(
//            painter = painterResource(R.drawable.scanlines),
//            contentDescription = "scanline",
//            modifier = Modifier
//                .fillMaxHeight()
//                .fillMaxWidth()
//                .clipToBounds()
//        )
    }
}

fun printKit(kit: Kit) : String {
    return buildString {
        append("${kit.name}\n")
        append("${kit.series}\n")
        append("${kit.date}\n")
    }
}

@Composable
fun gradeImage(scale : String) : Unit {
    return Image(
        painter = when(scale.lowercase()) {
            "hg" -> painterResource(R.drawable.high_grade_logo)
            "mg" -> painterResource(R.drawable.mg_logo)
            "rg-c" -> painterResource(R.drawable.rg_logo)
            else -> painterResource(R.drawable.high_grade_logo)
        },
        contentDescription = when(scale.lowercase()) {
            "hg" -> "HIGH GRADE"
            "mg" -> "MASTER GRADE"
            "rg-c" -> "REAL GRADE"
            else -> "HIGH GRADE"
        },
        modifier = Modifier
            .height(50.dp)
            .width(84.dp)
            .padding(5.dp)
    )
}

@Preview
@Composable
fun GunplaCardPreview1() {
    var kit = OldGunplogDatabase().getKit(0)
    GunplogsTheme {
        GunplogCard(
            kit = kit,
            addCollectionClicked = {},
            addWishlistClicked = {},
            showInfoPage = {}
        )
    }
}

@Preview
@Composable
fun GunplaCardPreview2() {
    var kit = OldGunplogDatabase().getKit(2)
    kit = kit.copy(IsInCollection = true, IsInWishlist = true)
    GunplogsTheme {
        GunplogCard(
            kit = kit,
            addCollectionClicked = {},
            addWishlistClicked = {},
            showInfoPage = {}
        )
    }
}

@Preview
@Composable
fun GunplaCardPreview3() {
    var kit = OldGunplogDatabase().getKit(3)
    kit = kit.copy(IsInWishlist = true)
    GunplogsTheme {
        GunplogCard(
            kit = kit,
            addCollectionClicked = { kit = kit.copy(IsInCollection = !kit.IsInCollection) },
            addWishlistClicked = { kit = kit.copy(IsInWishlist = !kit.IsInWishlist) },
            showInfoPage = {}
        )
    }
}