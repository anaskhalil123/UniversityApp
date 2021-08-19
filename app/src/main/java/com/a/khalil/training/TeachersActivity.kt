package com.a.khalil.training

import android.annotation.SuppressLint
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

class TeachersActivity : AppCompatActivity() {

    val TAG = "TeachersActivity"
    lateinit var alertDialog: AlertDialog
    lateinit var spinner: Spinner

    val teachersAdapter: TeacherCustomAdapter by lazy {
        TeacherCustomAdapter(
            this,
            StudentsActivity.teachers
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teachers)

        val recyclerView = findViewById<RecyclerView>(R.id.teachersRecycle)
        recyclerView.adapter = teachersAdapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.teacher_menu, menu)
        return true
    }


    @SuppressLint("CutPasteId")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.createTeacher -> {
                val dialogView =
                    LayoutInflater.from(this).inflate(R.layout.dialog_create_teacher, null)

                alertDialog = AlertDialog.Builder(this)
                    .setView(dialogView)
                    .setCancelable(false)
                    .setTitle("Create Teacher")
                    .show()

                val name = dialogView.findViewById<EditText>(R.id.dialog_teacher_name).text
                val specialty =
                    dialogView.findViewById<EditText>(R.id.dialog_teacher_specialty).text



                dialogView.findViewById<Button>(R.id.btnCreate).setOnClickListener {
                    if (name.isNotEmpty() && specialty.isNotEmpty()) {
                        val teacher = Teacher("$name", "$specialty")
                        if (!StudentsActivity.teachers.contains(teacher)) {
                            StudentsActivity.teachers.add(teacher)
                            teachersAdapter.notifyDataSetChanged()
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

            R.id.removeTeacher -> {
                if (teachersAdapter.getSelected() != null && StudentsActivity.teachers.size > 0) {
                    if (StudentsActivity.teachers.contains(teachersAdapter.getSelected())) {
                        StudentsActivity.teachers.remove(teachersAdapter.getSelected())
                        teachersAdapter.notifyDataSetChanged()
                    }

                } else {
                    Toast.makeText(this, "No Selection", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            R.id.updateTeacher -> {
                if (teachersAdapter.getSelected() != null && StudentsActivity.teachers.size > 0) {
                    val teacher = teachersAdapter.getSelected()

                    val dialogView =
                        LayoutInflater.from(this).inflate(R.layout.dialog_create_teacher, null)

                    alertDialog = AlertDialog.Builder(this)
                        .setView(dialogView)
                        .setTitle("Update Teacher")
                        .setCancelable(false)
                        .show()

                    val updatedTeacherName =
                        dialogView.findViewById<EditText>(R.id.dialog_teacher_name)

                    updatedTeacherName.setText(teacher!!.name)


                    val updateTeacherSpecialty =
                        dialogView.findViewById<EditText>(R.id.dialog_teacher_specialty)
                    updateTeacherSpecialty.setText(teacher.specialty)

                    dialogView.findViewById<Button>(R.id.btnCreate).setText("Update")

                    dialogView.findViewById<Button>(R.id.btnCreate).setOnClickListener {
                        if (updatedTeacherName.text.isNotEmpty() && updateTeacherSpecialty.text.isNotEmpty()) {
                            teacher.name = "${updatedTeacherName.text}"
                            teacher.specialty = "${updateTeacherSpecialty.text}"
                            teachersAdapter.notifyDataSetChanged()
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

            R.id.placementCourse -> {
                if (teachersAdapter.getSelected() != null && StudentsActivity.teachers.size > 0) {
                    val teacher = teachersAdapter.getSelected()

                    val dialogView =
                        LayoutInflater.from(this).inflate(R.layout.dialog_add_course, null)

                    alertDialog = AlertDialog.Builder(this)
                        .setCancelable(false)
                        .setTitle("Switch Teacher")
                        .setView(dialogView)
                        .show()


                    val options = ArrayList<String>()
                    val coursesCouldPlacement = ArrayList<Course>()
                    for (course in StudentsActivity.courses) {
                        coursesCouldPlacement.add(course)
                    }
                    coursesCouldPlacement.removeAll(teacher!!.getCourses())
                    coursesCouldPlacement.forEach { options.add(it.name) }

                    spinner = dialogView.findViewById<Spinner>(R.id.spinner)
                    spinner.adapter =
                        ArrayAdapter<String>(
                            this,
                            R.layout.support_simple_spinner_dropdown_item,
                            options
                        )

                    var myCourse: Course? = null
                    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            p0: AdapterView<*>?,
                            p1: View?,
                            p2: Int,
                            p3: Long
                        ) {
                            myCourse = coursesCouldPlacement[p2]
                            dialogView.findViewById<TextView>(R.id.dialog_course_name)
                                .setText(myCourse!!.name)
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {
                            TODO("Not yet implemented")
                        }
                    }
                    dialogView.findViewById<Button>(R.id.btnAdd).setText("Placement")
                    dialogView.findViewById<Button>(R.id.btnAdd).setOnClickListener {
                        if (myCourse != null) {
                            teacher.placementCourse(myCourse!!)
                            teachersAdapter.notifyDataSetChanged()
                        } else {
                            Toast.makeText(this, "choose course", Toast.LENGTH_SHORT)
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
                if (teachersAdapter.getSelected() != null && StudentsActivity.teachers.size > 0) {
                    val teacher = teachersAdapter.getSelected()
                    val students_names = ArrayList<String>()
                    teacher!!.getStudents().forEach { students_names.add(it.name) }

                    if (students_names.isNotEmpty()) {
                        val intent = Intent(this, RecyclerView_For_View_Strings::class.java)
                        intent.putExtra("studentsFromTeacher", students_names)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "There is no students", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            }

            R.id.viewCourses -> {
                if (teachersAdapter.getSelected() != null && StudentsActivity.teachers.size > 0) {
                    val teacher = teachersAdapter.getSelected()
                    val courses_names = ArrayList<String>()
                    teacher!!.getCourses().forEach { courses_names.add(it.name) }

                    if (courses_names.isNotEmpty()) {
                        val intent = Intent(this, RecyclerView_For_View_Strings::class.java)
                        intent.putExtra("coursesFromTeacher", courses_names)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "There is no courses", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            }

            else -> return super.onOptionsItemSelected(item)
        }

        return true
    }


}