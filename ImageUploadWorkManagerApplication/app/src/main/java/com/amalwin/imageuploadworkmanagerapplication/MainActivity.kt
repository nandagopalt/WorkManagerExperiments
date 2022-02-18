package com.amalwin.imageuploadworkmanagerapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.amalwin.imageuploadworkmanagerapplication.UploadWorker.Companion.INPUT_KEY
import com.amalwin.imageuploadworkmanagerapplication.UploadWorker.Companion.TAG
import com.amalwin.imageuploadworkmanagerapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startButton.setOnClickListener {
            startOneTimeWorkRequest()
        }
    }

    private fun startOneTimeWorkRequest() {
        val inputData = Data.Builder().putInt(INPUT_KEY, 50000).build()
        //val constraints = Constraints.Builder().setRequiresCharging(true).build()
        val workManagerInstance = WorkManager.getInstance(applicationContext)
        val uploadWorkRequest =
            //OneTimeWorkRequest.Builder(UploadWorker::class.java).setInputData(inputData).setConstraints(constraints).build()
        OneTimeWorkRequest.Builder(UploadWorker::class.java)
            .setInputData(inputData)
            .build()

        workManagerInstance.enqueue(uploadWorkRequest)
        workManagerInstance.getWorkInfoByIdLiveData(uploadWorkRequest.id).observe(this, Observer {
            binding.tvStatus.text = it.state.name
            if(it.state.isFinished) {
                val completionTime = it.outputData.getString(INPUT_KEY)
                Log.i(TAG, completionTime.toString())
            }
        })
    }
}