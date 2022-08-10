plugins {
    kotlin("jvm") version "1.7.0"
}

group = "nl.sagemc"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = JavaLanguageVersion.of(17).toString()
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}