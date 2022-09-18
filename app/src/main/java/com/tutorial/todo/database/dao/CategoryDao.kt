package com.tutorial.todo.database.dao

import androidx.room.*
import com.tutorial.todo.database.AppData
import com.tutorial.todo.database.CategoryAppData
import kotlinx.coroutines.flow.Flow
@Dao
interface CategoryDao {
    @Query("SELECT* FROM ToDo_Category")
    fun  getAllCategoryData(): Flow<List<CategoryAppData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categoryAppData: CategoryAppData)

    @Delete
    suspend fun delete(categoryAppData: CategoryAppData)

    @Update
    suspend fun update(categoryAppData: CategoryAppData)

    @Query("DELETE FROM ToDo_Category")
    suspend fun deleteAll()
}