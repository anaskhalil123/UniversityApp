package com.a.khalil.training.Models

import com.a.khalil.training.StudentsActivity


data class Teacher(
    var name: String = "",
    var specialty: String = "",
) {

    var teacherCourses: ArrayList<Course> = ArrayList()

    private var id: String = "TEACHER"

    init {
        val rand: Float = (Math.random()).toFloat()
        id += rand
    }

    override fun toString(): String {
        val coursesName: ArrayList<String> = ArrayList()
        teacherCourses.forEach { coursesName.add(it.name) }

        return """
            name:$name,
            id:$id,
            courses:$coursesName,
            specialty:$specialty
        """.trimIndent()
    }

    fun getId(): String {
        return id
    }


    fun placementCourse(course: Course) {
        course.getTeacher().getCourses().remove(course)
        course.setTeacher(this)
    }

    fun getStudents(): ArrayList<Student> {
        val students = StudentsActivity.students
        val myStudents = ArrayList<Student>()

        for (student in students) {
            if (student.getTeachers().contains(this) && (!myStudents.contains(student)))
                myStudents.add(student)
        }
        return myStudents
    }

    fun getCourses(): ArrayList<Course> {
        return teacherCourses
    }

}