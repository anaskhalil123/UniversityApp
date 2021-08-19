package com.a.khalil.training.Models

import com.a.khalil.training.StudentsActivity

data class Course(
    var name: String = "",
    private var teacherCourse: Teacher = Teacher()
) {

    val isChecked: Boolean = false

    companion object {
        var course_id: Int = 0
    }

    init {
        course_id++
        teacherCourse.getCourses().add(this)
    }

    private var id = course_id

    override fun toString(): String {
        return """
            name:$name,
            id:$id,
            teacher course: ${teacherCourse.name}
        """.trimIndent()
    }

    fun getId(): Int {
        return id
    }

    fun getStudents(): ArrayList<Student> {
        val students = StudentsActivity.students
        val myStudents = ArrayList<Student>()

        for (student in students) {
            if (student.getCourses().contains(this) && (!myStudents.contains(student))) {
                myStudents.add(student)
            }
        }
        return myStudents
    }


    fun getTeacher(): Teacher {
        return teacherCourse
    }

    fun updateTeacher(teacher: Teacher) {
        teacherCourse = teacher
        teacher.getCourses().add(this)
    }

    fun setTeacher(teacher: Teacher) {
        teacherCourse = teacher
        teacherCourse.getCourses().add(this)
    }


}