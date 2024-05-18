package com.example.myapplication7

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class MyWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val inputData = inputData.getString("username")
        if (inputData.isNullOrBlank()) {
            return Result.failure()
        }

        val repository = MyRepository(applicationContext)
        try {
            repository.refreshData(inputData)
        } catch (e: Exception) {
            return Result.retry()
        }
        return Result.success()
    }

    companion object {
        const val name = "com.example.repository_pattern.MyWorker"
    }
}
