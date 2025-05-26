package com.example.gunplogs.ui

import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import com.example.gunplogs.R
import com.example.gunplogs.data.GunplogDatabase
import com.example.gunplogs.model.Kit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.StateFlow

data class GunplogsUiState(
    val kits : List<Kit>,
    val searchValue : String,
    val category : String
)

class GunplogViewModel : ViewModel() {
    private var database = GunplogDatabase()
    private var kitsInDatabase = database.loadKits()
    private val _uiState : MutableStateFlow<GunplogsUiState> =
        MutableStateFlow(
            GunplogsUiState(
                kits = kitsInDatabase,
                searchValue = "",
                category = "Tous les kits"
            )
        )

    val uiState : StateFlow<GunplogsUiState> = _uiState.asStateFlow()

    // Updates the kits displayed to the user based on the
    // newSearchValue entered.
    fun onSearchValueChanged(newSearchValue : String) {
        _uiState.value = _uiState.value.copy(
            searchValue = newSearchValue,
            kits = kitsInDatabase.filter { kit ->
                (kit.name.contains(newSearchValue, ignoreCase = true) ||
                 kit.series.contains(newSearchValue, ignoreCase = true) ||
                 kit.series.contains(newSearchValue, ignoreCase = true)) &&
                        database.isKitInList(kit, uiState.value.category)
            }
        )
    }

    // Changes category based on what the user selected
    fun changeCategory(newCategory: String) {
        print("New category => $newCategory")
        _uiState.value = _uiState.value.copy(
            kits = database.loadCategoryList(newCategory),
            category = newCategory,
        )
    }

    // Adds the kit to the collection
    fun addKitToUserCollection(kit : Kit) {
        database.addToCollectionUser(kit)
    }

    // Adds the kit to the wishlist
    fun addKitToUserWishlist(kit : Kit) {
        database.addToWishist(kit)
    }



}