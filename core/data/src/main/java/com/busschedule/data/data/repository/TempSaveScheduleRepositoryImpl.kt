package com.busschedule.data.data.repository

import com.busschedule.data.local.room.dao.RegisterScheduleDao
import com.busschedule.data.local.room.model.ScheduleRegisterEntity
import com.busschedule.data.local.room.model.toModel
import com.busschedule.domain.repository.TempSaveScheduleRepository
import com.busschedule.model.BusStop
import com.busschedule.model.BusRegister
import com.busschedule.model.ScheduleRegister
import javax.inject.Inject

class TempSaveScheduleRepositoryImpl @Inject constructor(
    private val registerScheduleDao: RegisterScheduleDao
) : TempSaveScheduleRepository {
    override suspend fun insert(
        name: String,
        dayOfWeeks: List<String>,
        startTime: String,
        endTime: String,
        isNotify: Boolean,
        busRegisters: List<BusRegister>,
        arriveBusStop: BusStop,
    ) {
        val entity = ScheduleRegisterEntity(
            name = name,
            dayOfWeeks = dayOfWeeks,
            startTime = startTime,
            endTime = endTime,
            isNotify = isNotify,
            busRegisters = busRegisters,
            arriveBusStop = arriveBusStop
        )
        registerScheduleDao.insert(entity)
    }

    override suspend fun read(): ScheduleRegister =
        registerScheduleDao.readAll()[0].toModel()


    override suspend fun delete(): Boolean = try {
        registerScheduleDao.delete()
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }

    override suspend fun isExist(): Boolean =
        registerScheduleDao.isExist()

}