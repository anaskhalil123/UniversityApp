package com.a.khalil.training

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var studentsButton: Button
    lateinit var coursesButton: Button
    lateinit var teachersButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        studentsButton = findViewById(R.id.btnStudents)
        coursesButton = findViewById(R.id.btnCourses)
        teachersButton = findViewById(R.id.btnTeachers)

        studentsButton.setOnClickListener {
            val intent = Intent(this, StudentsActivity::class.java)
            startActivity(intent)
        }

        coursesButton.setOnClickListener {
            val intent = Intent(this, CoursesActivity::class.java)
            startActivity(intent)
        }

        teachersButton.setOnClickListener {
            val intent = Intent(this, TeachersActivity::class.java)
            startActivity(intent)
        }
    }
}