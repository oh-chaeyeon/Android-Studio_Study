package com.example.myapplication5

import androidx.room.*

@Entity(tableName = "student_table")
data class Student(
    @PrimaryKey @ColumnInfo(name = "student_id") val id: Int,
    val name: String
)

@Entity(tableName = "class_table")
data class ClassInfo (
    @PrimaryKey val id: Int,
    val name: String,
    val day_time: String,
    val room: String,
    val teacher_id: Int
)

@Entity(tableName = "enrollment",
    primaryKeys = ["sid", "cid"],
    foreignKeys = [
        ForeignKey(entity = Student::class, parentColumns = ["student_id"], childColumns = ["sid"], onDelete = ForeignKey.CASCADE ),
        ForeignKey(entity = ClassInfo::class, parentColumns = ["id"], childColumns = ["cid"])
    ],
    indices = [Index(value=["sid", "cid"])]
)
data class Enrollment (
    val sid: Int,
    val cid: Int,
    val grade: String? = null
)

@Entity(tableName = "teacher_table")
data class Teacher (
    @PrimaryKey val id: Int,
    val name: String,
    val position: String
)
