package com.retheviper.kotlin.seed.plant

import com.github.doyaaaaaken.kotlincsv.dsl.context.CsvWriterContext
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import com.retheviper.kotlin.seed.annotation.CsvHeaderName
import com.retheviper.kotlin.seed.context.SeedParserContext
import com.retheviper.kotlin.seed.types.JavaTimeType
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.reflect.full.memberProperties

class Planter<T>(private val context: SeedParserContext) {

    private val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(context.timeFormat)

    private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(context.dateFormat)

    private val dateTimeFormatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("${context.dateFormat}${context.dateTimeSeparator}${context.timeFormat}")

    fun plant(
        seeds: List<T>,
        targetFile: File,
        append: Boolean = false,
        csvWriterContext: CsvWriterContext.() -> Unit = {}
    ) {
        val headers = seeds.first()!!::class.memberProperties.map { property ->
            val name = property.annotations.filterIsInstance<CsvHeaderName>().firstOrNull()
            name?.value?.apply { if (context.trimWhiteSpace) trim() } ?: property.name
        }

        val rows = seeds.mapNotNull { seed ->
            seed!!::class.memberProperties.map { field ->
                field.call(seed)?.let {
                    when (field.returnType) {
                        JavaTimeType.LOCAL_DATE.type -> dateFormatter.format(it as LocalDate)
                        JavaTimeType.LOCAL_TIME.type -> timeFormatter.format(it as LocalTime)
                        JavaTimeType.LOCAL_DATE_TIME.type, JavaTimeType.LOCAL_DATE_TIME_NULLABLE.type -> dateTimeFormatter.format(it as LocalDateTime)
                        else -> it.toString().apply { if (context.trimWhiteSpace) trim() }
                    }
                } ?: ""
            }
        }

        csvWriter(csvWriterContext).writeAll(
            rows = listOf(headers) + rows,
            targetFile = targetFile,
            append = append
        )
    }
}