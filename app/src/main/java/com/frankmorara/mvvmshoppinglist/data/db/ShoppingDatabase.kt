package com.frankmorara.mvvmshoppinglist.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.frankmorara.mvvmshoppinglist.data.db.entities.ShoppingItem
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(
    entities = [ShoppingItem::class],
    version = 1
)
abstract class ShoppingDatabase:RoomDatabase() {
    abstract fun getShoppingDao(): ShoppingDao
    companion object{

        //creating and instance or singleton or database
        @Volatile
        private var instance: ShoppingDatabase? = null
        private val LOCK = Any()

        //Called every time we call and instance of the shopping item
        @InternalCoroutinesApi
        operator fun invoke(context: Context) = instance
                ?: synchronized(LOCK){
//            context = it
            instance
                    ?: createDatabase(
                            context).also { instance = it}
        }
        //Instantiating our database
        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
            ShoppingDatabase::class.java,"ShoppingDB.db").build()
    }

}