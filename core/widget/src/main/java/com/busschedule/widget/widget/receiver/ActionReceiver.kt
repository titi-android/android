package com.busschedule.widget.widget.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import com.busschedule.domain.repository.NotifyRepository
import com.busschedule.widget.widget.ScheduleGlanceWidget
import com.busschedule.widget.widget.ScheduleInfo
import com.busschedule.widget.widget.ScheduleStateDefinition
import com.busschedule.widget.widget.toBusArrivalData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class WidgetAction(val value: String, val text: String) {
    CLICK_NEXT_BUTTON("CLICK_NEXT_BUTTON", "다음"),
    CLICK_PREVIOUS_BUTTON("CLICK_PREVIOUS_BUTTON", "이전")
}


@AndroidEntryPoint
class ActionReceiver: BroadcastReceiver() {
    @Inject
    lateinit var repository: NotifyRepository

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onReceive(context: Context, intent: Intent?) {
        scope.launch {
            val manager = GlanceAppWidgetManager(context)
            val scheduleId = intent?.getStringExtra("scheduleId") ?: ""
            Log.d("daeyoung", "scheduleId: ${scheduleId}")
            val notifySchedule = repository.read(scheduleId)
            val maxSize = notifySchedule.busStopInfos.size
            var nextBusStopIndex = notifySchedule.busStopIndex
            Log.d("daeyoung", "maxSize: ${maxSize}, nextBusStopIndex: ${nextBusStopIndex}")

            if (intent?.action == WidgetAction.CLICK_NEXT_BUTTON.value && nextBusStopIndex < maxSize-1) {
                repository.updateBusStopIndex(scheduleId, ++nextBusStopIndex)
            }
            else if (intent?.action == WidgetAction.CLICK_PREVIOUS_BUTTON.value && nextBusStopIndex > 0) {
                repository.updateBusStopIndex(scheduleId, --nextBusStopIndex)
            }
            Log.d("daeyoung", "receiver: ${notifySchedule}")
            val glanceIds = manager.getGlanceIds(ScheduleGlanceWidget::class.java)
            val busStopInfo = notifySchedule.busStopInfos[nextBusStopIndex]
            val scheduleInfo = ScheduleInfo.Available(
                scheduleId = notifySchedule.scheduleId,
                busStop = busStopInfo.busStopName,
                scheduleName = notifySchedule.scheduleName,
                busArrivalInfo = busStopInfo.busInfos.map { it.toBusArrivalData() }
            )

            setWidgetState(context, glanceIds, scheduleInfo)
        }
    }

    private suspend fun setWidgetState(
        context: Context,
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