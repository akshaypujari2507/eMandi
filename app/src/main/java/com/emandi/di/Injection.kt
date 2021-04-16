package com.emandi.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.emandi.data.local.db.AppDatabase
import com.emandi.data.remote.api.ApiClient
import com.emandi.data.repository.Repository
import com.emandi.ui.viewmodel.factory.ViewModelCartActivityFactory
import com.emandi.ui.viewmodel.factory.ViewModelMainActivityFactory
import com.emandi.ui.viewmodel.factory.ViewModelOrderActivityFactory
import com.emandi.ui.viewmodel.factory.ViewModelOrderDetailsActivityFactory


object Injection {

    var repo: Repository? = null

    //repo provider
    public fun provideRepository(context: Context): Repository {
        val database = AppDatabase.getInstance(context)
        val api = ApiClient.api

        if (repo == null) {
            synchronized(Repository::class.java) {
                if (repo == null) {
                    repo = Repository(database, api)
                }
            }
        }
        return repo!!
    }

    // main activity viewmodel provider
    fun provideMainActivityViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelMainActivityFactory(
            provideRepository(
                context
            )
        )
    }

    // cart activity viewmodel provider
    fun provideCartActivityViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelCartActivityFactory(
            provideRepository(
                context
            )
        )
    }

    // order activity viewmodel provider
    fun provideOrderActivityViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelOrderActivityFactory(
            provideRepository(
                context
            )
        )
    }

    // order details activity viewmodel provider
    fun provideOrderDetailsActivityViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelOrderDetailsActivityFactory(
            provideRepository(
                context
            )
        )
    }

}