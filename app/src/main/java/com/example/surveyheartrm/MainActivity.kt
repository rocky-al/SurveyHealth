package com.example.surveyheartrm

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.material3.LinearProgressIndicator
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Entity
import androidx.room.Room
import com.Digicate.room.AppDatabase
import com.Digicate.room.interfa.UserDao
import com.Digicate.room.model.Item
import com.example.surveyheartrm.Adapter.HomeAdapter
import com.example.surveyheartrm.ViewModal.HomeViewModal
import com.example.surveyheartrm.databinding.DialogAddTaskBinding
import com.example.surveyheartrm.databinding.MainActivityBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : ComponentActivity() {

    lateinit var binding: MainActivityBinding
    lateinit var homeViewModal: HomeViewModal
    lateinit var adapter : HomeAdapter
    var db: AppDatabase? = null
    var userDao: UserDao? = null


    var todoList: ArrayList<Item> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {

        binding = MainActivityBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        homeViewModal = HomeViewModal(this@MainActivity)

        setContentView(binding.root)

        init()
    }


    fun showAddTask(){
        var dialog = Dialog(this@MainActivity)
        var cart = DialogAddTaskBinding.inflate(layoutInflater)
        dialog.setContentView(cart.root)
        dialog.show()

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT

        dialog.show()
        val window = dialog.window
        window!!.attributes = lp

        cart.btnSave.setOnClickListener {
            var taskName = cart.edTaskName.text.toString()


            Log.d(" todoList.size.toLong() ", todoList.size.toString() )

            if(!taskName.isNullOrEmpty()){
                var item = Item(
                    todoList.size.toLong() +1 ,
                    taskName,
                    false,
                    1234
                )

                todoList.add(item)
                homeViewModal.addIitems(userDao!!, item)

                Toast.makeText(this@MainActivity , "Add Task Successfully",Toast.LENGTH_SHORT).show()

                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }
        }





    }

    fun initClickListener(){
        binding.btnAddNew.setOnClickListener {
            showAddTask()
        }
    }


    fun init() {
        initObserver()
        initClickListener()

        initSaveData()

        homeViewModal.getAllItems(userDao!!)

    }


    fun initAdapter(list: ArrayList<Item>) {
        todoList = list

         adapter = HomeAdapter(todoList , userDao!!,homeViewModal)
        binding.idToDoList.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        binding.idToDoList.adapter = adapter

    }

    fun initObserver() {
        homeViewModal.loading.observe(this) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }


        homeViewModal.itemList.observe(this) {
            initAdapter(it)
        }


    }

    fun initSaveData() {
        // Initialize the database
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "Digicat"
        ).build()

        userDao = db!!.userDao()


    }
}
