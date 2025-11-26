package com.example.gunplogs.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gunplogs.data.KitsRepository
import com.example.gunplogs.model.Category
import com.example.gunplogs.model.Kit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class GunplogsUiState(
    val kits : List<Kit> = listOf<Kit>(),
    val category: Category = Category.ALL,
)

class GunplogViewModel(var repository: KitsRepository) : ViewModel() {

    private val currentCategory = MutableStateFlow(Category.ALL)
    private val search = MutableStateFlow<String>("")

    val uiState: StateFlow<GunplogsUiState> =
        combine(
            repository.getAllKitsStream(),
            currentCategory,
            search,
        ) { kits, category, search ->
            GunplogsUiState(
                kits = kits.filter { isKitInCurrentCategory(it) && isKitInCurrentSearch(it) },
                category = currentCategory.value
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = GunplogsUiState(
                    kits = emptyList(),
                    category = Category.ALL,
                ),
            )

    /**
     * Returns true if kit belongs to currentCategory defined in UiState
     */
    fun isKitInCurrentCategory(kit : Kit) : Boolean {

        when(currentCategory.value) {
            Category.ALL -> return true
            Category.COLLECTION -> return (kit.IsInCollection)
            Category.WISHLIST -> return (kit.IsInWishlist)
        }
        return false
    }

    /**
     * Returns true if the search entry corresponds to the name, series or manufacturer of the kit.
     */
    fun isKitInCurrentSearch(kit : Kit) : Boolean {
        if (search.value.isEmpty())
            return true
        else
            return  kit.name.contains(search.value, ignoreCase = true) ||
                    kit.series.contains(search.value, ignoreCase = true) ||
                    kit.manufacturer.contains(search.value, ignoreCase = true)

        return false
    }

    /**
     *  Updates the kits displayed to the user based on the newSearchValue entered.
     **/
    fun onSearchValueChanged(newSearchValue : String) {
        search.value = newSearchValue.trim()
    }

    /**
     *  Changes category based on what the user selected
     **/
    fun changeCategory(newCategory: Category) {
        currentCategory.value = newCategory
    }

    // Adds the kit to the collection
    fun addKitToUserCollection(kit : Kit) {
        val newKit = kit.copy(IsInCollection = !kit.IsInCollection)

        viewModelScope.launch {
            repository.updateKit(newKit)
        }
    }
    // Adds the kit to the wishlist
    fun addKitToUserWishlist(kit : Kit) {
        val newKit = kit.copy(IsInWishlist = !kit.IsInWishlist)

        viewModelScope.launch {
            repository.updateKit(newKit)
        }
    }

    fun loadKit(uid : Int?) : Kit {
        return repository.getKitStream(uid)
    }
}