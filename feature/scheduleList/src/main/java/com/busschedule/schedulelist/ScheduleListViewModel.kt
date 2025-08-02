package com.busschedule.schedulelist

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.busschedule.domain.repository.NotifyRepository
import com.busschedule.domain.repository.TempSaveScheduleRepository
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
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    private val tempSaveScheduleRepository: TempSaveScheduleRepository,
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
        viewModelScope.launch(Dispatchers.IO) {
            readTodaySchedulesUseCase().onSuccess { schedules ->
                _schedules.update { schedules.map { it.asStateUI() } }
                _isLoading.update { false }
            }.onFailure {
                // TODO("오류 해결해야 함, 테스트하기 위해 임시로 수정")
                Log.d("daeyoung", "error: ${it.message ?: ""}")
                _isLoading.update { false }
                //showToast(it.message!!)
            }
        }
    }

    fun fetchReadDayOfWeekSchedules(
        dayOfWeek: String,
        changeLoadingState: () -> Unit = {},
        showToast: (String) -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            readDaysSchedulesUseCase(dayOfWeek).onSuccess { schedules ->
                _schedules.update { schedules.map { it.asStateUI() } }
                changeLoadingState()
            }.onFailure { showToast(it.message!!) }
        }
    }

    fun fetchDeleteSchedules(scheduleId: Int, showToast: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
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
        viewModelScope.launch(Dispatchers.IO) {
            putScheduleAlarmUseCase(scheduleId).onSuccess { updateAlarm() }.onFailure {
                showToast(it.message!!)
            }
        }
    }

    fun fetchUpdateBusStopStateOfNotify(scheduleId: String, scheduleName: String, index: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val isScheduleIdExist = async { notifyRepository.isExist(scheduleId) }.await()

            if (isScheduleIdExist) {
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

    fun fetchIsExistTempSchedule(navigateToRegister: () -> Unit, showBringTempScheduleDialog: () -> Unit, ) {
        viewModelScope.launch(Dispatchers.IO) {
            val action = if (tempSaveScheduleRepository.isExist()) {
                showBringTempScheduleDialog
            } else { navigateToRegister }

            withContext(Dispatchers.Main) { action() }
        }
    }

    fun fetchDeleteTempSchedule(navigateToRegister: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val isSuccess = async { tempSaveScheduleRepository.delete() }.await()
            if (isSuccess) {
                withContext(Dispatchers.Main) { navigateToRegister() }
            }
        }
    }

}