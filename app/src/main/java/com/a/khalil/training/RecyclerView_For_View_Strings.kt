package com.a.khalil.training

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RecyclerView_For_View_Strings : AppCompatActivity() {
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_for_view_strings)


        if (intent.getStringArrayListExtra("coursesFromStudent") != null) {

            val courses_name = intent.getStringArrayListExtra("coursesFromStudent")

            val viewMyCourses = ArrayAdapter<String>(
                this,
                R.layout.textview,
                courses_name!!
            )

            findViewById<ListView>(R.id.listViewForViewStrings).adapter =
                viewMyCourses

            val text = findViewById<TextView>(R.id.textViewView)
            text.setText("Courses")
        } else if (intent.getStringArrayListExtra("teachersFromStudent") != null) {
            val teachers_names = intent.getStringArrayListExtra("teachersFromStudent")

            val viewTeachers = ArrayAdapter<String>(this, R.layout.textview, teachers_names!!)

            findViewById<ListView>(R.id.listViewForViewStrings).adapter =
                viewTeachers

            val text = findViewById<TextView>(R.id.textViewView)
            text.setText("Teachers")
        } else if (intent.getStringArrayListExtra("studentsFromCourse") != null) {
            val students_names = intent.getStringArrayListExtra("studentsFromCourse")

            val viewStudents = ArrayAdapter<String>(this, R.layout.textview, students_names!!)

            findViewById<ListView>(R.id.listViewForViewStrings).adapter =
                viewStudents

            val text = findViewById<TextView>(R.id.textViewView)
            text.setText("Students")
        } else if (intent.getStringExtra("teacherFromCourse") != null) {
            val teacher_name = intent.getStringExtra("teacherFromCourse")

            var text = findViewById<TextView>(R.id.textViewView)
            text.setText("Teacher Name : $teacher_name")

            text.setTextColor(R.color.very_black)
            text.setTextSize(23f)
        } else if (intent.getStringArrayListExtra("studentsFromTeacher") != null) {
            val students_names = intent.getStringArrayListExtra("studentsFromTeacher")

            val viewStudents = ArrayAdapter<String>(this, R.layout.textview, students_names!!)

            findViewById<ListView>(R.id.listViewForViewStrings).adapter =
                viewStudents

            val text = findViewById<TextView>(R.id.textViewView)
            text.setText("Students")
        } else if (intent.getStringArrayListExtra("coursesFromTeacher") != null) {

            val courses_names = intent.getStringArrayListExtra("coursesFromTeacher")

            val viewCourses = ArrayAdapter<String>(this, R.layout.textview, courses_names!!)

            findViewById<ListView>(R.id.listViewForViewStrings).adapter =
                viewCourses

            val text = findViewById<TextView>(R.id.textViewView)
            text.setText("Courses")
        }

    }
}