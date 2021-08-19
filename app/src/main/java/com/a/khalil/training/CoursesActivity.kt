package com.a.khalil.training

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a.khalil.training.Models.Course
import com.a.khalil.training.Models.Teacher
import com.a.khalil.training.StudentsActivity.Companion.courses
import com.a.khalil.training.StudentsActivity.Companion.teachers

class CoursesActivity : AppCompatActivity() {

    lateinit var alertDialog: AlertDialog
    lateinit var spinner: Spinner

    val coursesAdapter: CourseCustomAdapter by lazy {
        CourseCustomAdapter(
            this,
            StudentsActivity.courses
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_courses)


        val recyclerView = findViewById<RecyclerView>(R.id.coursesRecycle)
        recyclerView.adapter = coursesAdapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.course_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.createCourse -> {
                val dialogView =
                    LayoutInflater.from(this).inflate(R.layout.dialog_create_course, null)

                alertDialog = AlertDialog.Builder(this)
                    .setView(dialogView)
                    .setCancelable(false)
                    .setTitle("Add Course")
                    .show()

                val name = dialogView.findViewById<EditText>(R.id.dialog_course_name).text
                dialogView.findViewById<TextView>(R.id.dialogToChooseATeacher)
                    .setText("choose a teacher")

                spinner = dialogView.findViewById<Spinner>(R.id.spinner)

                val options = ArrayList<String>()
                (teachers).forEach { options.add(it.name) }

                spinner.adapter = ArrayAdapter<String>(
                    this,
                    R.layout.support_simple_spinner_dropdown_item,
                    options
                )
                var myTeacher: Teacher? = null
                spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        myTeacher = teachers[p2]
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }
                }
                dialogView.findViewById<Button>(R.id.btnCreate).setOnClickListener {
                    if (myTeacher != null && name.isNotEmpty()) {
                        val course = Course("$name", myTeacher!!)
                        if (!courses.contains(course)) {
                            courses.add(course)
                            coursesAdapter.notifyDataSetChanged()
                        }
                    } else {
                        Toast.makeText(this, "fill the fields", Toast.LENGTH_SHORT)
                            .show()
                    }
                    alertDialog.dismiss()
                }
                dialogView.findViewById<Button>(R.id.btnCancel).setOnClickListener {
                    alertDialog.dismiss()
                }

            }

            R.id.removeCourse -> {
                if (coursesAdapter.getSelected() != null && courses.size > 0) {
                    if (courses.contains(coursesAdapter.getSelected())) {
                        courses.remove(coursesAdapter.getSelected())
                        coursesAdapter.notifyDataSetChanged()
                    }

                } else {
                    Toast.makeText(this, "No Selection", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            R.id.updateCourse -> {
                if (coursesAdapter.getSelected() != null && courses.size > 0) {
                    val course = coursesAdapter.getSelected()

                    val dialogView =
                        LayoutInflater.from(this).inflate(R.layout.dialog_update_course, null)

                    alertDialog = AlertDialog.Builder(this)
                        .setView(dialogView)
                        .setTitle("Update Course")
                        .setCancelable(false)
                        .show()

                    val updatedCourseName =
                        dialogView.findViewById<EditText>(R.id.updatedCourseName)

                    updatedCourseName.setText(course!!.name)
                    dialogView.findViewById<Button>(R.id.btnUpdate).setOnClickListener {
                        if (updatedCourseName.text.isNotEmpty()) {
                            course.name = "${updatedCourseName.text}"
                            coursesAdapter.notifyDataSetChanged()
                        }
                        alertDialog.dismiss()
                    }

                    dialogView.findViewById<Button>(R.id.btnCancel).setOnClickListener {
                        alertDialog.dismiss()
                    }
                } else {
                    Toast.makeText(this, "No Selection", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            R.id.switchTeacher -> {
                if (coursesAdapter.getSelected() != null && courses.size > 0) {

                    val dialogView =
                        LayoutInflater.from(this).inflate(R.layout.dialog_add_course, null)

                    alertDialog = AlertDialog.Builder(this)
                        .setCancelable(false)
                        .setTitle("Switch Teacher")
                        .setView(dialogView)
                        .show()
                    val course = coursesAdapter.getSelected()
                    val chooseTeacher = ArrayList<String>()
                    teachers.forEach { chooseTeacher.add(it.name) }

                    spinner = dialogView.findViewById<Spinner>(R.id.spinner)
                    spinner.adapter =
                        ArrayAdapter<String>(
                            this,
                            R.layout.support_simple_spinner_dropdown_item,
                            chooseTeacher
                        )
                    var myTeacher: Teacher? = null
                    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            p0: AdapterView<*>?,
                            p1: View?,
                            p2: Int,
                            p3: Long
                        ) {
                            myTeacher = teachers[p2]
                            dialogView.findViewById<TextView>(R.id.dialog_course_name)
                                .setText(myTeacher!!.name)
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {
                            TODO("Not yet implemented")
                        }
                    }
                    dialogView.findViewById<Button>(R.id.btnAdd).setText("Switch")
                    dialogView.findViewById<Button>(R.id.btnAdd).setOnClickListener {
                        if (myTeacher != null) {
                            course!!.setTeacher(myTeacher!!)
                            coursesAdapter.notifyDataSetChanged()
                        } else {
                            Toast.makeText(this, "choose teacher", Toast.LENGTH_SHORT)
                                .show()
                        }
                        alertDialog.dismiss()
                    }

                    dialogView.findViewById<Button>(R.id.btnCancel).setOnClickListener {
                        alertDialog.dismiss()
                    }

                } else {
                    Toast.makeText(this, "No Selection", Toast.LENGTH_SHORT)
                        .show()
                }

            }

            R.id.viewStudents -> {
                if (coursesAdapter.getSelected() != null && courses.size > 0) {
                    val course = coursesAdapter.getSelected()
                    val students_names = ArrayList<String>()
                    course!!.getStudents().forEach { students_names.add(it.name) }

                    if (students_names.isNotEmpty()) {
                        val intent = Intent(this, RecyclerView_For_View_Strings::class.java)
                        intent.putExtra("studentsFromCourse", students_names)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "There is no students", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            }

            R.id.viewTeacher -> {
                if (coursesAdapter.getSelected() != null && courses.size > 0) {
                    val course = coursesAdapter.getSelected()
                    val teacher_name = course!!.getTeacher().name
                    if (teacher_name.isNotEmpty()) {
                        val intent = Intent(this, RecyclerView_For_View_Strings::class.java)
                        intent.putExtra("teacherFromCourse", teacher_name)
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(this, "There is no students", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            else -> return super.onOptionsItemSelected(item)
        }

        return true
    }


}