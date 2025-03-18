package com.busschedule.schedulelist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.busschedule.domain.repository.NotifyRepository
import com.busschedule.domain.usecase.schedule.DeleteScheduleUseCase
import com.busschedule.domain.usecase.schedule.PutScheduleAlarmUseCase
import com.busschedule.domain.usecase.schedule.ReadDaysSchedulesUseCase
import com.busschedule.domain.usecase.schedule.ReadTodaySchedulesUseCase
import com.busschedule.model.DayOfWeekUi
import com.busschedule.model.constant.DayOfWeek
import com.busschedule.schedulelist.model.ScheduleListUiState
import com.busschedule.schedulelist.model.ScheduleUI
import com.busschedule.schedulelist.model.asStateUI
import com.busschedule.widget.widget.worker.ScheduleWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ScheduleListViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val readTodaySchedulesUseCase: ReadTodaySchedulesUseCase,
    private val readDaysSchedulesUseCase: ReadDaysSchedulesUseCase,
    private val deleteScheduleUseCase: DeleteScheduleUseCase,
    private val putScheduleAlarmUseCase: PutScheduleAlarmUseCase,
    private val notifyRepository: NotifyRepository,
) : ViewModel() {

    private val _dayOfWeeks = MutableStateFlow(DayOfWeek.entries.map {
        DayOfWeekUi(
            dayOfWeek = it,
            init = LocalDate.now().dayOfWeek.name == it.enName
        )
    })
    val dayOfWeeks: StateFlow<List<DayOfWeekUi>> = _dayOfWeeks.asStateFlow()

    private val _schedules = MutableStateFlow(emptyList<ScheduleUI>())
    val schedules: StateFlow<List<ScheduleUI>> = _schedules.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val scheduleListUiState =
        combine(dayOfWeeks, schedules, isLoading) { dayOfWeeks, schedules, isLoading ->
            ScheduleListUiState(dayOfWeeks, schedules, isLoading)
        }

    private fun updateWidget() {
        WorkManager.getInstance(context).enqueue(
            OneTimeWorkRequestBuilder<ScheduleWorker>().build()
        )
    }

    fun fetchReadTodaySchedules(showToast: (String) -> Unit) {
        viewModelScope.launch {
            readTodaySchedulesUseCase().onSuccess { schedules ->
                _schedules.update { schedules.map { it.asStateUI() } }
                _isLoading.update { false }
            }.onFailure { showToast(it.message!!) }
        }
    }

    fun fetchReadDayOfWeekSchedules(dayOfWeek: String, changeLoadingState: () -> Unit = {}, showToast: (String) -> Unit) {
        viewModelScope.launch {
            readDaysSchedulesUseCase(dayOfWeek).onSuccess { schedules ->
                _schedules.update { schedules.map { it.asStateUI() } }
                changeLoadingState()
            }.onFailure { showToast(it.message!!) }
        }
    }

    fun fetchDeleteSchedules(scheduleId: Int, showToast: (String) -> Unit) {
        viewModelScope.launch {
            deleteScheduleUseCase(scheduleId).onSuccess {
                _schedules.update { schedule -> schedule.filter { it.id != scheduleId } }
                updateWidget()
            }.onFailure { showToast(it.message!!) }
        }
    }

    fun fetchPutScheduleAlarm(
        scheduleId: Int,
        updateAlarm: () -> Unit,
        showToast: (String) -> Unit,
    ) {
        viewModelScope.launch {
            putScheduleAlarmUseCase(scheduleId).onSuccess { updateAlarm() }.onFailure {
                showToast(it.message!!)
            }
        }
    }

    fun fetchUpdateBusStopStateOfNotify(scheduleId: String, scheduleName: String, index: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            if (notifyRepository.isExist(scheduleId)) {
                notifyRepository.updateBusStopIndex(scheduleId, index)
                return@launch
            }
            notifyRepository.insert(
                scheduleId = scheduleId,
                scheduleName = scheduleName,
                busStopIndex = index
            )
        }
    }

}