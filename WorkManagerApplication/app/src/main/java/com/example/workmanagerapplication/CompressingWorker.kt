package com.example.workmanagerapplication

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.text.SimpleDateFormat
import java.util.*

class CompressingWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {
        try {
            for (i in 1..3000)
                Log.i("MYTAG", "Compressing $i")
            return Result.success()
        } catch (exception: Exception) {
            return Result.failure()
        }
    }
}