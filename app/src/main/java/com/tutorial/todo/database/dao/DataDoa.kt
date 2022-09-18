package com.tutorial.todo.database.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.tutorial.todo.database.AppData
import kotlinx.coroutines.flow.Flow
import java.lang.Appendable

@Dao
interface DataDoa {
    @Query("SELECT* FROM ToDo_items")
    fun getAllData(): Flow<List<AppData>>

    @Transaction
    @Query("SELECT* FROM ToDo_items")
    fun getItemsWithCategory(): Flow<List<ItemsAndCategory>>

    @Insert(onConflict = REPLACE)
    suspend fun insert(appData: AppData)

    @Delete
    suspend fun delete(appData: AppData)

    @Update
    suspend fun update(appData: AppData)

    @Query("DELETE FROM ToDo_items")
    suspend fun deleteAll()
}