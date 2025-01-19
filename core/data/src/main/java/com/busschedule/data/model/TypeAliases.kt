package com.busschedule.data.model

import com.busschedule.domain.model.ApiResponse
import retrofit2.Response

typealias DefaultResponse<T> = Response<ApiResponse<T>>