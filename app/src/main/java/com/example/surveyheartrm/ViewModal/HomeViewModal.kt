package com.example.surveyheartrm.ViewModal


import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Dao
import com.Digicate.room.interfa.UserDao
import com.Digicate.room.model.Item
import com.sara.support.repository.UserRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject


class HomeViewModal(val context: Context) : ViewModel() {
    // creating a new variable for course repository.
    private val userRepository = UserRepositoryImpl()

    private val _loading: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)

    private val _itemList = MutableLiveData<ArrayList<Item>>()


    val loading: LiveData<Boolean> = _loading
    var itemList: LiveData<ArrayList<Item>> = _itemList


    fun addIitems(userDao: UserDao, item: Item) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
//                var item = Item(9, "Home Work", true, 4)
                userDao.insertItem(item)
            }
        }
    }


    fun changeTaskStatus(id: Long, status: Boolean, userDao: UserDao) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (status)
                    userDao.changeTaskStatus(id, 1)
                else
                    userDao.changeTaskStatus(id, 0)
            }
        }
    }


    fun updateTaskName(id: Long, name: String, userDao: UserDao) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                userDao.updateName(id, name)
            }
        }
    }




    fun getAllItems(userDao: UserDao) {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {


                var list = userDao.getAllDataProductID()

                if (list.size == 0) {
                    setAllDotoList(userDao)
                } else {

                    var list: ArrayList<Item> = ArrayList(list)

                    _itemList.postValue(list)
                }


            }
        }
    }

    fun setAllDotoList(userDao: UserDao) {


        viewModelScope.launch {

            _loading.postValue(true)
            try {

                val response = JSONObject(userRepository.getAllList().toString())

                val handler = Handler(Looper.getMainLooper())
                handler.post(Runnable {
                    // Update UI components here


                    var list: ArrayList<Item> = ArrayList()

                    var todos = response.getJSONArray("todos")
                    for (i in 0 until todos.length()) {

                        var itemJson = todos.getJSONObject(i)
                        var item = Item(
                            itemJson.getLong("id"),
                            itemJson.getString("todo"),
                            itemJson.getBoolean("completed"),
                            itemJson.getLong("userId")
                        )
                        list.add(item)
                        addIitems(userDao, item)
                    }

                    _loading.postValue(false)

                    _itemList.postValue(list)

                })
            } catch (e: Exception) {
                Log.d("Log Error", e.message.toString())
            }


        }

    }
}

