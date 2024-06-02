package com.Digicate.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ToDo")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val todo: String,
    var completed: Boolean,
    val userId: Long
)