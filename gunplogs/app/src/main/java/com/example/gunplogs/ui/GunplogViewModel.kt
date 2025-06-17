package com.example.gunplogs.ui

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.example.gunplogs.data.GunplogAppDatabase
import com.example.gunplogs.data.LocalKitsRepository
import com.example.gunplogs.model.Category
import com.example.gunplogs.model.Kit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.launch

data class GunplogsUiState(
    val kits : Flow<List<Kit>>,
    val searchValue : String,
    val category: Category = Category.ALL
)

class GunplogViewModel(var context : Context) : ViewModel() {
    private var repository : LocalKitsRepository = LocalKitsRepository(GunplogAppDatabase.getDatabase(context).kitDao())
    private var kitsInDatabase = repository.getAllKitsStream()
    private var currentCategory = Category.ALL
    private val _uiState : MutableStateFlow<GunplogsUiState> =
        MutableStateFlow(
            GunplogsUiState(
                kits = kitsInDatabase,
                searchValue = "",
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
                }
            },
            category = currentCategory
        )
    }

    // Changes category based on what the user selected
    fun changeCategory(newCategory: Category) {
        var newKits : Flow<List<Kit>>
        when(newCategory) {
            Category.ALL ->  newKits = repository.getAllKitsStream()
            Category.COLLECTION -> newKits = repository.getKitCollection()
            Category.WISHLIST -> newKits = repository.getKitWishlist()
        }

        kitsInDatabase = newKits

        _uiState.value = _uiState.value.copy(
            kits = kitsInDatabase,
            category = newCategory,
        )

        currentCategory = newCategory
    }

    // Adds the kit to the collection
    fun addKitToUserCollection(kit : Kit) {
        viewModelScope.launch(Dispatchers.IO) {
            kit.IsInCollection = !kit.IsInCollection
            repository.updateKit(kit)

            kitsInDatabase.map {
                    listKits -> listKits.map {newKit ->
                if (newKit.uid == kit.uid) {
                    newKit.copy(IsInWishlist = kit.IsInCollection)
                }
                else {
                    newKit
                }
            }
            }

            _uiState.value = _uiState.value.copy(
                kits = kitsInDatabase,
            )

            //changeCategory(_uiState.value.category) // reload the list at each change ?
        }
    }

    // Adds the kit to the wishlist
    fun addKitToUserWishlist(kit : Kit) {
        viewModelScope.launch(Dispatchers.IO) {
            kit.IsInWishlist = !kit.IsInWishlist
            repository.updateKit(kit)

            kitsInDatabase.map {
                listKits -> listKits.map {newKit ->
                    if (newKit.uid == kit.uid) {
                        newKit.copy(IsInWishlist = kit.IsInWishlist)
                    }
                    else {
                        newKit
                    }
                }
            }

            _uiState.value = _uiState.value.copy(
                kits = kitsInDatabase,
            )

            //changeCategory(_uiState.value.category) // reload the list at each change ?
        }
    }

}