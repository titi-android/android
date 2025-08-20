package com.busschedule.domain.usecase.subway

import com.busschedule.common.runCatchingIgnoreCancelled
import com.busschedule.domain.repository.SubwayRepository
import javax.inject.Inject

class GetSubwayStationLineInfoUseCase @Inject constructor(
    private val subwayRepository: SubwayRepository,
) {
    suspend operator fun invoke(stName: String) =
        runCatchingIgnoreCancelled { subwayRepository.getSubwayStationLineInfo(stName) }

}