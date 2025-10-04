package com.example.gunplogs.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gunplogs.data.KitDao
import com.example.gunplogs.data.KitsRepository

class GunplogViewModelFactory(private val kitsRepository: KitsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GunplogViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GunplogViewModel(kitsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}