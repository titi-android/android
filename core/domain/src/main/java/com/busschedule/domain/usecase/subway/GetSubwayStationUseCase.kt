package com.busschedule.domain.usecase.subway

import com.busschedule.common.runCatchingIgnoreCancelled
import com.busschedule.domain.repository.SubwayRepository
import javax.inject.Inject

class GetSubwayStationUseCase @Inject constructor(private val subwayRepository: SubwayRepository) {
    suspend operator fun invoke(lineName: String) = runCatchingIgnoreCancelled {
        subwayRepository.getSubwayStation(lineName)
    }
}