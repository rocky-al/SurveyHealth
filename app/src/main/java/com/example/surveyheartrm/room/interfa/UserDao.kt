package com.Digicate.room.interfa

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.Digicate.room.model.Item


@Dao
interface UserDao {


    @Insert
    fun insertItem(item: Item)

    @Query("SELECT * FROM todo")
    fun getAllDataProductID(): List<Item>


    @Query("UPDATE todo SET completed = :status WHERE todo.id = :id")
    fun changeTaskStatus(id: Long, status: Int)


    @Query("UPDATE todo SET todo = :name WHERE todo.id = :id")
    fun updateName(id: Long, name: String)


}
