package com.example.gunplogs.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fitInside
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gunplogs.R
import com.example.gunplogs.data.OldGunplogDatabase
import com.example.gunplogs.model.Kit
import com.example.gunplogs.ui.theme.GunplogsTheme
import com.example.gunplogs.viewmodels.GunplogViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// Ecran principal de l'application :
// barre de recherche permettant de rechercher dans la liste actuellement affichée
// barre de menu pour selectionner la liste voulue
// Affiche soit la collection de l'utilisateur, sa wishlist ou la liste complète des kits disponibles
fun GunplogsMainScreen(
    modifier : Modifier = Modifier,
    viewModel: GunplogViewModel = viewModel(),
    showInfoPage : (Int) -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val listKits = uiState.value.kits

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val jumpToFirstKitVisible =
        remember { derivedStateOf { listState.firstVisibleItemIndex > 370 } }

    Image(
        painter = painterResource(R.drawable.space_fit),
        contentDescription = "Space",
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .fillMaxSize()
    )
        LazyColumn (
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .padding(horizontal = 10.dp),
//            flingBehavior = rememberSnapFlingBehavior(lazyListState = listState),
        ) {
            items(listKits) { kit ->
                GunplogCard(
                    kit = kit,
                    addCollectionClicked = { viewModel.addKitToUserCollection(kit) },
                    addWishlistClicked = { viewModel.addKitToUserWishlist(kit) },
                    showInfoPage = showInfoPage
                )
            }
        }

    println("First visible index => " + listState.firstVisibleItemIndex)

    if (jumpToFirstKitVisible.value) {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        listState.animateScrollToItem(10)
                    }
                },
                modifier = Modifier
                    .padding(bottom = 16.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.fleche),
                    contentDescription = stringResource(R.string.go_to_top_of_list)
                )
            }
    }
}


@Preview
@Composable
fun GunplogsScreenPreview() {
    GunplogsTheme {
        GunplogsMainScreen(
            showInfoPage = {}
        )
    }
}
