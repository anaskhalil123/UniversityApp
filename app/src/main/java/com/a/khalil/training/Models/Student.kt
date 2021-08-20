package com.a.khalil.training.Models

import android.util.Log
import com.a.khalil.training.StudentsActivity

data class Student(
    var name: String = "",
    var age: Int = 20
) {

    val isChecked: Boolean = false
    var takenCourses: ArrayList<Course> = ArrayList<Course>()
    private var universityId: Long = staticUniversityId

    companion object {
        var staticUniversityId: Long = 120190900
    }

    init {
        staticUniversityId++
    }

    override fun toString(): String {
        val coursesName: ArrayList<String> = ArrayList()
        takenCourses.forEach { coursesName.add(it.name) }

        return """ 
            name:$name
            universityId:$universityId
            takenCourses:${coursesName}
            age:$age
        """.trimIndent()
    }

    fun getId(): Long {
        return universityId
    }

    fun addCourse(course: Course) {
        if (!takenCourses.contains(course))
            takenCourses.add(course)
    }

    fun removeCourse(course: Course) {
        if (takenCourses.contains(course))
            takenCourses.remove(course)
    }

    fun getTeachers(): ArrayList<Teacher> {
        val myTeachers =  ArrayList<Teacher>()

        for (course in takenCourses) {
            Log.d("StudentsActivity", course.toString())
            if (!myTeachers.contains(course.getTeacher())) {
                Log.d("StudentsActivity", course.getTeacher().toString())
                myTeachers.add(course.getTeacher())
            }
        }

        return myTeachers
    }

    fun getCourses(): ArrayList<Course> {
        return takenCourses
    }


}