package com.tutorial.todo.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "ToDo_items")
data class AppData(
    @PrimaryKey val id: String = "",
    val title:String = "",
    val description:String? =null,
    val priority:Int = 0,
    val createdAt:Long = 0L,
    val category:String = "",
    )


