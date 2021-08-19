package com.a.khalil.training

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.a.khalil.training.Models.Teacher

class TeacherCustomAdapter(val context: Context, val teachers: ArrayList<Teacher>) :
    RecyclerView.Adapter<TeacherCustomAdapter.MyHolder>() {

    var checkPosition = -1

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val teacher_name = itemView.findViewById<TextView>(R.id.teacher_name)
        val teacher_specialty = itemView.findViewById<TextView>(R.id.teacher_specialty)
        val teacher_id = itemView.findViewById<TextView>(R.id.teacher_id)
        val teacher_courses = itemView.findViewById<TextView>(R.id.teacher_courses)
        var imgTeacherSel = itemView.findViewById<ImageView>(R.id.imgTeacherSel)

        @SuppressLint("SetTextI18n")
        fun bind(teacher: Teacher) {
            val courses = ArrayList<String>()
            teacher.getCourses().forEach { courses.add(it.name) }
            teacher_name.setText("Name: ${teacher.name}")
            teacher_id.setText("Id: ${teacher.getId()}")
            teacher_specialty.setText("Specialty: ${teacher.specialty}")
            teacher_courses.setText("Courses: ${courses}")


            when (checkPosition) {
                -1 ->
                    imgTeacherSel.visibility = View.GONE
                adapterPosition ->
                    imgTeacherSel.visibility = View.VISIBLE
                else ->
                    imgTeacherSel.visibility = View.GONE
            }

            itemView.setOnClickListener {
                imgTeacherSel.visibility = View.VISIBLE
                if (checkPosition != adapterPosition) {
                    notifyItemChanged(checkPosition)
                    checkPosition = adapterPosition
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.teacher_item, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(teachers[position])
    }


    override fun getItemCount(): Int {
        return teachers.size
    }

    fun getSelected(): Teacher? {

        if (checkPosition != -1 && teachers.size > 0) {
            return teachers[checkPosition]
        }
        return null
    }

}