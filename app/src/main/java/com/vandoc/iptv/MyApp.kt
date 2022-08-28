package com.vandoc.iptv

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.vandoc.iptv.worker.DbWorker
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * @author Ichvandi
 * Created on 06/06/2022 at 20:58.
 */
@HiltAndroidApp
class MyApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initDBWorker(applicationContext)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initDBWorker(context: Context) {
        val isFirstTime = sharedPreferences.getBoolean("isFirstTime", true)
        if (!isFirstTime) return

        val myWork = OneTimeWorkRequestBuilder<DbWorker>()
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()

        WorkManager.getInstance(context)
            .enqueue(myWork)

        sharedPreferences.edit()
            .putBoolean("isFirstTime", false)
            .apply()
    }

}