# Kotlin-Seed

> Kotlin Data Class to CSV Converter<br>
> Uses [kotlin-csv by doyaaaaaken](https://github.com/doyaaaaaken/kotlin-csv/)<br>
> Inspired by [Kotlin-Grass](https://github.com/blackmo18/kotlin-grass)

## Requirements

- Java 17

## Examples

### Declare data class

```kotlin
data class TestData(
    val id: Int,
    val name: String,
    val birth: LocalDateTime,
    val joined: LocalDate,
    val total: LocalTime,
)
```

### Init seed

```kotlin
val seed = seed<TestData> {
    dateFormat = "yyyy-MM-dd" // default
    timeFormat = "HH:mm" // default
    dateTime = " " // default
    trimWhiteSpace = true // default
    headerNamingStrategy = HeaderNamingStrategies.CAMEL_TO_SPACE // nullable
}
```

### Write to CSV

```kotlin
val datas = listOf(
    TestData(
        id = 1,
        name = "John",
        birth = LocalDateTime.of(1980, 3, 1, 12, 0),
        joined = LocalDate.of(2020, 4, 15),
        total = LocalTime.of(12, 30, 10)
    ),
    TestData(
        id = 2,
        name = "Jane",
        birth = LocalDateTime.of(1981, 4, 1, 13, 0),
        joined = LocalDate.of(2021, 5, 15),
        total = LocalTime.of(13, 30, 20)
    ),
    TestData(
        id = 3,
        name = "Jack",
        birth = LocalDateTime.of(1982, 5, 1, 14, 0),
        joined = LocalDate.of(2022, 6, 15),
        total = LocalTime.of(14, 30, 30)
    )
)

seed.plant(
    seeds = datas,
    targetFile = File("datas.csv")
)
```

### CSV results

| id  | name | birth            | joined     | total |
|-----|------|------------------|------------|-------|
| 1   | John | 1980-03-01 12:00 | 2020-04-15 | 12:30 |
| 2   | Jane | 1981-04-01 13:00 | 2021-05-15 | 13:30 |
| 3   | Jack | 1982-05-01 14:00 | 2022-06-15 | 14:30 |

### Use annotation for header

#### Declare data class with annotation

```kotlin
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
```

#### Write to CSV

```kotlin
val datas = listOf(
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
    seeds = datas,
    targetFile = File("datas.csv")
)
```

#### CSV Results

| id  | name | birth            | joined date | total time |
|-----|------|------------------|-------------|------------|
| 1   | John | 1980-03-01 12:00 | 2020-04-15  | 12:30      |
| 2   | Jane | 1981-04-01 13:00 | 2021-05-15  | 13:30      |
| 3   | Jack | 1982-05-01 14:00 | 2022-06-15  | 14:30      |

or you can find more examples in [src/test/kotlin](./src/test/kotlin)