package com.busschedule.lock.service

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LockServiceManager @Inject constructor(
    @ApplicationContext val applicationContext: Context
): BaseForegroundServiceManager<LockService>(
    context = applicationContext,
    targetClass = LockService::class.java,
)