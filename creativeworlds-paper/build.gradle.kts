plugins {
    kotlin("jvm")
    id("io.papermc.paperweight.userdev") version "1.3.8"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "nl.sagemc"
version = "1.0"

repositories {
    mavenLocal()
}

dependencies {
    api(project(":creativeworlds-api"))
    implementation(kotlin("stdlib-jdk8"))
    paperDevBundle("1.19.2-R0.1-SNAPSHOT")

    implementation("me.thojs:kommandhandler-core:1.0")
    implementation("me.thojs:kommandhandler-bukkit:1.0")
}

tasks {
    assemble {
        dependsOn(shadowJar)
        dependsOn(reobfJar)
    }

    compileKotlin {
        kotlinOptions.jvmTarget = JavaLanguageVersion.of(17).toString()
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

bukkit {
    name = "CreativeWorlds"
    main = "nl.sagemc.creativeworlds.paper.CreativeWorlds"
    apiVersion = "1.19"
    authors = listOf("Thojs", "RobijnenTaart")
}