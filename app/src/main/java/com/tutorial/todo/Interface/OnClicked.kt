package com.tutorial.todo.Interface

import com.tutorial.todo.database.AppData
import com.tutorial.todo.database.CategoryAppData

interface OnClicked {
    fun itemClicked(data: AppData)
}