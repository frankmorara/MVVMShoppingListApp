package com.frankmorara.mvvmshoppinglist

import android.app.Application
import com.frankmorara.mvvmshoppinglist.data.db.ShoppingDatabase
import com.frankmorara.mvvmshoppinglist.data.repos.ShoppingRepository
import com.frankmorara.mvvmshoppinglist.ui.shoppinglist.ShoppingViewModelFactory
import kotlinx.coroutines.InternalCoroutinesApi
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ShoppingApplication: Application(), KodeinAware {

    @InternalCoroutinesApi
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@ShoppingApplication))
        bind() from singleton { ShoppingDatabase(instance()) }
        bind() from singleton {
            ShoppingRepository(
                instance()
            )
        }
        bind() from provider {
            ShoppingViewModelFactory(
                instance()
            )
        }
    }
}