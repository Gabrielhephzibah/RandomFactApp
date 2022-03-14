package com.cherish.randomfactproject.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cherish.randomfactproject.data.model.StoreResponse
import com.cherish.randomfactproject.utils.DataConverter

@Database(entities = [StoreResponse::class], version = 1, exportSchema = false)
@TypeConverters(DataConverter::class)
abstract class StoreDatabase :RoomDatabase() {
    abstract fun storeDao() : StoreDao
}