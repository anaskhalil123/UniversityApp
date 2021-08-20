package com.a.khalil.training

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a.khalil.training.Models.Course
import com.a.khalil.training.Models.Student
import com.a.khalil.training.Models.Teacher

class StudentsActivity : AppCompatActivity() {

    private val TAG = "StudentsActivity"
    var studentAdapter: StudentCustomAdapter = StudentCustomAdapter(students, this)
    lateinit var viewCourses: Spinner
    var addedCousre: Course? = null
    var removedCousre: Course? = null

    companion object {
        var students = ArrayList<Student>()
        var courses = ArrayList<Course>()
        var teachers = ArrayList<Teacher>()
        var openingTimes = 1
    }

    private val recyclerView: RecyclerView by lazy { findViewById(R.id.recyclerView) }
    private lateinit var alertDialog: AlertDialog

    override fun onStart() {
        super.onStart()
        openingTimes++
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_students)


        if (openingTimes <= 1) {
//            val khalid = Teacher("khalid", "Arabic Language")
//            teachers.add(khalid)
//
//            val Ali = Teacher("Ali", "Arabic Language")
//            teachers.add(Ali)
//
//            val course = Course("Arabic", khalid)
//            courses.add(course)
//
//            val math = Course("Math", Ali)
//            courses.add(math)
//
//            val English = Course("English", khalid)
//            courses.add(English)
//
//            val khalid_khalil = Student("khalid", "32")
//            students.add(khalid_khalil)
//            khalid_khalil.takenCourses.add(course)
//            students.add(Student("hassan", "25"))
//            students.add(Student("anas", "22"))
        }

