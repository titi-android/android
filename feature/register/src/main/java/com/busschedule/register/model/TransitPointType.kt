package com.busschedule.register.model

enum class TransitPointType(val value: String){
    START("출발"), END("도착"), TRANSFER("환승");

    companion object {
        fun isTransfer(c: TransitPointType) =
            c == TransitPointType.TRANSFER

    }
}