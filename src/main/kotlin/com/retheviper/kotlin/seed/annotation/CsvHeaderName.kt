package com.retheviper.kotlin.seed.annotation

@Target(AnnotationTarget.PROPERTY)
annotation class CsvHeaderName(val value: String)