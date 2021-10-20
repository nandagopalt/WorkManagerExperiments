package com.example.workmanagerapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.work.*
import com.example.workmanagerapplication.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    companion object {
        const val KEY_INPUT = "key_input"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val inputData = Data.Builder().putInt(KEY_INPUT, 5000).build()
        val inputConstraints = Constraints.Builder().
                                setRequiresCharging(true).
                                setRequiredNetworkType(NetworkType.CONNECTED).
                                build()

        val uploadRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java).
                                    setInputData(inputData).
                                    setConstraints(inputConstraints).build()
        val filterRequest = OneTimeWorkRequest.Builder(FilteringWorker::class.java).build()
        val compressingRequest = OneTimeWorkRequest.Builder(CompressingWorker::class.java).build()

        val oneTimeRequestList = mutableListOf<OneTimeWorkRequest>()
        oneTimeRequestList.add(filterRequest)
        oneTimeRequestList.add(compressingRequest)

        //val uploadRequest = PeriodicWorkRequest.Builder(UploadWorker::class.java, 16, TimeUnit.MINUTES).
        //setInputData(inputData).
        //setConstraints(inputConstraints).build()
        val workManagerInstance = WorkManager.getInstance(applicationContext)
        /*workManagerInstance.beginWith(filterRequest).
            then(compressingRequest).
            then(uploadRequest).
            enqueue()*/
        workManagerInstance.beginWith(oneTimeRequestList).
                            then(uploadRequest).
                            enqueue()
        workManagerInstance.getWorkInfoByIdLiveData(uploadRequest.id).observe(this, Observer {
            Log.i("MYTAG", it.state.name)
            binding.text.text = it.state.name
            if(it.state.isFinished) {
               val outputData = it.outputData
               val result = outputData.getString(UploadWorker.KEY_WORKER)
               Log.i("MYTAG", "Work Completed at " + result.toString())
            }
        })

    }
}