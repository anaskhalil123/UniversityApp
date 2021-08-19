package com.a.khalil.training

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.a.khalil.training.Models.Course

class CourseCustomAdapter(val context: Context, val courses: ArrayList<Course>) :
    RecyclerView.Adapter<CourseCustomAdapter.MyHolder>() {

    var checkPosition = -1

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val course_name = itemView.findViewById<TextView>(R.id.course_name)
        val course_id = itemView.findViewById<TextView>(R.id.course_id)
        val course_teachers = itemView.findViewById<TextView>(R.id.course_teachers)
        var imgCourseSel = itemView.findViewById<ImageView>(R.id.imgCourseSel)

        @SuppressLint("SetTextI18n")
        fun bind(course: Course) {

            course_name.setText("Name: ${course.name}")
            course_id.setText("Id: ${course.getId()}")
            course_teachers.setText("Teacher: ${course.getTeacher().name}")

            when (checkPosition) {
                -1 ->
                    imgCourseSel.visibility = View.GONE
                adapterPosition ->
                    imgCourseSel.visibility = View.VISIBLE
                else ->
                    imgCourseSel.visibility = View.GONE
            }

            itemView.setOnClickListener {
                imgCourseSel.visibility = View.VISIBLE
                if (checkPosition != adapterPosition) {
                    notifyItemChanged(checkPosition)
                    checkPosition = adapterPosition
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.course_item, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(courses[position])
    }

    override fun getItemCount(): Int {
        return courses.size
    }

    fun getSelected(): Course? {

        if (checkPosition != -1 && courses.size > 0) {
            return courses[checkPosition]
        }
        return null
    }
}