package com.tutorial.todo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tutorial.todo.database.dao.CategoryDao
import com.tutorial.todo.database.dao.DataDoa

@Database(entities = [AppData::class, CategoryAppData::class], version = 2)
abstract class AppDataBase: RoomDatabase()  {
    companion object{
        private var appDataBase:AppDataBase? = null

        fun getDatabase(context: Context):AppDataBase{
            if (appDataBase != null)
                return appDataBase!!

            appDataBase = Room.databaseBuilder(context.applicationContext, AppDataBase::class.java, "ToDo-Database")
                .addMigrations(MIGRATION_1_2())
                .build()
            return appDataBase!!
        }
    }

    class  MIGRATION_1_2:Migration(1,2){
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE IF NOT EXISTS 'ToDo_Category'('id' TEXT NOT NULL, 'name' TEXT NOT NULL, PRIMARY KEY('id'))")
        }

    }
    abstract fun dataDao(): DataDoa
    abstract fun categoryDao():CategoryDao
}