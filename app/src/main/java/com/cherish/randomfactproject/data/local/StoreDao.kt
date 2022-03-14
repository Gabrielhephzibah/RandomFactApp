package com.cherish.randomfactproject.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cherish.randomfactproject.data.model.StoreResponse
@Dao
interface StoreDao {
    @Query("SELECT * from StoreResponse")
   suspend fun getAllItemFromDb(): List<StoreResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend  fun insertAllItemToDb(item : List<StoreResponse>)
}