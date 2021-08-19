package com.a.khalil.training

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.a.khalil.training.Models.Student

class StudentCustomAdapter(private val students: ArrayList<Student>, val context: Context) :
    RecyclerView.Adapter<StudentCustomAdapter.ViewHolder>() {

    var checkPosition = -1 // -1  ---> not selected, 0 or over ---> selected

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var studentName = itemView.findViewById<TextView>(R.id.tv_studentName)
        var studentId = itemView.findViewById<TextView>(R.id.tv_studentId)
        var studentAge = itemView.findViewById<TextView>(R.id.tv_studentAge)
        var studentCourses = itemView.findViewById<TextView>(R.id.tv_studentCourses)
        var imageViewForSelected = itemView.findViewById<ImageView>(R.id.imageViewForSelected)

        @SuppressLint("SetTextI18n")
        fun bind(student: Student) {
            val coursesName: ArrayList<String> = ArrayList()
            students[position].takenCourses.forEach { coursesName.add(it.name) }

            studentName.text = "Name: ${students[position].name}"
            studentAge.text = "Age: ${students[position].age}"
            studentCourses.text = "Courses:${coursesName}"
            studentId.text = "Id: ${students[position].getId()}"

            when (checkPosition) {
                -1 ->
                    imageViewForSelected.visibility = View.GONE
                adapterPosition ->
                    imageViewForSelected.visibility = View.VISIBLE
                else ->
                    imageViewForSelected.visibility = View.GONE
            }

            itemView.setOnClickListener {
                imageViewForSelected.visibility = View.VISIBLE
                if (checkPosition != adapterPosition) {
                    notifyItemChanged(checkPosition)
                    checkPosition = adapterPosition
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.students_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return students.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(students[position])
    }

    fun getSelected(): Student? {

        if (checkPosition != -1 && students.size > 0) {
            return students[checkPosition]
        }
        return null
    }

}