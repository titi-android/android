package com.busschedule.schedulelist

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main() = runBlocking<Unit> {
    println("[${Thread.currentThread().name}] runBlocking 블록 실행")
    withContext(Dispatchers.IO) {
        println("[${Thread.currentThread().name}] withContext 블록 실행")
    }
    async(Dispatchers.IO) {
        println("[${Thread.currentThread().name}] async 블록 실행")
    }.await()
    // 결과:
}