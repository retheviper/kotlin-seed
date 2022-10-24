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
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

class Planter<T : Any>(private val context: SeedParserContext) {

    private val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(context.timeFormat)

    private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(context.dateFormat)

    private val dateTimeFormatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("${context.dateFormat}${context.dateTimeSeparator}${context.timeFormat}")

    fun plant(
        seeds: List<T>, targetFile: File, append: Boolean = false, csvWriterContext: CsvWriterContext.() -> Unit = {}
    ) {
        val fieldNames = seeds.first()::class.primaryConstructor!!.parameters.mapNotNull { it.name }
        val headers = getHeaders(seeds, fieldNames)
        val rows = getRows(seeds, fieldNames)

        csvWriter(csvWriterContext).writeAll(
            rows = headers + rows, targetFile = targetFile, append = append
        )
    }

    private fun getHeaders(
        seeds: List<T>, fieldNames: List<String>
    ): List<List<String>> {
        val header = fieldNames.mapNotNull { name ->
            seeds.first()::class.memberProperties.find { it.name == name }?.let { property ->
                val headerName = property.annotations.filterIsInstance<CsvHeaderName>().firstOrNull()
                headerName?.value?.trimIfNecessary() ?: property.name
            }
        }
        return listOf(header)
    }

    private fun getRows(
        seeds: List<T>, fieldNames: List<String>
    ): List<List<String>> {
        return seeds.map { seed ->
            fieldNames.mapNotNull { name ->
                seed::class.memberProperties.find { it.name == name }?.let { field ->
                    val value = field.call(seed)
                    if (value != null) convertToString(field, value) else ""
                }
            }
        }
    }

    private fun convertToString(field: KProperty1<out T, *>, value: Any): String {
        return when (field.returnType) {
            JavaTimeType.LOCAL_DATE.type -> dateFormatter.format(value as LocalDate)

            JavaTimeType.LOCAL_TIME.type -> timeFormatter.format(value as LocalTime)

            JavaTimeType.LOCAL_DATE_TIME.type, JavaTimeType.LOCAL_DATE_TIME_NULLABLE.type -> dateTimeFormatter.format(
                value as LocalDateTime
            )

            else -> value.toString().trimIfNecessary()
        }
    }

    private fun String.trimIfNecessary(): String {
        return if (context.trimWhiteSpace) this.trim() else this
    }
}