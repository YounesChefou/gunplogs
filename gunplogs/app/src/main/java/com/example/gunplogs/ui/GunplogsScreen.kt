package com.example.gunplogs.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gunplogs.R
import com.example.gunplogs.ui.theme.GunplogsTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// Ecran principal de l'application :
// barre de recherche permettant de rechercher dans la liste actuellement affichée
// barre de menu pour selectionner la liste voulue
// Affiche soit la collection de l'utilisateur, sa wishlist ou la liste complète des kits disponibles
fun GunplogsScreen(
    modifier : Modifier = Modifier,
    viewModel: GunplogViewModel = viewModel(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val flowKits = uiState.value.kits
    val listKits = flowKits.collectAsState(initial = emptyList()).value
    val category = uiState.value.category

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.headlineLarge, // TODO() : faire le theme principal
                        color = Color(0xFF22054D),
                    )
                }
            )
        }
    ) { innerPadding ->
        LazyColumn (
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ){
            item {
                GunplogSearchBar(
                    searchValue = uiState.value.searchValue,
                    onSearchChange = { viewModel.onSearchValueChanged(it)}
                )
            }
            item {
                GunplogMenuBar(
                    onCategoryChange = { viewModel.changeCategory(it) }
                )
            }
            items (listKits) { kit ->
                    GunplogCard(
                        kit = kit,
                        addCollectionClicked = { viewModel.addKitToUserCollection(kit) },
                        addWishlistClicked = { viewModel.addKitToUserWishlist(kit) },
                    )
                }
            }
        }
}

@Preview
@Composable
fun GunplogsScreenPreview() {
    GunplogsTheme {
        GunplogsScreen()
    }
}
