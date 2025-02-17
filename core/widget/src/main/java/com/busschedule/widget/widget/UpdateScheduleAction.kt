package com.busschedule.widget.widget

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.busschedule.widget.widget.worker.ScheduleWorker

class UpdateScheduleAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        WorkManager.getInstance(context).enqueue(
            OneTimeWorkRequestBuilder<ScheduleWorker>().build()
        )
    }
}
