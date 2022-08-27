import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.32"
}

group = "com.retheviper.kotlin.seed"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // kotlin
    implementation(kotlin("reflect"))

    // kotlin-csv
    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.6.0")

    // test
    testImplementation("io.kotest:kotest-runner-junit5:5.4.2")
    testImplementation("io.kotest:kotest-assertions-core:5.4.2")
    testImplementation("io.github.blackmo18:kotlin-grass-core-jvm:1.0.0")
    testImplementation("io.github.blackmo18:kotlin-grass-parser-jvm:0.8.0")
    testImplementation("io.github.blackmo18:kotlin-grass-date-time-jvm:0.8.0")
}

tasks {
    test {
        useJUnitPlatform()
    }

    compileKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}