package com.example.gunplogs.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gunplogs.data.KitsRepository
import com.example.gunplogs.model.Category
import com.example.gunplogs.model.Kit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class GunplogsUiState(
    val kits : List<Kit> = listOf<Kit>(),
    val category: Category = Category.ALL,
)

class GunplogViewModel(var repository: KitsRepository) : ViewModel() {

    private val _uiState : MutableStateFlow<GunplogsUiState> =
        MutableStateFlow(
            GunplogsUiState(
                kits = repository.getAllKitsStream(),
                category = Category.ALL,
            )
        )

    val uiState : StateFlow<GunplogsUiState> = _uiState.asStateFlow()

    fun getKitListBasedOnCategory(category: Category) : List<Kit> {
        var kits : List<Kit>

        when(category) {
            Category.ALL -> kits = repository.getAllKitsStream()
            Category.COLLECTION -> kits = repository.getKitCollection()
            Category.WISHLIST -> kits = repository.getKitWishlist()
        }

        return kits
    }
    // Updates the kits displayed to the user based on the
    // newSearchValue entered.
    fun onSearchValueChanged(newSearchValue : String) {

        _uiState.update { state ->
            state.copy(
                kits = getKitListBasedOnCategory(uiState.value.category).filter
                { kit ->
                    (kit.name.contains(newSearchValue, ignoreCase = true)) ||
                    (kit.series.contains(newSearchValue, ignoreCase = true)) ||
                    (kit.manufacturer.contains(newSearchValue, ignoreCase = true))
                },
            )
        }
    }

    // Changes category based on what the user selected
    fun changeCategory(newCategory: Category) {
        var newKits : List<Kit> = getKitListBasedOnCategory(newCategory)

        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    kits = newKits,
                    category = newCategory
                )
            }
        }
    }

    // Adds the kit to the collection
    fun addKitToUserCollection(kit : Kit) {
        val newKit = kit.copy(IsInCollection = !kit.IsInCollection)

        viewModelScope.launch {
            repository.updateKit(newKit)
            _uiState.update { state ->
                state.copy(kits = getKitListBasedOnCategory(uiState.value.category))
            }
        }
    }
    // Adds the kit to the wishlist
    fun addKitToUserWishlist(kit : Kit) {
        val newKit = kit.copy(IsInWishlist = !kit.IsInWishlist)

        viewModelScope.launch {
            repository.updateKit(newKit)
            _uiState.update { state ->
                state.copy(kits = getKitListBasedOnCategory(uiState.value.category))
            }
        }
    }

    fun loadKit(uid : Int?) : Kit {
        return repository.getKitStream(uid)
    }
}