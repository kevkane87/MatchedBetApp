package com.example.android.kevkane87.matchedbetapp.findbets

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FindBetsViewModelFactory(val app: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FindBetsViewModel::class.java)) {
            return FindBetsViewModel(app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}