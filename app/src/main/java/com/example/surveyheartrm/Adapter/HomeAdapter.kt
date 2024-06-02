package com.example.surveyheartrm.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.Digicate.room.interfa.UserDao
import com.Digicate.room.model.Item
import com.example.surveyheartrm.R
import com.example.surveyheartrm.ViewModal.HomeViewModal
import com.example.surveyheartrm.databinding.CardViewBinding


class HomeAdapter(var list: ArrayList<Item>, var userDao: UserDao, var viewModal: HomeViewModal) :
    RecyclerView.Adapter<HomeAdapter.ViewModal>() {

    class ViewModal(var item: CardViewBinding) : RecyclerView.ViewHolder(item.root)

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewModal {
        var layoutInflator =
            CardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        context = parent.context
        return ViewModal(layoutInflator)
    }

    override fun onBindViewHolder(holder: ViewModal, position: Int) {
        with(holder) {

            item.tvCourseName.setText(list.get(position).todo)

            if (list.get(position).completed) {
                item.imStatus.setImageDrawable(context.getDrawable(R.drawable.ic_checked))
            } else {
                item.imStatus.setImageDrawable(context.getDrawable(R.drawable.ic_unselected))
            }


            item.imStatus.setOnClickListener {
                showDialog(list.get(position).id, list.get(position).completed, item,position)
            }

            item.icEdit.setOnClickListener {
                item.btnSave.visibility = View.VISIBLE
                item.icEdit.visibility = View.GONE

                item.tvCourseName.isEnabled = true

                item.tvCourseName.requestFocus()
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm!!.showSoftInput(item.tvCourseName, InputMethodManager.SHOW_IMPLICIT)
                item.tvCourseName.setSelection(item.tvCourseName.length())

            }

            item.btnSave.setOnClickListener {


                item.btnSave.visibility = View.GONE
                item.icEdit.visibility = View.VISIBLE

                viewModal.updateTaskName(list.get(position).id, item.tvCourseName.text.toString(), userDao)

                item.tvCourseName.isClickable = false
                item.tvCourseName.isEnabled = false
            }



        }
    }

    fun showDialog(id: Long, status: Boolean, item: CardViewBinding ,position: Int) {


        val builder: AlertDialog.Builder = AlertDialog.Builder(context)

        builder.setMessage("Are you sure you want to change task status")

        builder.setTitle("Task Status")

        builder.setCancelable(false)

        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.

        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setPositiveButton("Yes",
            DialogInterface.OnClickListener { dialog: DialogInterface, which: Int ->
                // When the user click yes button then app will close
                viewModal.changeTaskStatus(id, !status, userDao)

                list.get(position).completed = !status

                notifyItemChanged(position)

                if (!status) {
                    Toast.makeText(context, "Task Marked Completed", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Task Unmarked Completed", Toast.LENGTH_SHORT).show()
                }


                dialog.cancel()
            } as DialogInterface.OnClickListener)
        builder.setNegativeButton("No",
            DialogInterface.OnClickListener { dialog: DialogInterface, which: Int ->
                // If user click no then dialog box is canceled.


                dialog.cancel()
            } as DialogInterface.OnClickListener)

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }


    override fun getItemCount(): Int {
        return list.size
    }

}