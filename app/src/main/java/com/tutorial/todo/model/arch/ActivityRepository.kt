package com.tutorial.todo.model.arch

import com.tutorial.todo.database.AppData
import com.tutorial.todo.database.AppDataBase
import com.tutorial.todo.database.CategoryAppData
import com.tutorial.todo.database.dao.ItemsAndCategory
import kotlinx.coroutines.flow.Flow

class ActivityRepository(private val appDataBase: AppDataBase) {


    fun getAllData(): Flow<List<AppData>> {
        return appDataBase.dataDao().getAllData()
    }

    fun getAllCategory(): Flow<List<CategoryAppData>> {
        return appDataBase.categoryDao().getAllCategoryData()
    }

    fun getItemsWithCategory(): Flow<List<ItemsAndCategory>> {
        return appDataBase.dataDao().getItemsWithCategory()
    }


    // region AppData
    suspend fun insert(appData: AppData) {
        appDataBase.dataDao().insert(appData)
    }


    suspend fun delete(appData: AppData) {
        appDataBase.dataDao().delete(appData)
    }

    suspend fun update(appData: AppData) {
        appDataBase.dataDao().update(appData)
    }
    suspend fun deleteAllItems() {
        appDataBase.dataDao().deleteAll()
    }
    // endregion


    //region CategoryData
    suspend fun insert(categoryAppData: CategoryAppData) {
        appDataBase.categoryDao().insert(categoryAppData)
    }


    suspend fun delete(categoryAppData: CategoryAppData) {
        appDataBase.categoryDao().delete(categoryAppData)
    }

    suspend fun update(categoryAppData: CategoryAppData) {
        appDataBase.categoryDao().update(categoryAppData)
    }

    suspend fun deleteAllCategory() {
        appDataBase.categoryDao().deleteAll()
    }


    //endregion
}