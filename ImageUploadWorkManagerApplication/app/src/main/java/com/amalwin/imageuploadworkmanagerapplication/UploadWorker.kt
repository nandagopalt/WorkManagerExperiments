package com.amalwin.imageuploadworkmanagerapplication

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.text.SimpleDateFormat
import java.util.*

class UploadWorker constructor(context: Context, params: WorkerParameters) :
    Worker(context, params) {
    override fun doWork(): Result {
        try {
            val input = inputData.getInt(INPUT_KEY, 1000)
            for (i in 1 until input) {
                Log.i(TAG, "Uploading $i")
            }
        } catch (e: Exception) {
            return Result.failure()
        }
        val completionTime = SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.getDefault()).format(Date())
        val outputData = Data.Builder().putString(INPUT_KEY, completionTime).build()
        return Result.success(outputData)
    }

    companion object {
        const val TAG = "MYTAG"
        const val INPUT_KEY = "INPUT_KEY"
    }
}