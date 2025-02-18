package com.example.myapplication5

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {
    lateinit var dao: MyDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dao = MyDatabase.getDatabase(this).getMyDao()
        CoroutineScope(Dispatchers.IO).launch {
            with(dao) {
                insertStudent(Student(1, "james"))
                insertStudent(Student(2, "john"))
                insertClass(ClassInfo(1, "c-lang", "Mon 9:00", "E301", 1))
                insertClass(ClassInfo(2, "android prog", "Tue 9:00", "E302", 1))
                insertEnrollment(Enrollment(1, 1))
                insertEnrollment(Enrollment(1, 2))
            }
        }

        val textStudentList = findViewById<TextView>(R.id.text_student_list)
        val allStudents = dao.getAllStudents()
        allStudents.observe(this) {
            val str = StringBuilder().apply {
                for ((id, name) in it) {
                    append(id)
                    append("-")
                    append(name)
                    append("\n")
                }
            }.toString()
            textStudentList.text = str
        }

        val queryStudent = findViewById<Button>(R.id.query_student)
        val editStudentId = findViewById<EditText>(R.id.edit_student_id)
        val textQueryStudent = findViewById<TextView>(R.id.text_query_student)
        queryStudent.setOnClickListener {
            val id = editStudentId.text.toString().toInt()
            CoroutineScope(Dispatchers.IO).launch {
                val results = dao.getStudentsWithEnrollment(id)
                if (results.isNotEmpty()) {
                    val str = StringBuilder().apply {
                        append(results[0].student.id)
                        append("-")
                        append(results[0].student.name)
                        append(":")
                        for (c in results[0].enrollments) {
                            append(c.cid)
                            val cls_result = dao.getClassInfo(c.cid)
                            if (cls_result.isNotEmpty())
                                append("(${cls_result[0].name})")
                            append(",")
                        }
                    }
                    withContext(Dispatchers.Main) {
                        textQueryStudent.text = str
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        textQueryStudent.text = ""
                    }
                }
            }
        }

        val addStudent = findViewById<Button>(R.id.add_student)
        val editStudentName = findViewById<EditText>(R.id.edit_student_name)
        addStudent.setOnClickListener {
            val id = editStudentId.text.toString().toInt()
            val name = editStudentName.text.toString()
            if (id > 0 && name.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    dao.insertStudent(Student(id, name))
                }
            }
        }

        val deleteStudent = findViewById<Button>(R.id.del_student)
        deleteStudent.setOnClickListener {
            val id = findViewById<EditText>(R.id.edit_student_id).text.toString().toInt()
            val name = editStudentName.text.toString()
            CoroutineScope(Dispatchers.IO).launch{
                dao.deleteStudent(Student(id, name))
            }
        }

        val enrollment = findViewById<Button>(R.id.enroll)
        enrollment.setOnClickListener{
            val student_id = findViewById<EditText>(R.id.edit_student_id).text.toString().toInt()
            CoroutineScope(Dispatchers.IO).launch {
                dao.insertEnrollment(Enrollment(student_id, 1))
            }
        }
    }
}