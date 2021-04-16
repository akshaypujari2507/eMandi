package com.emandi.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.emandi.data.repository.Repository
import com.emandi.ui.viewmodel.OrderActivityViewModel

class ViewModelOrderActivityFactory(private val repository: Repository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OrderActivityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}