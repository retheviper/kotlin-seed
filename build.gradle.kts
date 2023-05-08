plugins {
    kotlin("jvm") version "1.7.21"
}

group = "com.retheviper.kotlin.seed"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies { // kotlin
    implementation(kotlin("reflect"))

    // kotlin-csv
    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.9.0")

    // test
    testImplementation("io.kotest:kotest-runner-junit5:5.4.2")
    testImplementation("io.kotest:kotest-assertions-core:5.4.2")
    testImplementation("io.github.blackmo18:kotlin-grass-core-jvm:1.0.0")
    testImplementation("io.github.blackmo18:kotlin-grass-parser-jvm:0.8.0")
    testImplementation("io.github.blackmo18:kotlin-grass-date-time-jvm:0.8.0")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks {
    test {
        useJUnitPlatform()
    }

    compileKotlin {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "17"
            freeCompilerArgs = listOf("-XOpt-in=kotlin.RequiresOptIn")
        }
    }
}