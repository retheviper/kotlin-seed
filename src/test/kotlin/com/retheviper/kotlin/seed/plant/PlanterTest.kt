package com.retheviper.kotlin.seed.plant

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.retheviper.kotlin.seed.annotation.CsvHeaderName
import com.retheviper.kotlin.seed.dsl.seed
import com.retheviper.kotlin.seed.naming.HeaderNamingStrategies
import io.blackmo18.kotlin.grass.date.time.Java8DateTime
import io.blackmo18.kotlin.grass.dsl.grass
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.file.Files
import java.nio.file.Path
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalStdlibApi::class)
class PlanterTest : FreeSpec({

    lateinit var file: Path

    beforeAny {
        file = withContext(Dispatchers.IO) {
            Files.createTempFile("test", ".csv")
        }
    }

    afterAny {
        withContext(Dispatchers.IO) {
            Files.deleteIfExists(file)
        }
    }

    "with header name annotation" - {
        "without header naming strategy" {
            data class TestData(
                @CsvHeaderName("id")
                val a: Int,
                @CsvHeaderName("name")
                val b: String,
                @CsvHeaderName("birth")
                val c: LocalDateTime,
                @CsvHeaderName("joined date")
                val d: LocalDate,
                @CsvHeaderName("total time")
                val e: LocalTime,
            )

            val expected = listOf(
                TestData(
                    a = 1,
                    b = "John",
                    c = LocalDateTime.of(1980, 3, 1, 12, 0),
                    d = LocalDate.of(2020, 4, 15),
                    e = LocalTime.of(12, 30, 10)
                ),
                TestData(
                    a = 2,
                    b = "Jane",
                    c = LocalDateTime.of(1981, 4, 1, 13, 0),
                    d = LocalDate.of(2021, 5, 15),
                    e = LocalTime.of(13, 30, 20)
                ),
                TestData(
                    a = 3,
                    b = "Jack",
                    c = LocalDateTime.of(1982, 5, 1, 14, 0),
                    d = LocalDate.of(2022, 6, 15),
                    e = LocalTime.of(14, 30, 30)
                )
            )

            seed<TestData>().plant(
                seeds = expected,
                targetFile = file.toFile()
            )

            val actual = csvReader().readAllWithHeader(file.toFile())

            actual.forEachIndexed { index, row ->
                row["id"] shouldBe expected[index].a.toString()
                row["name"] shouldBe expected[index].b
                row["birth"] shouldBe DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(expected[index].c)
                row["joined date"] shouldBe expected[index].d.toString()
                row["total time"] shouldBe DateTimeFormatter.ofPattern("HH:mm").format(expected[index].e)
            }
        }

        "with header naming strategy" {
            data class TestData(
                @CsvHeaderName("id")
                val a: Int,
                @CsvHeaderName("name")
                val b: String,
                @CsvHeaderName("birth")
                val c: LocalDateTime,
                @CsvHeaderName("joinedDate")
                val d: LocalDate,
                @CsvHeaderName("totalTime")
                val e: LocalTime,
            )

            val expected = listOf(
                TestData(
                    a = 1,
                    b = "John",
                    c = LocalDateTime.of(1980, 3, 1, 12, 0),
                    d = LocalDate.of(2020, 4, 15),
                    e = LocalTime.of(12, 30, 10)
                ),
                TestData(
                    a = 2,
                    b = "Jane",
                    c = LocalDateTime.of(1981, 4, 1, 13, 0),
                    d = LocalDate.of(2021, 5, 15),
                    e = LocalTime.of(13, 30, 20)
                ),
                TestData(
                    a = 3,
                    b = "Jack",
                    c = LocalDateTime.of(1982, 5, 1, 14, 0),
                    d = LocalDate.of(2022, 6, 15),
                    e = LocalTime.of(14, 30, 30)
                )
            )

            seed<TestData> {
                headerNamingStrategy = HeaderNamingStrategies.CAMEL_TO_SPACE
            }.plant(
                seeds = expected,
                targetFile = file.toFile()
            )

            val actual = csvReader().readAllWithHeader(file.toFile())

            actual.forEachIndexed { index, row ->
                row["id"] shouldBe expected[index].a.toString()
                row["name"] shouldBe expected[index].b
                row["birth"] shouldBe DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(expected[index].c)
                row["joined date"] shouldBe expected[index].d.toString()
                row["total time"] shouldBe DateTimeFormatter.ofPattern("HH:mm").format(expected[index].e)
            }
        }
    }

    "without header name annotation" - {
        "without header naming strategy" {
            data class TestData(
                val id: Int,
                val name: String,
                val birth: LocalDateTime,
                val joined: LocalDate,
                val total: LocalTime,
            )

            val expected = listOf(
                TestData(
                    id = 1,
                    name = "John",
                    birth = LocalDateTime.of(1980, 3, 1, 12, 0),
                    joined = LocalDate.of(2020, 4, 15),
                    total = LocalTime.of(12, 30, 10)
                ), TestData(
                    id = 2,
                    name = "Jane",
                    birth = LocalDateTime.of(1981, 4, 1, 13, 0),
                    joined = LocalDate.of(2021, 5, 15),
                    total = LocalTime.of(13, 30, 20)
                ), TestData(
                    id = 3,
                    name = "Jack",
                    birth = LocalDateTime.of(1982, 5, 1, 14, 0),
                    joined = LocalDate.of(2022, 6, 15),
                    total = LocalTime.of(14, 30, 30)
                )
            )

            seed<TestData>().plant(
                seeds = expected, targetFile = file.toFile()
            )

            val actual = csvReader().readAllWithHeader(file.toFile())

            actual.forEachIndexed { index, row ->
                row["id"] shouldBe expected[index].id.toString()
                row["name"] shouldBe expected[index].name
                row["birth"] shouldBe DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(expected[index].birth)
                row["joined"] shouldBe expected[index].joined.toString()
                row["total"] shouldBe DateTimeFormatter.ofPattern("HH:mm").format(expected[index].total)
            }
        }

        "with header naming strategy" {
            data class TestData(
                val id: Int,
                val name: String,
                val birthDate: LocalDateTime,
                val joinedDate: LocalDate,
                val totalTime: LocalTime,
            )

            val expected = listOf(
                TestData(
                    id = 1,
                    name = "John",
                    birthDate = LocalDateTime.of(1980, 3, 1, 12, 0),
                    joinedDate = LocalDate.of(2020, 4, 15),
                    totalTime = LocalTime.of(12, 30, 10)
                ),
                TestData(
                    id = 2,
                    name = "Jane",
                    birthDate = LocalDateTime.of(1981, 4, 1, 13, 0),
                    joinedDate = LocalDate.of(2021, 5, 15),
                    totalTime = LocalTime.of(13, 30, 20)
                ),
                TestData(
                    id = 3,
                    name = "Jack",
                    birthDate = LocalDateTime.of(1982, 5, 1, 14, 0),
                    joinedDate = LocalDate.of(2022, 6, 15),
                    totalTime = LocalTime.of(14, 30, 30)
                )
            )

            seed<TestData> {
                headerNamingStrategy = HeaderNamingStrategies.CAMEL_TO_SPACE
            }.plant(
                seeds = expected,
                targetFile = file.toFile()
            )

            val actual = csvReader().readAllWithHeader(file.toFile())

            actual.forEachIndexed { index, row ->
                row["id"] shouldBe expected[index].id.toString()
                row["name"] shouldBe expected[index].name
                row["birth date"] shouldBe DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(expected[index].birthDate)
                row["joined date"] shouldBe expected[index].joinedDate.toString()
                row["total time"] shouldBe DateTimeFormatter.ofPattern("HH:mm").format(expected[index].totalTime)
            }
        }
    }

    "nullable fields" {
        data class TestData(
            val id: Int?,
            val name: String?,
            val birth: LocalDateTime?,
            val joined: LocalDate?,
            val total: LocalTime?,
        )

        val expected = listOf(
            TestData(
                id = 1,
                name = "John",
                birth = LocalDateTime.of(1980, 3, 1, 12, 0),
                joined = LocalDate.of(2020, 4, 15),
                total = LocalTime.of(12, 30, 0)
            ),
            TestData(
                id = null,
                name = null,
                birth = null,
                joined = null,
                total = null
            ),
            TestData(
                id = 3,
                name = "Jack",
                birth = LocalDateTime.of(1982, 5, 1, 14, 0),
                joined = LocalDate.of(2022, 6, 15),
                total = LocalTime.of(14, 30, 0)
            )
        )

        seed<TestData>().plant(
            seeds = expected,
            targetFile = file.toFile()
        )

        val actual = csvReader().readAllWithHeader(file.toFile())

        fun Any?.toStringOrBlank() = this?.toString() ?: ""

        actual.forEachIndexed { index, row ->
            row["id"] shouldBe expected[index].id.toStringOrBlank()
            row["name"] shouldBe expected[index].name.toStringOrBlank()
            row["birth"] shouldBe expected[index].birth?.let {
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(it)
            }.toStringOrBlank()
            row["joined"] shouldBe expected[index].joined.toStringOrBlank()
            row["total"] shouldBe expected[index].total?.let {
                DateTimeFormatter.ofPattern("HH:mm").format(it)
            }.toStringOrBlank()
        }
    }

    "read with kotlin-grass" {
        data class TestData(
            val id: Int,
            val name: String,
            val birth: LocalDateTime,
            val joined: LocalDate,
            val total: LocalTime,
        )

        val expected = listOf(
            TestData(
                id = 1,
                name = "John",
                birth = LocalDateTime.of(1980, 3, 1, 12, 0),
                joined = LocalDate.of(2020, 4, 15),
                total = LocalTime.of(12, 30, 0)
            ),
            TestData(
                id = 2,
                name = "Jane",
                birth = LocalDateTime.of(1981, 4, 1, 13, 0),
                joined = LocalDate.of(2021, 5, 15),
                total = LocalTime.of(13, 30, 0)
            ),
            TestData(
                id = 3,
                name = "Jack",
                birth = LocalDateTime.of(1982, 5, 1, 14, 0),
                joined = LocalDate.of(2022, 6, 15),
                total = LocalTime.of(14, 30, 0)
            )
        )

        val separator = "-"

        seed<TestData> {
            dateTimeSeparator = separator
        }.plant(
            seeds = expected,
            targetFile = file.toFile()
        )

        val actual =
            grass<TestData> {
                dateTimeSeparator = separator
                customDataTypes = listOf(Java8DateTime)
            }.harvest(csvReader().readAllWithHeader(file.toFile()))

        actual shouldBe expected
    }

    "read nullable fields with kotlin-grass" {
        data class TestData(
            val id: Int?,
            val name: String?,
            val birth: LocalDateTime?,
            val joined: LocalDate?,
            val total: LocalTime?,
        )

        val expected = listOf(
            TestData(
                id = 1,
                name = "John",
                birth = LocalDateTime.of(1980, 3, 1, 12, 0),
                joined = LocalDate.of(2020, 4, 15),
                total = LocalTime.of(12, 30, 0)
            ),
            TestData(
                id = null,
                name = null,
                birth = null,
                joined = null,
                total = null
            ),
            TestData(
                id = 3,
                name = "Jack",
                birth = LocalDateTime.of(1982, 5, 1, 14, 0),
                joined = LocalDate.of(2022, 6, 15),
                total = LocalTime.of(14, 30, 0)
            )
        )

        val separator = "-"

        seed<TestData> {
            dateTimeSeparator = separator
        }.plant(
            seeds = expected,
            targetFile = file.toFile()
        )

        val actual =
            grass<TestData> {
                dateTimeSeparator = separator
                customDataTypes = listOf(Java8DateTime)
            }.harvest(csvReader().readAllWithHeader(file.toFile()))

        actual shouldBe expected
    }
})