package com.tutorial.todo.database.dao

import androidx.room.Embedded
import androidx.room.Relation
import com.tutorial.todo.database.AppData
import com.tutorial.todo.database.CategoryAppData

class ItemsAndCategory (
    @Embedded  val AppData:AppData,
    @Relation(
        parentColumn = "category",
        entityColumn = "id"
    )
    val CategoryAppData: CategoryAppData?
                        )