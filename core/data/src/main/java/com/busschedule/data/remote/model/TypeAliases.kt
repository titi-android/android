package com.busschedule.data.model

import com.busschedule.data.network.ApiResult
import com.busschedule.domain.model.ApiResponse

typealias DefaultResponse<T> = ApiResult<ApiResponse<T>>