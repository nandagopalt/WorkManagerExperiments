package com.example.workmanagerapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager

class MainActivity : AppCompatActivity() {
    companion object {
        const val KEY_INPUT = "key_input"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val uploadRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java).build()
        val workManagerInstance = WorkManager.getInstance(applicationContext)
        workManagerInstance.enqueue(uploadRequest)
        workManagerInstance.getWorkInfoByIdLiveData(uploadRequest.id).observe(this, Observer {
            Log.i("MYTAG", it.state.name)
            if(it.state.isFinished) {
               val outputData = it.outputData
               val result = outputData.getString(UploadWorker.KEY_WORKER)
               Log.i("MYTAG", "Work Completed at " + result.toString())
            }
        })

    }
}