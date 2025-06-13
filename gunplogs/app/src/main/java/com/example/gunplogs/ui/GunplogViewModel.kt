package com.example.gunplogs.ui

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gunplogs.data.GunplogAppDatabase
import com.example.gunplogs.data.LocalKitsRepository
import com.example.gunplogs.model.Kit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toCollection

data class GunplogsUiState(
    val kits : Flow<List<Kit>>,
    val searchValue : String,
    val category : String
)

class GunplogViewModel(var context : Context) : ViewModel() {
    private var repository : LocalKitsRepository = LocalKitsRepository(GunplogAppDatabase.getDatabase(context).kitDao())
    private var kitsInDatabase = repository.getAllKitsStream()
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
            kits = kitsInDatabase.map { kits -> kits.filter {
                kit ->(kit.name.contains(newSearchValue, ignoreCase = true) ||
                       kit.series.contains(newSearchValue, ignoreCase = true) ||
                       kit.manufacturer.contains(newSearchValue, ignoreCase = true))
                    // && database.isKitInList(kit, uiState.value.category) TODO() : retablir les categories
                }
            }
        )
    }

    // Changes category based on what the user selected
    fun changeCategory(newCategory: String) {
        print("New category => $newCategory")
        _uiState.value = _uiState.value.copy(
            //kits = database.loadCategoryList(newCategory),
            category = newCategory,
        )
    }

    // Adds the kit to the collection
    fun addKitToUserCollection(kit : Kit) {
        //database.addToCollectionUser(kit)
    }

    // Adds the kit to the wishlist
    fun addKitToUserWishlist(kit : Kit) {
        //database.addToWishist(kit)
    }



}