package com.busschedule.widget

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.busschedule.widget.worker.ScheduleWorker

class ScheduleWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget = ScheduleGlanceWidget()
    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        ScheduleWorker.enqueue(context)
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        ScheduleWorker.cancel(context)
    }
}