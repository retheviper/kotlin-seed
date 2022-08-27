package com.retheviper.kotlin.seed.types

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.reflect.KType
import kotlin.reflect.full.createType

internal enum class JavaTimeType(val type: KType) {
    LOCAL_DATE(LocalDate::class.createType()),
    LOCAL_TIME(LocalTime::class.createType()),
    LOCAL_DATE_TIME(LocalDateTime::class.createType()),
    LOCAL_DATE_TIME_NULLABLE(LocalDateTime::class.createType(nullable = true))
}