package com.example.myapplication5

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [Student::class, ClassInfo::class, Enrollment::class, Teacher::class],
    exportSchema = false, version=1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun getMyDao() : MyDao

    companion object {
        private var INSTANCE: MyDatabase? = null
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {

            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE student_table ADD COLUMN last_update INTEGER")
            }
        }
        fun getDatabase(context: Context) : MyDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context, MyDatabase::class.java, "school_database")
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build()
            }
            return INSTANCE as MyDatabase
        }
    }

    /*companion object {
        fun getDatabase(context: Context) : MyDatabase {
            val builder = Room.databaseBuilder(context, MyDatabase::class.java,"school_database")
            val db = builder.build()
            return db
        }
    }*/
}