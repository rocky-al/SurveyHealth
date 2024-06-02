package com.Digicate.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.Digicate.room.interfa.UserDao
import com.Digicate.room.model.Item

@Database(entities = [Item::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