        recyclerView.adapter = studentAdapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)
    }

    @SuppressLint("CutPasteId")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuAddStudent -> {

                val dialogView =
                    LayoutInflater.from(this).inflate(R.layout.dialog_add_student, null)

                alertDialog = AlertDialog.Builder(this)
                    .setView(dialogView)
                    .setTitle("Add Student")
                    .show()

                val name =
                    dialogView.findViewById<EditText>(R.id.dialog_student_name).text
                val age =
                    dialogView.findViewById<EditText>(R.id.dialog_student_age).text

                dialogView.findViewById<Button>(R.id.btnAdd).setOnClickListener {


                    if (name.isNotEmpty() && age.isNotEmpty()) {
                        val studentName: String = "" + name
                        val studentAge: Int = Integer.parseInt(age.toString())

                        val student = Student(studentName, studentAge)
                        val student1 = Student(studentName, studentAge)
                        student1.takenCourses = student.takenCourses
                        if (!students.contains(student1)) {
                            students.add(student)
                        }
                        studentAdapter.notifyDataSetChanged()
                        alertDialog.dismiss()
                    } else {
                        Toast.makeText(this, "fill the fields", Toast.LENGTH_SHORT).show()
                        alertDialog.dismiss()
                    }

                }
                dialogView.findViewById<Button>(R.id.btnCancel).setOnClickListener {
                    alertDialog.dismiss()
                }
            }

            R.id.menuRemoveStudent -> {
                Log.d(TAG, studentAdapter.getSelected().toString())

                if (studentAdapter.getSelected() != null && students.size > 0) {
                    val studentToRemoved = studentAdapter.getSelected()
                    if (students.contains(studentToRemoved))
                        students.remove(studentToRemoved)
                    studentAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this, "No Selection", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            R.id.menuUpdateStudent -> {
                if (studentAdapter.getSelected() != null && students.size > 0) {
                    val dialogView =
                        LayoutInflater.from(this).inflate(R.layout.dialog_add_student, null)

                    alertDialog = AlertDialog.Builder(this)
                        .setTitle("Update")
                        .setCancelable(false)
                        .setView(dialogView)
                        .show()

                    val student: Student? = studentAdapter.getSelected()

                    dialogView.findViewById<Button>(R.id.btnAdd).setText("Update")

                    dialogView.findViewById<EditText>(R.id.dialog_student_name)
                        .setText(student!!.name)

                    dialogView.findViewById<EditText>(R.id.dialog_student_age)
                        .setText(student.age.toString())

                    dialogView.findViewById<Button>(R.id.btnAdd).setOnClickListener {
                        val updatedName =
                            dialogView.findViewById<EditText>(R.id.dialog_student_name).text

                        val updatedAge =
                            dialogView.findViewById<EditText>(R.id.dialog_student_age).text

                        if (updatedName.isNotEmpty() && updatedAge.isNotEmpty()) {
                            student.name = "$updatedName"
                            student.age = Integer.parseInt(updatedAge.toString())
                            studentAdapter.notifyDataSetChanged()
                            alertDialog.dismiss()
                        } else {
                            Toast.makeText(this, "fill fields", Toast.LENGTH_SHORT).show()
                            alertDialog.dismiss()
                        }
                    }

                    dialogView.findViewById<Button>(R.id.btnCancel).setOnClickListener {
                        alertDialog.dismiss()
                    }
                } else {
                    Toast.makeText(this, "No selection", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.menuAddCourse -> {

                if (studentAdapter.getSelected() != null && students.size > 0) {
                    val dialogView =
                        LayoutInflater.from(this).inflate(R.layout.dialog_add_course, null)

                    alertDialog = AlertDialog.Builder(this)
                        .setView(dialogView)
                        .setTitle("Add Course")
                        .setCancelable(false)
                        .show()


                    val student: Student? = studentAdapter.getSelected()
                    addedCousre = Course()

                    viewCourses = dialogView.findViewById(R.id.spinner)
                    val course_name = dialogView.findViewById<TextView>(R.id.dialog_course_name)


                    val options = ArrayList<String>()
                    courses.forEach { options.add(it.name) }

                    viewCourses.adapter = ArrayAdapter<String>(
                        this,
                        R.layout.support_simple_spinner_dropdown_item,
                        options
                    )

                    Log.d(TAG, options.toString())
                    viewCourses.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                p0: AdapterView<*>?,
                                p1: View?,
                                p2: Int,
                                p3: Long
                            ) {
                                addedCousre = courses[p2]
                                course_name.text = options[p2]
                            }

                            override fun onNothingSelected(p0: AdapterView<*>?) {
                                Toast.makeText(
                                    this@StudentsActivity,
                                    "choose course",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                    dialogView.findViewById<Button>(R.id.btnAdd).setOnClickListener {
                        if (addedCousre != null) {
                            student!!.addCourse(addedCousre!!)
                            studentAdapter.notifyDataSetChanged()
                        } else {
                            Toast.makeText(this, "choose course", Toast.LENGTH_SHORT).show()
                        }
                        alertDialog.dismiss()
                    }

                    dialogView.findViewById<Button>(R.id.btnCancel).setOnClickListener {
                        alertDialog.dismiss()
                    }

                } else {
                    Toast.makeText(this, "No selection", Toast.LENGTH_SHORT).show()
                }

            }

            R.id.menuRemoveCourse -> {
                if (studentAdapter.getSelected() != null && students.size > 0) {
                    if (studentAdapter.getSelected()!!.getCourses().isNotEmpty()) {
                        val dialogView =
                            LayoutInflater.from(this).inflate(R.layout.dialog_add_course, null)

                        alertDialog = AlertDialog.Builder(this)
                            .setCancelable(false)
                            .setTitle("Remove Course")
                            .setView(dialogView)
                            .show()

                        val student: Student? = studentAdapter.getSelected()
                        addedCousre = Course()


                        viewCourses = dialogView.findViewById(R.id.spinner)
                        val course_name = dialogView.findViewById<TextView>(R.id.dialog_course_name)

                        val options = ArrayList<String>()
                        student!!.getCourses().forEach { options.add(it.name) }


                        viewCourses.adapter = ArrayAdapter<String>(
                            this,
                            R.layout.support_simple_spinner_dropdown_item,
                            options
                        )

                        viewCourses.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    p0: AdapterView<*>?,
                                    p1: View?,
                                    p2: Int,
                                    p3: Long
                                ) {
                                    removedCousre = student.getCourses().get(p2)
                                    course_name.text = options[p2]
                                }

                                override fun onNothingSelected(p0: AdapterView<*>?) {
                                    Toast.makeText(
                                        this@StudentsActivity,
                                        "choose course",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        dialogView.findViewById<Button>(R.id.btnAdd).setText("remove")

                        dialogView.findViewById<Button>(R.id.btnAdd).setOnClickListener {

                            if (removedCousre != null) {
                                student.removeCourse(removedCousre!!)
                                studentAdapter.notifyDataSetChanged()
                            } else {
                                Toast.makeText(this, "choose course", Toast.LENGTH_SHORT).show()
                            }
                            alertDialog.dismiss()
                        }

                        dialogView.findViewById<Button>(R.id.btnCancel).setOnClickListener {
                            alertDialog.dismiss()
                        }

                    } else {
                        Toast.makeText(this, "There is no courses to remove", Toast.LENGTH_SHORT)
                            .show()
                    }

                } else {
                    Toast.makeText(this, "No selection", Toast.LENGTH_SHORT).show()
                }

            }

            R.id.menuViewCourses -> {
                if (studentAdapter.getSelected() != null && students.size > 0) {
                    val student = studentAdapter.getSelected()
                    val courses_names = ArrayList<String>()
                    student!!.getCourses().forEach { courses_names.add(it.name) }

                    if (courses_names.isNotEmpty()) {
                        val intent = Intent(this, RecyclerView_For_View_Strings::class.java)
                        intent.putExtra("coursesFromStudent", courses_names)
                        startActivity(intent)

                    } else {
                        Toast.makeText(this, "There is no courses", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(this, "No selection", Toast.LENGTH_SHORT).show()
                }

            }

            R.id.menuViewTeachers -> {
                if (studentAdapter.getSelected() != null && students.size > 0) {
                    val student = studentAdapter.getSelected()
                    val teachers_names = ArrayList<String>()
                    student!!.getTeachers().forEach { teachers_names.add(it.name) }
                    if (teachers_names.isNotEmpty()) {
                        val intent = Intent(this, RecyclerView_For_View_Strings::class.java)
                        intent.putExtra("teachersFromStudent", teachers_names)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "There is no teacher", Toast.LENGTH_SHORT)
                            .show()
                    }

                } else {
                    Toast.makeText(this, "No selection", Toast.LENGTH_SHORT).show()
                }
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.students_menu, menu)
        return true
    }

}