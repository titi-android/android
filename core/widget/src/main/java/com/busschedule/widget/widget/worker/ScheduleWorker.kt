package com.busschedule.widget.widget.worker

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.busschedule.domain.repository.NotifyRepository
import com.busschedule.domain.usecase.schedule.ReadNowScheduleUseCase
import com.busschedule.model.Schedule
import com.busschedule.model.exception.AccessTokenExpiredException
import com.busschedule.model.exception.AccessTokenIllegalArgumentException
import com.busschedule.widget.widget.ScheduleGlanceWidget
import com.busschedule.widget.widget.ScheduleInfo
import com.busschedule.widget.widget.ScheduleStateDefinition
import com.busschedule.widget.widget.toWidgetState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.Duration

@HiltWorker
class ScheduleWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val readNowScheduleUseCase: ReadNowScheduleUseCase,
    private val notifyRepository: NotifyRepository,
) : CoroutineWorker(context, workerParameters) {
    companion object {
        private val uniqueWorkName = ScheduleWorker::class.java.simpleName

        internal fun enqueue(context: Context, force: Boolean = false) {
            val manager = WorkManager.getInstance(context)
            val requestBuilder =
                PeriodicWorkRequestBuilder<ScheduleWorker>(Duration.ofHours(1))
            val constrains = Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build()

            // Replace any enqueued work and expedite the request
            val workPolicy = if (force) {
                ExistingPeriodicWorkPolicy.UPDATE
            } else {
                ExistingPeriodicWorkPolicy.KEEP
            }

            manager.enqueueUniquePeriodicWork(
                uniqueWorkName,
                workPolicy,
                requestBuilder
                    .setConstraints(constrains)
                    .build(),
            )
        }

        internal fun cancel(context: Context) {
            WorkManager.getInstance(context).cancelUniqueWork(uniqueWorkName)
        }
    }

    override suspend fun doWork(): Result {
        val manager = GlanceAppWidgetManager(context)
        val glanceIds = manager.getGlanceIds(ScheduleGlanceWidget::class.java)

        setWidgetState(glanceIds, ScheduleInfo.Loading)
        var resultState = Result.success()
        readNowScheduleUseCase().onSuccess { schedule ->
            val index = schedule?.id?.let {
                if (notifyRepository.isExist(it.toString())) {
                    notifyRepository.readBusStopIndex(it.toString())
                } else {
                    notifyRepository.insert(
                        scheduleId = schedule.id.toString(),
                        scheduleName = schedule.name,
                        busStopIndex = 0,
                        /* busStopInfos = schedule.busStopInfos */
                    )
                    0
                }
            }

            val scheduleInfo = getSuccessWidgetState(schedule = schedule, index = index)
            setWidgetState(glanceIds, scheduleInfo)
            resultState = Result.success()
        }.onFailure {
            val scheduleInfo = getExceptionWidgetState(it)
            setWidgetState(glanceIds, scheduleInfo)
            resultState = Result.failure()
        }
        return resultState
    }

    private fun getExceptionWidgetState(e: Throwable): ScheduleInfo.Unavailable {
        return when (e) {
            is AccessTokenExpiredException -> ScheduleInfo.Unavailable.JWT401
            is AccessTokenIllegalArgumentException -> ScheduleInfo.Unavailable.JWT401
            else -> ScheduleInfo.Unavailable.UnExpected
        }
    }

    private fun getSuccessWidgetState(schedule: Schedule?, index: Int? = 0): ScheduleInfo {
        if (schedule == null) {
            return ScheduleInfo.Unavailable.DataIsNull
        }
        return schedule.toWidgetState(index!!)
    }


    private suspend fun setWidgetState(
        glanceIds: List<GlanceId>,
        newState: ScheduleInfo,
    ) {
        glanceIds.forEach { glanceId ->
            updateAppWidgetState(
                context = context,
                glanceId = glanceId,
                definition = ScheduleStateDefinition,
                updateState = { newState },
            )
        }
        ScheduleGlanceWidget().updateAll(context)
    }
}