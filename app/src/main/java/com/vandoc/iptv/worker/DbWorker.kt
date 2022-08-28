package com.vandoc.iptv.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.vandoc.iptv.base.Resource
import com.vandoc.iptv.data.IPTVRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.toList
import timber.log.Timber

/**
 * @author Ichvandi
 * Created on 28/08/2022 at 11:02.
 */
@HiltWorker
class DbWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: IPTVRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        Timber.d("DBWorker Started")
        val results = coroutineScope {
            val categoryResult = async(Dispatchers.IO) { repository.getCategories().toList() }
            val countryResult = async(Dispatchers.IO) { repository.getCountries().toList() }
            val languageResult = async(Dispatchers.IO) { repository.getLanguages().toList() }
            val regionResult = async(Dispatchers.IO) { repository.getRegions().toList() }
            val subdivisionResult = async(Dispatchers.IO) { repository.getSubdivisions().toList() }
            return@coroutineScope listOf(
                categoryResult,
                countryResult,
                languageResult,
                regionResult,
                subdivisionResult
            ).awaitAll().flatten()
        }

        if (results.any { it is Resource.Error }) {
            Timber.d("DBWorker retried")
            return Result.retry()
        }

        Timber.d("DBWorker Finished")
        return Result.success()
    }

}