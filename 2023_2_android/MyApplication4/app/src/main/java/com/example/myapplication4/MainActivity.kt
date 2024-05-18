package com.example.myapplication4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    lateinit var dao:MyDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //dao는 데이터베이스 작업을 수행하기 위한 DAO(Data Access Object) 인터페이스
        dao = MyDatabase.getDatabase(this).getMyDao()

        //앱의 초기 설정 및 데이터베이스 초기화 작업 수행
        CoroutineScope(Dispatchers.IO).launch {
            dao.inserStudent(Student(1, "james"))
            dao.inserStudent(Student(2,"top"))
            dao.insertEnrollment(Enrollment(1, 1))
            dao.insertEnrollment(Enrollment(1, 2))
        }

        val textStudentList = findViewById<TextView>(R.id.text_student_list)
        val allStudent = dao.getAllStudents()
        allStudent.observe(this){
            val str = StringBuilder().apply{
                for((id,name) in it){
                    append(id)
                    append("-")
                    append(name)
                    append("\n")
                }
            }.toString()
            textStudentList.text = str
        }

        //Add Student 버튼 클릭 리스너 : 학생 정보 추가
        findViewById<Button>(R.id.add_student).setOnClickListener{
            val id = findViewById<EditText>(R.id.edit_student_id).text.toString()
            val name = findViewById<EditText>(R.id.edit_student_name).text.toString()
            CoroutineScope(Dispatchers.IO).launch{
                dao.inserStudent(Student(id.toInt(), name))
            }
        }
        // Delete Student 버튼 클릭 리스너 : 학생 정보 삭제
        findViewById<Button>(R.id.del_student).setOnClickListener{
            val id = findViewById<EditText>(R.id.edit_student_id).text.toString()
            val name = findViewById<EditText>(R.id.edit_student_name).text.toString()
            CoroutineScope(Dispatchers.IO).launch{
                dao.deleteStudent(Student(id.toInt(), name))
            }
        }

        // Enrollment 버튼 클릭 리스너 : 과목 등록
        //"Enrollment" 버튼을 누르면 해당 학생이 1번 과목을 수강하는 것으로 가정하고 Enrollment 테이블에 추가
        findViewById<Button>(R.id.enroll).setOnClickListener {
            val studentId = findViewById<EditText>(R.id.edit_student_id).text.toString().toInt()
            val courseId = 1 // 과목 ID가 1인 과목을 추가한다고 가정

            // Enrollment 테이블에 추가
            CoroutineScope(Dispatchers.IO).launch {
                dao.insertEnrollment(Enrollment(studentId, courseId))
            }
        }

        //학생 정보 조회 : 학생 ID를 입력하고 "Query Student" 버튼을 누르면 해당 학생과 수강한 과목 정보를 조회
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

    }
}









