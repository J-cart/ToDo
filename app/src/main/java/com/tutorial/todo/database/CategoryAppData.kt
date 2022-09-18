package com.tutorial.todo.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ToDo_Category")
data class CategoryAppData(
    @PrimaryKey
    val id: String = "",
    val name: String = "",
) {
    companion object {
        const val DEFAULT_CATEGORY_ID = "NONE"

        fun defaultCategory(): CategoryAppData{
            return CategoryAppData(DEFAULT_CATEGORY_ID,"None")
        }
    }


}