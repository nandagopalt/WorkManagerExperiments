package com.example.workmanagerapplication

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.text.SimpleDateFormat
import java.util.*

class UploadWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    companion object {
        const val KEY_WORKER = "key_worker"
    }
    override fun doWork(): Result {
        try {
            val input = inputData
            val receivedInput = input.getInt(MainActivity.KEY_INPUT, 1)
            for (i in 1..receivedInput)
                Log.i("MYTAG", "Uploading $i")

            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.getDefault())
            val date = simpleDateFormat.format(Date())
            val outputData = Data.Builder().putString(KEY_WORKER,date).build()
            return Result.success(outputData)
        } catch (exception: Exception) {
            return Result.failure()
        }
    }
}